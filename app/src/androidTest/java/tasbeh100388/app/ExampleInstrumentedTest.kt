package tasbeh100388.app

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CounterInstrumentedTest {

    @Test
    fun initial_count_is_zero() {
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withId(R.id.countText)).check(matches(withText("0")))
        }
    }

    @Test
    fun tap_increments_count() {
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withId(R.id.countButton)).perform(click())
            onView(withId(R.id.countText)).check(matches(withText("1")))
        }
    }

    @Test
    fun multiple_taps_increment_correctly() {
        ActivityScenario.launch(MainActivity::class.java).use {
            repeat(5) { onView(withId(R.id.countButton)).perform(click()) }
            onView(withId(R.id.countText)).check(matches(withText("5")))
        }
    }

    @Test
    fun reset_button_clears_count() {
        ActivityScenario.launch(MainActivity::class.java).use {
            repeat(10) { onView(withId(R.id.countButton)).perform(click()) }
            onView(withId(R.id.resetButton)).perform(click())
            onView(withId(R.id.countText)).check(matches(withText("0")))
        }
    }

    @Test
    fun target_selection_resets_count() {
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withId(R.id.target99)).perform(click())
            onView(withId(R.id.countText)).check(matches(withText("0")))
        }
    }

    @Test
    fun long_press_cycles_dhikr() {
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withId(R.id.countButton)).perform(longClick())
            onView(withId(R.id.dhikrText)).check(matches(withText("Alhamdulillah")))
        }
    }
}