package tasbeh100388.app

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.animation.ScaleAnimation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private var count = 0
    private var target = 33

    private lateinit var countText: TextView
    private lateinit var dhikrText: TextView
    private lateinit var countButton: MaterialButton
    private lateinit var resetButton: MaterialButton

    private val dhikrList = arrayOf(
        "Subhanallah", "Alhamdulillah", "Allahu Akbar",
        "La ilaha illallah", "Astaghfirullah"
    )
    private var dhikrIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countText = findViewById(R.id.countText)
        dhikrText = findViewById(R.id.dhikrText)
        countButton = findViewById(R.id.countButton)
        resetButton = findViewById(R.id.resetButton)

        updateDisplay()
        highlightTarget()

        countButton.setOnClickListener { incrementCount() }
        countButton.setOnLongClickListener {
            nextDhikr()
            true
        }

        findViewById<MaterialButton>(R.id.target33).setOnClickListener { setTarget(33) }
        findViewById<MaterialButton>(R.id.target99).setOnClickListener { setTarget(99) }
        findViewById<MaterialButton>(R.id.target100).setOnClickListener { setTarget(100) }

        resetButton.setOnClickListener { resetCount() }
    }

    private fun incrementCount() {
        count++
        updateDisplay()
        animateButton()
        vibrate()

        if (count >= target) {
            Snackbar.make(countButton, getString(R.string.target_reached, target), Snackbar.LENGTH_LONG)
                .setAnchorView(countButton)
                .show()
            count = 0
            updateDisplay()
        }
    }

    private fun animateButton() {
        val anim = ScaleAnimation(1f, 0.85f, 1f, 0.85f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f)
        anim.duration = 100
        anim.repeatMode = ScaleAnimation.REVERSE
        anim.repeatCount = 1
        countButton.startAnimation(anim)
    }

    private fun vibrate() {
        try {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val manager = getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
                manager.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                getSystemService(VIBRATOR_SERVICE) as Vibrator
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(30)
            }
        } catch (_: Exception) { }
    }

    private fun resetCount() {
        count = 0
        updateDisplay()
    }

    private fun setTarget(newTarget: Int) {
        target = newTarget
        highlightTarget()
        resetCount()
    }

    private fun nextDhikr() {
        dhikrIndex = (dhikrIndex + 1) % dhikrList.size
        dhikrText.text = dhikrList[dhikrIndex]
    }

    private fun updateDisplay() {
        countText.text = count.toString()
    }

    private fun highlightTarget() {
        val selected = ContextCompat.getColor(this, R.color.counterColor)
        val selectedText = ContextCompat.getColor(this, android.R.color.white)
        val defaultBg = ContextCompat.getColor(this, R.color.targetDefault)
        val defaultText = ContextCompat.getColor(this, R.color.textSecondary)

        val ids = listOf(R.id.target33, R.id.target99, R.id.target100)
        val targets = listOf(33, 99, 100)
        for (i in ids.indices) {
            val btn = findViewById<MaterialButton>(ids[i])
            if (targets[i] == target) {
                btn.setBackgroundColor(selected)
                btn.setTextColor(selectedText)
            } else {
                btn.setBackgroundColor(defaultBg)
                btn.setTextColor(defaultText)
            }
        }
    }
}
