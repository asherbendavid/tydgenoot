package cvc.dashingdog.tydgenoot.ui.settings

import android.graphics.Color as AndroidColor
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cvc.dashingdog.tydgenoot.data.BackgroundPreset
import cvc.dashingdog.tydgenoot.data.ThemeDefaults
import cvc.dashingdog.tydgenoot.data.ThemePreferences
import cvc.dashingdog.tydgenoot.model.SegmentState
import cvc.dashingdog.tydgenoot.ui.clock.ClockUiState
import cvc.dashingdog.tydgenoot.ui.clock.SegmentDigit
import kotlin.math.roundToInt

private fun argbToHsv(colorArgb: Long): FloatArray {
    val hsv = FloatArray(3)
    AndroidColor.colorToHSV(ThemePreferences.sanitizeColorArgb(colorArgb).toInt(), hsv)
    // Normalize hue to [0f, 360f)
    hsv[0] = ((hsv[0] % 360f) + 360f) % 360f
    hsv[1] = hsv[1].coerceIn(0f, 1f)
    hsv[2] = hsv[2].coerceIn(0f, 1f)
    return hsv
}

private fun hsvToOpaqueArgb(hue: Float, saturation: Float, value: Float): Long {
    val clampedHsv = floatArrayOf(
        ((hue % 360f) + 360f) % 360f,
        saturation.coerceIn(0f, 1f),
        value.coerceIn(0f, 1f)
    )
    val intColor = AndroidColor.HSVToColor(255, clampedHsv)
    return ThemePreferences.sanitizeColorArgb(Color(intColor).toArgb().toLong())
}

