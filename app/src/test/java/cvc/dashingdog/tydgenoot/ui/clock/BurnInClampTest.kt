package cvc.dashingdog.tydgenoot.ui.clock

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import cvc.dashingdog.tydgenoot.model.Offset
import org.junit.Assert.assertEquals
import org.junit.Test

class BurnInClampTest {

    @Test
    fun `allows full offset when generous slack exists`() {
        val result = clampBurnInOffsetPx(
            requestedOffset = Offset(24f, -24f),
            containerSize = IntSize(1024, 600),
            contentSize = IntSize(700, 400),
            safetyMarginPx = 4f
        )
        assertEquals(IntOffset(24, -24), result)
    }

    @Test
    fun `clamps offset to prevent clipping when viewport is tight`() {
        // Total horizontal slack = 800 - 780 = 20px -> half slack = 10px -> minus 4px margin = 6px max
        val result = clampBurnInOffsetPx(
            requestedOffset = Offset(24f, -24f),
            containerSize = IntSize(800, 420),
            contentSize = IntSize(780, 412),
            safetyMarginPx = 4f
        )
        assertEquals(IntOffset(6, 0), result)
    }

    @Test
    fun `returns zero offset when content fills or exceeds container`() {
        val result = clampBurnInOffsetPx(
            requestedOffset = Offset(-20f, 18f),
            containerSize = IntSize(500, 300),
            contentSize = IntSize(520, 310),
            safetyMarginPx = 4f
        )
        assertEquals(IntOffset(0, 0), result)
    }
}