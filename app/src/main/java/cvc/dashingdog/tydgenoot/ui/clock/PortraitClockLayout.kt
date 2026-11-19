package cvc.dashingdog.tydgenoot.ui.clock

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import cvc.dashingdog.tydgenoot.model.DateFormatter

@Composable
fun PortraitClockLayout(
    uiState: ClockUiState,
    modifier: Modifier = Modifier,
    activeColor: Color = Color(0xFF00E5FF),
    ghostColor: Color = Color(0xFF112225)
) {
    val density = LocalDensity.current
    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    var contentSize by remember { mutableStateOf(IntSize.Zero) }

    val outerPadding: Dp = 20.dp

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged { containerSize = it }
            .padding(outerPadding),
        contentAlignment = Alignment.Center
    ) {
        val availableWidth = this@BoxWithConstraints.maxWidth
        val availableHeight = this@BoxWithConstraints.maxHeight

        // In portrait, width is typically the tighter constraint for the 4-digit HH:MM row (~2.95 * h).
        // Height also accounts for top day row + bottom date row + vertical gaps (~1.15 * h).
        val widthDrivenTimeDp: Dp = availableWidth * 0.90f / 2.95f
        val heightDrivenTimeDp: Dp = availableHeight * 0.65f / 1.15f
        val timeDigitHeight: Dp = min(widthDrivenTimeDp, heightDrivenTimeDp)
            .coerceAtLeast(24.dp)

        val timeDigitWidth: Dp = timeDigitHeight * 0.5625f
        val colonWidth: Dp = timeDigitHeight * 0.25f
        val digitGap: Dp = timeDigitWidth * 0.09f
        val colonGap: Dp = timeDigitWidth * 0.09f

        // Date row below HH:MM, scaled proportionally (~0.45x of time digit height, bounded by width)
        val maxDateDigitWidth: Dp = (availableWidth * 0.88f) / 8.9f
        val dateDigitHeight: Dp = min(
            timeDigitHeight * 0.45f,
            maxDateDigitWidth / 0.5625f
        ).coerceAtLeast(14.dp)

        // Compact M..S day labels across top so all 7 days fit cleanly in portrait width
        val dayScaleDp: Dp = (timeDigitHeight * 0.18f).coerceAtLeast(12.dp)
        val dayFontSp = with(density) { dayScaleDp.toSp() }
        val daySpacing: Dp = ((availableWidth * 0.72f) / 7f)
            .coerceIn(6.dp, 24.dp)

        val safeTranslation: IntOffset = remember(uiState.burnInOffset, containerSize, contentSize) {
            clampBurnInOffsetPx(
                requestedOffset = uiState.burnInOffset,
                containerSize = containerSize,
                contentSize = contentSize
            )
        }

        Column(
            modifier = Modifier
                .onSizeChanged { contentSize = it }
                .graphicsLayer {
                    translationX = safeTranslation.x.toFloat()
                    translationY = safeTranslation.y.toFloat()
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DayOfWeekIndicator(
                activeDayIndex = uiState.dayOfWeekIndex,
                vertical = false,
                useCompactLabels = true,
                labels = DateFormatter.mondayFirstCompactLabels,
                activeColor = activeColor,
                inactiveColor = ghostColor,
                fontSize = dayFontSp,
                spacing = daySpacing
            )

            Spacer(modifier = Modifier.height((timeDigitHeight * 0.14f).coerceAtLeast(10.dp)))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                SegmentDigit(
                    segmentState = uiState.hourTens,
                    activeColor = activeColor,
                    ghostColor = ghostColor,
                    modifier = Modifier.size(width = timeDigitWidth, height = timeDigitHeight)
                )
                Spacer(modifier = Modifier.width(digitGap))
                SegmentDigit(
                    segmentState = uiState.hourUnits,
                    activeColor = activeColor,
                    ghostColor = ghostColor,
                    modifier = Modifier.size(width = timeDigitWidth, height = timeDigitHeight)
                )
                Spacer(modifier = Modifier.width(colonGap))
                BlinkingColon(
                    visible = uiState.colonVisible,
                    activeColor = activeColor,
                    ghostColor = ghostColor,
                    modifier = Modifier.size(width = colonWidth, height = timeDigitHeight)
                )
                Spacer(modifier = Modifier.width(colonGap))
                SegmentDigit(
                    segmentState = uiState.minuteTens,
                    activeColor = activeColor,
                    ghostColor = ghostColor,
                    modifier = Modifier.size(width = timeDigitWidth, height = timeDigitHeight)
                )
                Spacer(modifier = Modifier.width(digitGap))
                SegmentDigit(
                    segmentState = uiState.minuteUnits,
                    activeColor = activeColor,
                    ghostColor = ghostColor,
                    modifier = Modifier.size(width = timeDigitWidth, height = timeDigitHeight)
                )
            }

            Spacer(modifier = Modifier.height((timeDigitHeight * 0.14f).coerceAtLeast(10.dp)))

            DateRow(
                formattedDate = uiState.formattedDate,
                digitHeight = dateDigitHeight,
                activeColor = activeColor,
                ghostColor = ghostColor
            )
        }
    }
}