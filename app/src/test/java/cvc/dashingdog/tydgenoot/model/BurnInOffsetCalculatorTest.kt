package cvc.dashingdog.tydgenoot.model

import org.junit.Assert.assertTrue
import org.junit.Test

class BurnInOffsetCalculatorTest {

    @Test
    fun testOffsetWithinBounds() {
        val time1 = 0L
        val time2 = 5 * 60 * 1000L + 1234L

        val offset1 = BurnInOffsetCalculator.calculateOffset(time1, 24f)
        val offset2 = BurnInOffsetCalculator.calculateOffset(time2, 24f)

        assertTrue(offset1.x in -24f..24f)
        assertTrue(offset1.y in -24f..24f)
        assertTrue(offset2.x in -24f..24f)
        assertTrue(offset2.y in -24f..24f)
    }

    @Test
    fun testOffsetStabilityWithinInterval() {
        val baseTime = 1000000L
        val offsetA = BurnInOffsetCalculator.calculateOffset(baseTime)
        val offsetB = BurnInOffsetCalculator.calculateOffset(baseTime + 1000L) // within same 5-min window

        // Should be identical within the same 5-minute block
        assertTrue(offsetA.x == offsetB.x)
        assertTrue(offsetA.y == offsetB.y)
    }
}