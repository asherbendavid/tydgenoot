package cvc.dashingdog.tydgenoot.model

/**
 * Represents the state of the 7 segments (a through g) for a single digit or blank display.
 * Standard layout:
 *   a = top
 *   b = top-right
 *   c = bottom-right
 *   d = bottom
 *   e = bottom-left
 *   f = top-left
 *   g = middle
 */
data class SegmentState(
    val a: Boolean,
    val b: Boolean,
    val c: Boolean,
    val d: Boolean,
    val e: Boolean,
    val f: Boolean,
    val g: Boolean
) {
    companion object {
        // Predefined mappings for 0-9
        val DIGIT_0 = SegmentState(a = true, b = true, c = true, d = true, e = true, f = true, g = false)
        val DIGIT_1 = SegmentState(a = false, b = true, c = true, d = false, e = false, f = false, g = false)
        val DIGIT_2 = SegmentState(a = true, b = true, c = false, d = true, e = true, f = false, g = true)
        val DIGIT_3 = SegmentState(a = true, b = true, c = true, d = true, e = false, f = false, g = true)
        val DIGIT_4 = SegmentState(a = false, b = true, c = true, d = false, e = false, f = true, g = true)
        val DIGIT_5 = SegmentState(a = true, b = false, c = true, d = true, e = false, f = true, g = true)
        val DIGIT_6 = SegmentState(a = true, b = false, c = true, d = true, e = true, f = true, g = true)
        val DIGIT_7 = SegmentState(a = true, b = true, c = true, d = false, e = false, f = false, g = false)
        val DIGIT_8 = SegmentState(a = true, b = true, c = true, d = true, e = true, f = true, g = true)
        val DIGIT_9 = SegmentState(a = true, b = true, c = true, d = true, e = false, f = true, g = true)
        val BLANK   = SegmentState(a = false, b = false, c = false, d = false, e = false, f = false, g = false)
    }
}

object SegmentMapper {
    /**
     * Maps an integer digit (0-9) to its corresponding [SegmentState].
     * Out-of-range values return [SegmentState.BLANK].
     */
    fun mapDigit(digit: Int): SegmentState {
        return when (digit) {
            0 -> SegmentState.DIGIT_0
            1 -> SegmentState.DIGIT_1
            2 -> SegmentState.DIGIT_2
            3 -> SegmentState.DIGIT_3
            4 -> SegmentState.DIGIT_4
            5 -> SegmentState.DIGIT_5
            6 -> SegmentState.DIGIT_6
            7 -> SegmentState.DIGIT_7
            8 -> SegmentState.DIGIT_8
            9 -> SegmentState.DIGIT_9
            else -> SegmentState.BLANK
        }
    }

    /**
     * Maps hours (0-23) into tens and units digits, respecting the leading zero setting.
     * @param hour The hour value (0-23).
     * @param showLeadingZero If true, hours < 10 render as "08"; if false, render as blank tens digit (" 8").
     * @return A Pair of [SegmentState] for (tensDigit, unitsDigit).
     */
    fun mapHour(hour: Int, showLeadingZero: Boolean): Pair<SegmentState, SegmentState> {
        val clampedHour = hour.coerceIn(0, 23)
        val tens = clampedHour / 10
        val units = clampedHour % 10

        val tensState = if (tens == 0 && !showLeadingZero) {
            SegmentState.BLANK
        } else {
            mapDigit(tens)
        }
        val unitsState = mapDigit(units)
        return Pair(tensState, unitsState)
    }

    /**
     * Maps minutes (0-59) into tens and units digits.
     */
    fun mapMinute(minute: Int): Pair<SegmentState, SegmentState> {
        val clampedMinute = minute.coerceIn(0, 59)
        return Pair(mapDigit(clampedMinute / 10), mapDigit(clampedMinute % 10))
    }
}