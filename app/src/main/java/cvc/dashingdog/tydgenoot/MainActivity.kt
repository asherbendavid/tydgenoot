package cvc.dashingdog.tydgenoot

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import cvc.dashingdog.tydgenoot.about.AboutScreen
import cvc.dashingdog.tydgenoot.data.DataStoreThemePreferencesRepository
import cvc.dashingdog.tydgenoot.data.themePreferencesDataStore
import cvc.dashingdog.tydgenoot.ui.clock.ClockViewModel
import cvc.dashingdog.tydgenoot.ui.clock.LandscapeClockLayout
import cvc.dashingdog.tydgenoot.ui.clock.PortraitClockLayout
import cvc.dashingdog.tydgenoot.ui.settings.SettingsOverlay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ensure screen stays awake continuously per specs & constitution
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Enable immersive full-screen mode
        hideSystemUI()

        setContent {
            MaterialTheme {
                ClockScreenRoot()
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

private fun resolveInstalledVersionName(context: Context): String {
    return try {
        val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.PackageInfoFlags.of(0)
            )
        } else {
            @Suppress("DEPRECATION")
            context.packageManager.getPackageInfo(context.packageName, 0)
        }
        packageInfo.versionName ?: "unknown"
    } catch (_: Exception) {
        "unknown"
    }
}

private class ClockViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClockViewModel::class.java)) {
            val repository = DataStoreThemePreferencesRepository(context.applicationContext.themePreferencesDataStore)
            return ClockViewModel(preferencesRepository = repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

@Composable
private fun ClockScreenRoot() {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val versionName = remember(context) { resolveInstalledVersionName(context) }
    val factory = remember(context) { ClockViewModelFactory(context) }
    val viewModel: ClockViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsState()

    var isSettingsOpen by rememberSaveable { mutableStateOf(false) }
    var isAboutOpen by rememberSaveable { mutableStateOf(false) }

    val themePrefs = remember(uiState.activeColorArgb, uiState.backgroundPreset, uiState.showLeadingZero) {
        uiState.currentThemePreferences()
    }
    val activeColor = remember(themePrefs) { themePrefs.activeComposeColor() }
    val ghostColor = remember(themePrefs) { themePrefs.ghostComposeColor() }
    val backgroundColor = remember(uiState.backgroundPreset) { uiState.backgroundPreset.toComposeColor() }

    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        isSettingsOpen = true
                    }
                )
            }
    ) {
        if (isPortrait) {
            PortraitClockLayout(
                uiState = uiState,
                activeColor = activeColor,
                ghostColor = ghostColor,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            LandscapeClockLayout(
                uiState = uiState,
                activeColor = activeColor,
                ghostColor = ghostColor,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Clock version label retained permanently per developer preference
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = "Tydgenoot v$versionName",
                color = ghostColor,
                fontSize = 12.sp
            )
        }

        if (isSettingsOpen) {
            SettingsOverlay(
                uiState = uiState,
                onDismiss = {
                    isAboutOpen = false
                    isSettingsOpen = false
                },
                onSetActiveColorArgb = viewModel::setActiveColorArgb,
                onSetBackgroundPreset = viewModel::setBackgroundPreset,
                onSetShowLeadingZero = viewModel::setShowLeadingZero,
                onOpenAbout = { isAboutOpen = true }
            )
        }

        if (isAboutOpen) {
            AboutScreen(
                accentColor = activeColor,
                onDismiss = { isAboutOpen = false }
            )
        }
    }
}