package cvc.dashingdog.tydgenoot.model

import org.junit.Assert.assertEquals
import org.junit.Test

class SegmentMapperTest {

    @Test
    fun testDigitMapping() {
        assertEquals(SegmentState.DIGIT_0, SegmentMapper.mapDigit(0))
        assertEquals(SegmentState.DIGIT_8, SegmentMapper.mapDigit(8))
        assertEquals(SegmentState.DIGIT_9, SegmentMapper.mapDigit(9))
        assertEquals(SegmentState.BLANK, SegmentMapper.mapDigit(10))
        assertEquals(SegmentState.BLANK, SegmentMapper.mapDigit(-1))
    }

    @Test
    fun testHourMappingWithLeadingZero() {
        val (tens, units) = SegmentMapper.mapHour(8, showLeadingZero = true)
        assertEquals(SegmentState.DIGIT_0, tens)
        assertEquals(SegmentState.DIGIT_8, units)

        val (tens12, units12) = SegmentMapper.mapHour(12, showLeadingZero = true)
        assertEquals(SegmentState.DIGIT_1, tens12)
        assertEquals(SegmentState.DIGIT_2, units12)
    }

    @Test
    fun testHourMappingWithoutLeadingZero() {
        val (tens, units) = SegmentMapper.mapHour(8, showLeadingZero = false)
        assertEquals(SegmentState.BLANK, tens)
        assertEquals(SegmentState.DIGIT_8, units)

        val (tens12, units12) = SegmentMapper.mapHour(12, showLeadingZero = false)
        assertEquals(SegmentState.DIGIT_1, tens12)
        assertEquals(SegmentState.DIGIT_2, units12)
    }

    @Test
    fun testMinuteMapping() {
        val (tens, units) = SegmentMapper.mapMinute(45)
        assertEquals(SegmentState.DIGIT_4, tens)
        assertEquals(SegmentState.DIGIT_5, units)
    }
}