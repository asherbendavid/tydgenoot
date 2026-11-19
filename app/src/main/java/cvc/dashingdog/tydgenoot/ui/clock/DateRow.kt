package cvc.dashingdog.tydgenoot.ui.clock

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cvc.dashingdog.tydgenoot.model.SegmentMapper
import cvc.dashingdog.tydgenoot.model.SegmentState

@Composable
fun DateRow(
    formattedDate: String,
    digitHeight: Dp,
    modifier: Modifier = Modifier,
    activeColor: Color = Color(0xFF00E5FF),
    ghostColor: Color = Color(0xFF112225)
) {
    val safeHeight = digitHeight.coerceAtLeast(8.dp)
    val digitWidth = safeHeight * 0.5625f
    val digitGap = digitWidth * 0.10f
    val groupGap = digitWidth * 0.45f

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        formattedDate.forEachIndexed { index, ch ->
            if (ch == ' ') {
                Spacer(modifier = Modifier.width(groupGap))
            } else {
                if (index > 0 && formattedDate[index - 1] != ' ') {
                    Spacer(modifier = Modifier.width(digitGap))
                }
                val state: SegmentState = if (ch in '0'..'9') {
                    SegmentMapper.mapDigit(ch.digitToInt())
                } else {
                    SegmentState.BLANK
                }
                SegmentDigit(
                    segmentState = state,
                    activeColor = activeColor,
                    ghostColor = ghostColor,
                    modifier = Modifier.size(width = digitWidth, height = safeHeight)
                )
            }
        }
    }
}