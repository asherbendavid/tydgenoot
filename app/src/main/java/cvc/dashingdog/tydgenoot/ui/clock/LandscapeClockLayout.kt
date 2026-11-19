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
import cvc.dashingdog.tydgenoot.model.Offset
import kotlin.math.abs
import kotlin.math.min as mathMin
import kotlin.math.roundToInt

/**
 * Computes a safe translation offset in pixels so applying burn-in shift
 * never pushes content outside the available container bounds.
 */
fun clampBurnInOffsetPx(
    requestedOffset: Offset,
    containerSize: IntSize,
    contentSize: IntSize,
    safetyMarginPx: Float = 4f
): IntOffset {
    val maxSlackX = ((containerSize.width - contentSize.width) / 2f - safetyMarginPx).coerceAtLeast(0f)
    val maxSlackY = ((containerSize.height - contentSize.height) / 2f - safetyMarginPx).coerceAtLeast(0f)

    val maxAllowedX = mathMin(abs(requestedOffset.x), maxSlackX)
    val maxAllowedY = mathMin(abs(requestedOffset.y), maxSlackY)

    val clampedX = if (requestedOffset.x >= 0f) maxAllowedX else -maxAllowedX
    val clampedY = if (requestedOffset.y >= 0f) maxAllowedY else -maxAllowedY

    return IntOffset(x = clampedX.roundToInt(), y = clampedY.roundToInt())
}

@Composable
fun LandscapeClockLayout(
    uiState: ClockUiState,
    modifier: Modifier = Modifier,
    activeColor: Color = Color(0xFF00E5FF),
    ghostColor: Color = Color(0xFF112225)
) {
    val density = LocalDensity.current
    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    var contentSize by remember { mutableStateOf(IntSize.Zero) }

    val outerPadding: Dp = 24.dp

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged { containerSize = it }
            .padding(outerPadding),
        contentAlignment = Alignment.Center
    ) {
        val availableHeight = this@BoxWithConstraints.maxHeight
        val availableWidth = this@BoxWithConstraints.maxWidth

        // Target time digit height ~0.6x of available height, bounded by available width.
        // Time row has 4 digits (0.5625 * h each) + colon (0.25 * h) + gaps (~0.45 * h) ~= 2.95 * h.
        val heightDrivenTimeDp: Dp = availableHeight * 0.60f
        val widthDrivenTimeDp: Dp = availableWidth * 0.72f / 2.95f
        val timeDigitHeight: Dp = min(heightDrivenTimeDp, widthDrivenTimeDp)
            .coerceAtLeast(24.dp)

        val timeDigitWidth: Dp = timeDigitHeight * 0.5625f
        val colonWidth: Dp = timeDigitHeight * 0.25f
        val digitGap: Dp = timeDigitWidth * 0.09f
        val colonGap: Dp = timeDigitWidth * 0.09f

        // Date height ~0.5x of time digit height
        val dateDigitHeight: Dp = (timeDigitHeight * 0.50f).coerceAtLeast(14.dp)

        // Day label font size ~0.17x of time digit height (converted from dp to sp)
        val dayScaleDp: Dp = (timeDigitHeight * 0.17f).coerceAtLeast(10.dp)
        val dayFontSp = with(density) { dayScaleDp.toSp() }
        val daySpacing: Dp = (dayScaleDp * 0.22f).coerceAtLeast(2.dp)

        val safeTranslation = remember(uiState.burnInOffset, containerSize, contentSize) {
            clampBurnInOffsetPx(
                requestedOffset = uiState.burnInOffset,
                containerSize = containerSize,
                contentSize = contentSize
            )
        }

        Row(
            modifier = Modifier
                .onSizeChanged { contentSize = it }
                .graphicsLayer {
                    translationX = safeTranslation.x.toFloat()
                    translationY = safeTranslation.y.toFloat()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            DayOfWeekIndicator(
                activeDayIndex = uiState.dayOfWeekIndex,
                vertical = true,
                useCompactLabels = false,
                activeColor = activeColor,
                inactiveColor = ghostColor,
                fontSize = dayFontSp,
                spacing = daySpacing
            )

            Spacer(modifier = Modifier.width((timeDigitWidth * 0.28f).coerceAtLeast(10.dp)))

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
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

                Spacer(modifier = Modifier.height((timeDigitHeight * 0.10f).coerceAtLeast(8.dp)))

                DateRow(
                    formattedDate = uiState.formattedDate,
                    digitHeight = dateDigitHeight,
                    activeColor = activeColor,
                    ghostColor = ghostColor
                )
            }
        }
    }
}