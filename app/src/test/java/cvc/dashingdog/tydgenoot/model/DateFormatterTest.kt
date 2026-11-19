package cvc.dashingdog.tydgenoot.model

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Calendar
import java.util.TimeZone

class DateFormatterTest {

    @Test
    fun `calendarDayOfWeek maps Monday through Sunday to 0 through 6`() {
        assertEquals(0, DateFormatter.calendarDayOfWeekToMondayFirstIndex(Calendar.MONDAY))
        assertEquals(1, DateFormatter.calendarDayOfWeekToMondayFirstIndex(Calendar.TUESDAY))
        assertEquals(2, DateFormatter.calendarDayOfWeekToMondayFirstIndex(Calendar.WEDNESDAY))
        assertEquals(3, DateFormatter.calendarDayOfWeekToMondayFirstIndex(Calendar.THURSDAY))
        assertEquals(4, DateFormatter.calendarDayOfWeekToMondayFirstIndex(Calendar.FRIDAY))
        assertEquals(5, DateFormatter.calendarDayOfWeekToMondayFirstIndex(Calendar.SATURDAY))
        assertEquals(6, DateFormatter.calendarDayOfWeekToMondayFirstIndex(Calendar.SUNDAY))
    }

    @Test
    fun `out of range calendar day of week safely falls back to 0 without crashing`() {
        assertEquals(0, DateFormatter.calendarDayOfWeekToMondayFirstIndex(0))
        assertEquals(0, DateFormatter.calendarDayOfWeekToMondayFirstIndex(-5))
        assertEquals(0, DateFormatter.calendarDayOfWeekToMondayFirstIndex(8))
        assertEquals(0, DateFormatter.calendarDayOfWeekToMondayFirstIndex(Int.MAX_VALUE))
    }

    @Test
    fun `formatDdMmYyyy formats single digit days and months with leading zeros and 4 digit year`() {
        assertEquals("05 03 2026", DateFormatter.formatDdMmYyyy(5, 3, 2026))
        assertEquals("19 11 2026", DateFormatter.formatDdMmYyyy(19, 11, 2026))
        assertEquals("29 02 2028", DateFormatter.formatDdMmYyyy(29, 2, 2028))
    }

    @Test
    fun `formatDdMmYyyy handles year rollovers and clamps out of range inputs`() {
        assertEquals("31 12 2026", DateFormatter.formatDdMmYyyy(31, 12, 2026))
        assertEquals("01 01 2027", DateFormatter.formatDdMmYyyy(1, 1, 2027))
        assertEquals("01 01 0000", DateFormatter.formatDdMmYyyy(-10, 0, -42))
        assertEquals("31 12 9999", DateFormatter.formatDdMmYyyy(99, 99, 10500))
    }

    @Test
    fun `formatFromCalendar updates day and date across Sunday to Monday midnight rollover`() {
        val tz = TimeZone.getTimeZone("Africa/Johannesburg")

        val sundayNight = Calendar.getInstance(tz).apply {
            set(2026, Calendar.NOVEMBER, 22, 23, 59, 59)
            set(Calendar.MILLISECOND, 999)
        }
        val beforeMidnight = DateFormatter.formatFromCalendar(sundayNight)
        assertEquals("22 11 2026", beforeMidnight.formattedDate)
        assertEquals(6, beforeMidnight.dayOfWeekIndex)
        assertEquals("SUN", beforeMidnight.dayShortLabel)

        val mondayMorning = Calendar.getInstance(tz).apply {
            set(2026, Calendar.NOVEMBER, 23, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val afterMidnight = DateFormatter.formatFromCalendar(mondayMorning)
        assertEquals("23 11 2026", afterMidnight.formattedDate)
        assertEquals(0, afterMidnight.dayOfWeekIndex)
        assertEquals("MON", afterMidnight.dayShortLabel)
    }
}