package cvc.dashingdog.tydgenoot.model

import java.util.Calendar
import java.util.Locale

data class FormattedDateInfo(
    val formattedDate: String, // "dd MM yyyy", always 2-2-4 digits
    val dayOfWeekIndex: Int,   // 0 = MON .. 6 = SUN
    val dayShortLabel: String  // "MON" .. "SUN"
)

object DateFormatter {

    val mondayFirstLabels: List<String> = listOf(
        "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"
    )

    val mondayFirstCompactLabels: List<String> = listOf(
        "M", "T", "W", "T", "F", "S", "S"
    )

    /**
     * Maps java.util.Calendar.DAY_OF_WEEK (Calendar.SUNDAY = 1 .. Calendar.SATURDAY = 7)
     * to a Monday-first index (0 = MON .. 6 = SUN).
     * Any out-of-range value safely falls back to 0 (MON) instead of throwing.
     */
    fun calendarDayOfWeekToMondayFirstIndex(calendarDayOfWeek: Int): Int {
        return when (calendarDayOfWeek) {
            Calendar.MONDAY -> 0
            Calendar.TUESDAY -> 1
            Calendar.WEDNESDAY -> 2
            Calendar.THURSDAY -> 3
            Calendar.FRIDAY -> 4
            Calendar.SATURDAY -> 5
            Calendar.SUNDAY -> 6
            else -> 0
        }
    }

    /**
     * Formats day, month, and year strictly as "dd MM yyyy" with zero-padded 2-digit day/month
     * and 4-digit year (clamped to 0..9999 so formatting always produces 4 year digits).
     */
    fun formatDdMmYyyy(dayOfMonth: Int, monthOneBased: Int, year: Int): String {
        val safeDay = dayOfMonth.coerceIn(1, 31)
        val safeMonth = monthOneBased.coerceIn(1, 12)
        val safeYear = year.coerceIn(0, 9999)
        return String.format(Locale.US, "%02d %02d %04d", safeDay, safeMonth, safeYear)
    }

    fun formatFromCalendar(calendar: Calendar): FormattedDateInfo {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val monthOneBased = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        val dayIndex = calendarDayOfWeekToMondayFirstIndex(calendar.get(Calendar.DAY_OF_WEEK))

        return FormattedDateInfo(
            formattedDate = formatDdMmYyyy(day, monthOneBased, year),
            dayOfWeekIndex = dayIndex,
            dayShortLabel = mondayFirstLabels[dayIndex]
        )
    }

    fun formatFromEpochMillis(epochMillis: Long): FormattedDateInfo {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = epochMillis
        }
        return formatFromCalendar(calendar)
    }
}