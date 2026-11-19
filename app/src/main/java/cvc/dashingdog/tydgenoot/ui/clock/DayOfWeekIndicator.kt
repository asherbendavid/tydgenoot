package cvc.dashingdog.tydgenoot.ui.clock

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cvc.dashingdog.tydgenoot.model.DateFormatter

@Composable
fun DayOfWeekIndicator(
    activeDayIndex: Int,
    modifier: Modifier = Modifier,
    vertical: Boolean = true,
    useCompactLabels: Boolean = !vertical,
    labels: List<String> = if (useCompactLabels) {
        DateFormatter.mondayFirstCompactLabels
    } else {
        DateFormatter.mondayFirstLabels
    },
    activeColor: Color = Color(0xFF00E5FF),
    inactiveColor: Color = Color(0xFF112225),
    fontSize: TextUnit = 18.sp,
    spacing: Dp = 4.dp
) {
    val safeActiveIndex = activeDayIndex.coerceIn(0, (labels.size - 1).coerceAtLeast(0))

    if (vertical) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(spacing, Alignment.CenterVertically),
            horizontalAlignment = Alignment.Start
        ) {
            labels.forEachIndexed { index, label ->
                val isActive = index == safeActiveIndex
                Text(
                    text = label,
                    color = if (isActive) activeColor else inactiveColor,
                    fontSize = fontSize,
                    fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                    maxLines = 1,
                    softWrap = false
                )
            }
        }
    } else {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(spacing, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            labels.forEachIndexed { index, label ->
                val isActive = index == safeActiveIndex
                Text(
                    text = label,
                    color = if (isActive) activeColor else inactiveColor,
                    fontSize = fontSize,
                    fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                    maxLines = 1,
                    softWrap = false
                )
            }
        }
    }
}