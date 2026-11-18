package cvc.dashingdog.tydgenoot.ui.clock

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import cvc.dashingdog.tydgenoot.model.SegmentState

@Composable
fun SegmentDigit(
    segmentState: SegmentState,
    modifier: Modifier = Modifier,
    activeColor: Color = Color(0xFF00E5FF), // Cyan default
    ghostColor: Color = Color(0xFF112225)   // Dim dark glowing ghost segment
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Segment geometry thickness and padding proportional to canvas width/height
        val t = w * 0.12f // segment thickness

        drawSegment(SegmentPos.TOP, segmentState.a, w, h, t, activeColor, ghostColor)
        drawSegment(SegmentPos.TOP_LEFT, segmentState.f, w, h, t, activeColor, ghostColor)
        drawSegment(SegmentPos.TOP_RIGHT, segmentState.b, w, h, t, activeColor, ghostColor)
        drawSegment(SegmentPos.MIDDLE, segmentState.g, w, h, t, activeColor, ghostColor)
        drawSegment(SegmentPos.BOTTOM_LEFT, segmentState.e, w, h, t, activeColor, ghostColor)
        drawSegment(SegmentPos.BOTTOM_RIGHT, segmentState.c, w, h, t, activeColor, ghostColor)
        drawSegment(SegmentPos.BOTTOM, segmentState.d, w, h, t, activeColor, ghostColor)
    }
}

@Composable
fun BlinkingColon(
    visible: Boolean,
    modifier: Modifier = Modifier,
    activeColor: Color = Color(0xFF00E5FF),
    ghostColor: Color = Color(0xFF112225)
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val radius = w * 0.25f

        val topColor = if (visible) activeColor else ghostColor
        val bottomColor = if (visible) activeColor else ghostColor

        drawCircle(color = topColor, radius = radius, center = Offset(w / 2f, h * 0.35f))
        drawCircle(color = bottomColor, radius = radius, center = Offset(w / 2f, h * 0.65f))
    }
}

private enum class SegmentPos { TOP, TOP_LEFT, TOP_RIGHT, MIDDLE, BOTTOM_LEFT, BOTTOM_RIGHT, BOTTOM }

private fun DrawScope.drawSegment(
    pos: SegmentPos,
    isLit: Boolean,
    w: Float,
    h: Float,
    t: Float,
    activeColor: Color,
    ghostColor: Color
) {
    val color = if (isLit) activeColor else ghostColor
    val path = Path()

    // Sharp-angled classic 7-segment geometry paths
    val pad = t * 0.3f
    val halfH = h / 2f

    when (pos) {
        SegmentPos.TOP -> {
            path.moveTo(t * 0.8f, pad)
            path.lineTo(w - t * 0.8f, pad)
            path.lineTo(w - t * 1.3f, pad + t)
            path.lineTo(t * 1.3f, pad + t)
            path.close()
        }
        SegmentPos.TOP_LEFT -> {
            path.moveTo(pad, t * 1.2f)
            path.lineTo(pad + t, t * 1.8f)
            path.lineTo(pad + t, halfH - t * 0.6f)
            path.lineTo(pad, halfH - t * 0.2f)
            path.close()
        }
        SegmentPos.TOP_RIGHT -> {
            path.moveTo(w - pad, t * 1.2f)
            path.lineTo(w - pad, halfH - t * 0.2f)
            path.lineTo(w - pad - t, halfH - t * 0.6f)
            path.lineTo(w - pad - t, t * 1.8f)
            path.close()
        }
        SegmentPos.MIDDLE -> {
            // Pointed outward tips on both ends (< > shape)
            val halfT = t * 0.5f
            path.moveTo(t * 1.2f, halfH)
            path.lineTo(w - t * 1.2f, halfH)
            // Right tip pointing outward
            path.lineTo(w - t * 0.7f, halfH + halfT)
            path.lineTo(w - t * 1.2f, halfH + t)
            path.lineTo(t * 1.2f, halfH + t)
            // Left tip pointing outward
            path.lineTo(t * 0.7f, halfH + halfT)
            path.close()
        }
        SegmentPos.BOTTOM_LEFT -> {
            path.moveTo(pad, halfH + t * 1.2f)
            path.lineTo(pad + t, halfH + t * 1.6f)
            path.lineTo(pad + t, h - t * 1.8f)
            path.lineTo(pad, h - t * 1.2f)
            path.close()
        }
        SegmentPos.BOTTOM_RIGHT -> {
            path.moveTo(w - pad, halfH + t * 1.2f)
            path.lineTo(w - pad, h - t * 1.2f)
            path.lineTo(w - pad - t, h - t * 1.8f)
            path.lineTo(w - pad - t, halfH + t * 1.6f)
            path.close()
        }
        SegmentPos.BOTTOM -> {
            path.moveTo(t * 1.3f, h - pad - t)
            path.lineTo(w - t * 1.3f, h - pad - t)
            path.lineTo(w - t * 0.8f, h - pad)
            path.lineTo(t * 0.8f, h - pad)
            path.close()
        }
    }
    drawPath(path = path, color = color)
}