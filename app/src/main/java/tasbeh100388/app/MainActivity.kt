package tasbeh100388.app

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Находим главный корневой View или используем клик по всему экрану
        val rootView = findViewById<View>(android.R.id.content)
        
        // Попробуем найти текстовое поле, а если его нет по ID, приложение не упадет
        val tvCounter = findViewById<TextView>(resources.getIdentifier("tvCounter", "id", packageName))
            ?: findViewById<TextView>(resources.getIdentifier("tv_counter", "id", packageName))

        rootView.setOnClickListener {
            count++
            tvCounter?.text = count.toString()
            vibrateDevice()
        }
    }

    private fun vibrateDevice() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(50)
        }
    }
}
