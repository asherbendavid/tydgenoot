package cvc.dashingdog.tydgenoot.ui.clock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cvc.dashingdog.tydgenoot.data.BackgroundPreset
import cvc.dashingdog.tydgenoot.data.ThemeDefaults
import cvc.dashingdog.tydgenoot.data.ThemePreferences
import cvc.dashingdog.tydgenoot.data.ThemePreferencesRepository
import cvc.dashingdog.tydgenoot.model.BurnInOffsetCalculator
import cvc.dashingdog.tydgenoot.model.DateFormatter
import cvc.dashingdog.tydgenoot.model.Offset
import cvc.dashingdog.tydgenoot.model.SegmentMapper
import cvc.dashingdog.tydgenoot.model.SegmentState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Calendar

data class ClockUiState(
    val hourTens: SegmentState = SegmentState.BLANK,
    val hourUnits: SegmentState = SegmentState.DIGIT_0,
    val minuteTens: SegmentState = SegmentState.DIGIT_0,
    val minuteUnits: SegmentState = SegmentState.DIGIT_0,
    val colonVisible: Boolean = true,
    val showLeadingZero: Boolean = ThemeDefaults.DEFAULT_SHOW_LEADING_ZERO,
    val activeColorArgb: Long = ThemeDefaults.DEFAULT_ACTIVE_COLOR_ARGB,
    val backgroundPreset: BackgroundPreset = ThemeDefaults.DEFAULT_BACKGROUND,
    val formattedDate: String = "01 01 2026",
    val dayOfWeekIndex: Int = 0,
    val burnInOffset: Offset = Offset(0f, 0f)
) {
    fun currentThemePreferences(): ThemePreferences = ThemePreferences(
        activeColorArgb = activeColorArgb,
        backgroundPreset = backgroundPreset,
        showLeadingZero = showLeadingZero
    )
}

class ClockViewModel(
    private val preferencesRepository: ThemePreferencesRepository? = null,
    private val timeProvider: () -> Long = { System.currentTimeMillis() },
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val coroutineScope: CoroutineScope? = null
) : ViewModel() {

    private val scope: CoroutineScope
        get() = coroutineScope ?: viewModelScope

    private val _uiState = MutableStateFlow(ClockUiState())
    val uiState: StateFlow<ClockUiState> = _uiState.asStateFlow()

    init {
        observePreferences()
        startClockTicker()
    }

    private fun observePreferences() {
        val repo = preferencesRepository ?: return
        scope.launch(dispatcher) {
            repo.preferencesFlow.collectLatest { prefs ->
                val leadingZeroChanged = _uiState.value.showLeadingZero != prefs.showLeadingZero
                _uiState.update {
                    it.copy(
                        activeColorArgb = prefs.activeColorArgb,
                        backgroundPreset = prefs.backgroundPreset,
                        showLeadingZero = prefs.showLeadingZero
                    )
                }
                if (leadingZeroChanged) {
                    updateTimeAndOffset()
                }
            }
        }
    }

    private fun startClockTicker() {
        scope.launch(dispatcher) {
            while (isActive) {
                updateTimeAndOffset()
                delay(500)
            }
        }
    }

    fun updateTimeAndOffset(currentTimeMs: Long = timeProvider()) {
        val calendar = Calendar.getInstance().apply { timeInMillis = currentTimeMs }
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        val currentShowZero = _uiState.value.showLeadingZero
        val (hTens, hUnits) = SegmentMapper.mapHour(hour, currentShowZero)
        val (mTens, mUnits) = SegmentMapper.mapMinute(minute)
        val dateInfo = DateFormatter.formatFromCalendar(calendar)
        val offset = BurnInOffsetCalculator.calculateOffset(currentTimeMs)

        val colonBlink = (second % 2 == 0)

        _uiState.update {
            it.copy(
                hourTens = hTens,
                hourUnits = hUnits,
                minuteTens = mTens,
                minuteUnits = mUnits,
                colonVisible = colonBlink,
                formattedDate = dateInfo.formattedDate,
                dayOfWeekIndex = dateInfo.dayOfWeekIndex,
                burnInOffset = offset
            )
        }
    }

    fun setShowLeadingZero(show: Boolean) {
        _uiState.update { it.copy(showLeadingZero = show) }
        updateTimeAndOffset()
        preferencesRepository?.let { repo ->
            scope.launch(dispatcher) {
                repo.setShowLeadingZero(show)
            }
        }
    }

    fun setActiveColorArgb(colorArgb: Long) {
        val sanitized = ThemePreferences.sanitizeColorArgb(colorArgb)
        _uiState.update { it.copy(activeColorArgb = sanitized) }
        preferencesRepository?.let { repo ->
            scope.launch(dispatcher) {
                repo.setActiveColorArgb(sanitized)
            }
        }
    }

    fun setBackgroundPreset(preset: BackgroundPreset) {
        _uiState.update { it.copy(backgroundPreset = preset) }
        preferencesRepository?.let { repo ->
            scope.launch(dispatcher) {
                repo.setBackgroundPreset(preset)
            }
        }
    }
}