@Composable
fun SettingsOverlay(
    uiState: ClockUiState,
    onDismiss: () -> Unit,
    onSetActiveColorArgb: (Long) -> Unit,
    onSetBackgroundPreset: (BackgroundPreset) -> Unit,
    onSetShowLeadingZero: (Boolean) -> Unit,
    onOpenAbout: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BackHandler(enabled = true) {
        onDismiss()
    }

    val sanitizedActiveArgb = remember(uiState.activeColorArgb) {
        ThemePreferences.sanitizeColorArgb(uiState.activeColorArgb)
    }

    var hue by remember { mutableFloatStateOf(argbToHsv(sanitizedActiveArgb)[0]) }
    var saturation by remember { mutableFloatStateOf(argbToHsv(sanitizedActiveArgb)[1]) }
    var value by remember { mutableFloatStateOf(argbToHsv(sanitizedActiveArgb)[2]) }
    var lastSyncedArgb by remember { mutableLongStateOf(sanitizedActiveArgb) }

    LaunchedEffect(sanitizedActiveArgb) {
        val currentPickerArgb = hsvToOpaqueArgb(hue, saturation, value)
        if (sanitizedActiveArgb != lastSyncedArgb && sanitizedActiveArgb != currentPickerArgb) {
            val hsv = argbToHsv(sanitizedActiveArgb)
            hue = hsv[0]
            saturation = hsv[1]
            value = hsv[2]
        }
        lastSyncedArgb = sanitizedActiveArgb
    }

    val previewPrefs = remember(sanitizedActiveArgb, uiState.backgroundPreset, uiState.showLeadingZero) {
        ThemePreferences(
            activeColorArgb = sanitizedActiveArgb,
            backgroundPreset = uiState.backgroundPreset,
            showLeadingZero = uiState.showLeadingZero
        )
    }
    val previewActiveColor = remember(previewPrefs) { previewPrefs.activeComposeColor() }
    val previewGhostColor = remember(previewPrefs) { previewPrefs.ghostComposeColor() }
    val previewBgColor = remember(uiState.backgroundPreset) { uiState.backgroundPreset.toComposeColor() }

    val panelSurface = Color(0xFF14181F)
    val cardSurface = Color(0xFF1C212B)
    val textPrimary = Color(0xFFF3F6FA)
    val textSecondary = Color(0xFFA8B3C2)
    val accentColor = previewActiveColor

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xD9000000))
            .clickable(enabled = false, onClick = {}),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(16.dp),
            color = panelSurface,
            contentColor = textPrimary,
            border = BorderStroke(1.dp, Color(0xFF2B3240))
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header with Close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.titleLarge,
                        color = textPrimary
                    )
                    IconButton(onClick = onDismiss) {
                        Text(
                            text = "✕",
                            style = MaterialTheme.typography.titleMedium,
                            color = textPrimary
                        )
                    }
                }

                HorizontalDivider(color = Color(0xFF282F3C))

                // Segment Color Presets + Live Preview Swatch
                Text(
                    text = "Segment Color",
                    style = MaterialTheme.typography.titleSmall,
                    color = textSecondary
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PresetColorChip(
                            label = "Cyan",
                            colorArgb = ThemeDefaults.CYAN_ARGB,
                            selected = sanitizedActiveArgb == ThemeDefaults.CYAN_ARGB,
                            accentColor = accentColor,
                            onClick = {
                                val hsv = argbToHsv(ThemeDefaults.CYAN_ARGB)
                                hue = hsv[0]
                                saturation = hsv[1]
                                value = hsv[2]
                                lastSyncedArgb = ThemeDefaults.CYAN_ARGB
                                onSetActiveColorArgb(ThemeDefaults.CYAN_ARGB)
                            }
                        )
                        PresetColorChip(
                            label = "Teal",
                            colorArgb = ThemeDefaults.TEAL_ARGB,
                            selected = sanitizedActiveArgb == ThemeDefaults.TEAL_ARGB,
                            accentColor = accentColor,
                            onClick = {
                                val hsv = argbToHsv(ThemeDefaults.TEAL_ARGB)
                                hue = hsv[0]
                                saturation = hsv[1]
                                value = hsv[2]
                                lastSyncedArgb = ThemeDefaults.TEAL_ARGB
                                onSetActiveColorArgb(ThemeDefaults.TEAL_ARGB)
                            }
                        )
                    }

                    // Live 7-segment preview swatch
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(width = 68.dp, height = 56.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(previewBgColor)
                                .border(1.dp, Color(0xFF343D4E), RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            SegmentDigit(
                                segmentState = SegmentState.DIGIT_8,
                                activeColor = previewActiveColor,
                                ghostColor = previewGhostColor,
                                modifier = Modifier.size(width = 24.dp, height = 42.dp)
                            )
                        }
                        Text(
                            text = "#${sanitizedActiveArgb.and(0x00FFFFFFL).toString(16).uppercase().padStart(6, '0')}",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            color = textSecondary
                        )
                    }
                }

                // Built-in Compose HSV Color Picker
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = cardSurface
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Custom HSV Color",
                            style = MaterialTheme.typography.bodyMedium,
                            color = textPrimary
                        )

                        HsvSliderRow(
                            label = "H",
                            valueText = "${hue.roundToInt()}°",
                            value = hue,
                            valueRange = 0f..360f,
                            sliderColor = accentColor,
                            trackColor = Color(0xFF333C4D),
                            onValueChange = { newHue ->
                                hue = newHue
                                val nextArgb = hsvToOpaqueArgb(newHue, saturation, value)
                                lastSyncedArgb = nextArgb
                                onSetActiveColorArgb(nextArgb)
                            }
                        )

                        HsvSliderRow(
                            label = "S",
                            valueText = "${(saturation * 100f).roundToInt()}%",
                            value = saturation,
                            valueRange = 0f..1f,
                            sliderColor = accentColor,
                            trackColor = Color(0xFF333C4D),
                            onValueChange = { newSat ->
                                saturation = newSat
                                val nextArgb = hsvToOpaqueArgb(hue, newSat, value)
                                lastSyncedArgb = nextArgb
                                onSetActiveColorArgb(nextArgb)
                            }
                        )

                        HsvSliderRow(
                            label = "V",
                            valueText = "${(value * 100f).roundToInt()}%",
                            value = value,
                            valueRange = 0f..1f,
                            sliderColor = accentColor,
                            trackColor = Color(0xFF333C4D),
                            onValueChange = { newVal ->
                                value = newVal
                                val nextArgb = hsvToOpaqueArgb(hue, saturation, newVal)
                                lastSyncedArgb = nextArgb
                                onSetActiveColorArgb(nextArgb)
                            }
                        )
                    }
                }

                // Background Selector
                Text(
                    text = "Background",
                    style = MaterialTheme.typography.titleSmall,
                    color = textSecondary
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BackgroundPreset.entries.forEach { preset ->
                        val isSelected = uiState.backgroundPreset == preset
                        FilterChip(
                            selected = isSelected,
                            onClick = { onSetBackgroundPreset(preset) },
                            label = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(12.dp)
                                            .clip(CircleShape)
                                            .background(preset.toComposeColor())
                                            .border(1.dp, Color(0xFF647084), CircleShape)
                                    )
                                    Text(
                                        text = preset.label,
                                        maxLines = 1,
                                        softWrap = false,
                                        fontSize = 12.sp
                                    )
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = cardSurface,
                                labelColor = textPrimary,
                                selectedContainerColor = accentColor.copy(alpha = 0.22f),
                                selectedLabelColor = textPrimary
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = Color(0xFF343D4E),
                                selectedBorderColor = accentColor
                            )
                        )
                    }
                }

                // Leading-Hour Zero Switch
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(role = Role.Switch) {
                            onSetShowLeadingZero(!uiState.showLeadingZero)
                        },
                    shape = RoundedCornerShape(12.dp),
                    color = cardSurface
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = "Leading Hour Zero",
                                style = MaterialTheme.typography.bodyMedium,
                                color = textPrimary
                            )
                            Text(
                                text = if (uiState.showLeadingZero) "Showing 08:30" else "Showing  8:30",
                                style = MaterialTheme.typography.bodySmall,
                                color = textSecondary
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Switch(
                            checked = uiState.showLeadingZero,
                            onCheckedChange = onSetShowLeadingZero,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFF0B0E14),
                                checkedTrackColor = accentColor,
                                uncheckedThumbColor = textSecondary,
                                uncheckedTrackColor = Color(0xFF2D3544)
                            )
                        )
                    }
                }

                // About Button
                OutlinedButton(
                    onClick = onOpenAbout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFF3A4457)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = textPrimary
                    )
                ) {
                    Text(
                        text = "About",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

@Composable
private fun PresetColorChip(
    label: String,
    colorArgb: Long,
    selected: Boolean,
    accentColor: Color,
    onClick: () -> Unit
) {
    val swatchColor = Color(colorArgb)
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(swatchColor)
                        .border(1.dp, Color.White.copy(alpha = 0.35f), CircleShape)
                )
                Text(text = label, maxLines = 1, softWrap = false)
            }
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Color(0xFF1C212B),
            labelColor = Color(0xFFF3F6FA),
            selectedContainerColor = accentColor.copy(alpha = 0.22f),
            selectedLabelColor = Color(0xFFF3F6FA)
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = Color(0xFF343D4E),
            selectedBorderColor = accentColor
        )
    )
}

@Composable
private fun HsvSliderRow(
    label: String,
    valueText: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    sliderColor: Color,
    trackColor: Color,
    onValueChange: (Float) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = label,
            fontFamily = FontFamily.Monospace,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFFA8B3C2),
            modifier = Modifier.width(16.dp)
        )
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            modifier = Modifier.weight(1f),
            colors = SliderDefaults.colors(
                thumbColor = sliderColor,
                activeTrackColor = sliderColor,
                inactiveTrackColor = trackColor
            )
        )
        Text(
            text = valueText,
            fontFamily = FontFamily.Monospace,
            fontSize = 12.sp,
            color = Color(0xFFF3F6FA),
            modifier = Modifier.width(44.dp)
        )
    }
}