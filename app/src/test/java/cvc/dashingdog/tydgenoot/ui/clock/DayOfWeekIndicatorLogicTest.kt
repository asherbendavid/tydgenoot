package cvc.dashingdog.tydgenoot.model

import org.junit.Assert.assertEquals
import org.junit.Test

class DayOfWeekIndicatorLogicTest {

    @Test
    fun `monday first standard labels have 7 entries starting with MON and ending with SUN`() {
        assertEquals(7, DateFormatter.mondayFirstLabels.size)
        assertEquals("MON", DateFormatter.mondayFirstLabels.first())
        assertEquals("SUN", DateFormatter.mondayFirstLabels.last())
    }

    @Test
    fun `monday first compact labels have 7 entries starting with M and ending with S`() {
        assertEquals(7, DateFormatter.mondayFirstCompactLabels.size)
        assertEquals("M", DateFormatter.mondayFirstCompactLabels.first())
        assertEquals("S", DateFormatter.mondayFirstCompactLabels.last())
    }

    @Test
    fun `active day index coerces out of bounds values safely into 0 to 6 range`() {
        assertEquals(0, (-3).coerceIn(0, 6))
        assertEquals(0, 0.coerceIn(0, 6))
        assertEquals(6, 6.coerceIn(0, 6))
        assertEquals(6, 12.coerceIn(0, 6))
    }
}