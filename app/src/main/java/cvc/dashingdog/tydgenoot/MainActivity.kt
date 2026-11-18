package cvc.dashingdog.tydgenoot

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cvc.dashingdog.tydgenoot.ui.clock.BlinkingColon
import cvc.dashingdog.tydgenoot.ui.clock.ClockViewModel
import cvc.dashingdog.tydgenoot.ui.clock.SegmentDigit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ensure screen stays awake continuously per specs & constitution
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Enable immersive full-screen mode
        hideSystemUI()

        setContent {
            MaterialTheme {
                val viewModel: ClockViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsState()

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF000000)), // Pure black background
                    contentAlignment = Alignment.Center
                ) {
                    // Apply burn-in shift offset
                    Row(
                        modifier = Modifier
                            .offset(
                                x = uiState.burnInOffset.x.dp,
                                y = uiState.burnInOffset.y.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SegmentDigit(
                            segmentState = uiState.hourTens,
                            modifier = Modifier.size(width = 90.dp, height = 160.dp)
                        )
                        SegmentDigit(
                            segmentState = uiState.hourUnits,
                            modifier = Modifier.size(width = 90.dp, height = 160.dp).padding(start = 8.dp)
                        )
                        BlinkingColon(
                            visible = uiState.colonVisible,
                            modifier = Modifier.size(width = 40.dp, height = 160.dp).padding(horizontal = 8.dp)
                        )
                        SegmentDigit(
                            segmentState = uiState.minuteTens,
                            modifier = Modifier.size(width = 90.dp, height = 160.dp)
                        )
                        SegmentDigit(
                            segmentState = uiState.minuteUnits,
                            modifier = Modifier.size(width = 90.dp, height = 160.dp).padding(start = 8.dp)
                        )
                    }

                    // Minimal About stub in bottom corner (T-015)
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Text(
                            text = "Tydgenoot v0.1.0",
                            color = Color(0xFF224448),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )
    }
}