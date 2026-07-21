package tasbeh100388.app

import org.junit.Test
import org.junit.Assert.*

class CounterUnitTest {

    private var count = 0
    private var target = 33

    private fun increment() {
        count++
    }

    private fun reset() {
        count = 0
    }

    private fun setTarget(t: Int) {
        target = t
        reset()
    }

    @Test
    fun count_starts_at_zero() {
        assertEquals(0, count)
    }

    @Test
    fun increment_increases_count() {
        increment()
        assertEquals(1, count)
    }

    @Test
    fun multiple_increments_add_up() {
        repeat(5) { increment() }
        assertEquals(5, count)
    }

    @Test
    fun reset_sets_count_to_zero() {
        repeat(10) { increment() }
        reset()
        assertEquals(0, count)
    }

    @Test
    fun set_target_resets_count() {
        repeat(7) { increment() }
        setTarget(99)
        assertEquals(0, count)
        assertEquals(99, target)
    }
}