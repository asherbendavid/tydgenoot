package cvc.dashingdog.tydgenoot.ui.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cvc.dashingdog.tydgenoot.ui.clock.ClockUiState

@Composable
fun SettingsOverlay(
    uiState: ClockUiState,
    onDismiss: () -> Unit,
    onSetActiveColorArgb: (Long) -> Unit,
    onSetBackgroundPreset: (cvc.dashingdog.tydgenoot.data.BackgroundPreset) -> Unit,
    onSetShowLeadingZero: (Boolean) -> Unit,
    onOpenAbout: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BackHandler(enabled = true) {
        onDismiss()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xCC000000)),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(16.dp),
            shape = MaterialTheme.shapes.large,
            color = Color(0xFF12151A),
            contentColor = Color(0xFFF2F5F8)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.titleLarge
                    )
                    IconButton(onClick = onDismiss) {
                        Text(
                            text = "✕",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFFF2F5F8)
                        )
                    }
                }

                // Full controls (presets, HSV picker, background selector, leading zero, About) added in T-021.
                Text(
                    text = "Double-tap clock to open · Back or ✕ to close",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF9AA5B4)
                )
            }
        }
    }
}