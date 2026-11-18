package cvc.dashingdog.tydgenoot.model

import kotlin.random.Random

data class Offset(val x: Float, val y: Float)

object BurnInOffsetCalculator {
    private const val INTERVAL_MS = 5 * 60 * 1000L // 5 minutes
    private const val MAX_OFFSET_PX = 24f

    /**
     * Calculates a burn-in protection offset based on epoch time and maximum safe bounds.
     * Ensures the offset stays within [-maxOffset, +maxOffset] so outer segments never clip.
     */
    fun calculateOffset(
        currentEpochMs: Long,
        maxOffset: Float = MAX_OFFSET_PX,
        random: Random = Random
    ): Offset {
        // Derive a stable seed block based on 5-minute intervals
        val intervalIndex = currentEpochMs / INTERVAL_MS
        val intervalRandom = Random(intervalIndex)

        val dx = intervalRandom.nextFloat() * (2 * maxOffset) - maxOffset
        val dy = intervalRandom.nextFloat() * (2 * maxOffset) - maxOffset

        return Offset(dx.coerceIn(-maxOffset, maxOffset), dy.coerceIn(-maxOffset, maxOffset))
    }
}