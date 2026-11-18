package cvc.dashingdog.tydgenoot.ui.clock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cvc.dashingdog.tydgenoot.model.BurnInOffsetCalculator
import cvc.dashingdog.tydgenoot.model.Offset
import cvc.dashingdog.tydgenoot.model.SegmentState
import cvc.dashingdog.tydgenoot.model.SegmentMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    val showLeadingZero: Boolean = true,
    val burnInOffset: Offset = Offset(0f, 0f)
)

class ClockViewModel(
    private val timeProvider: () -> Long = { System.currentTimeMillis() },
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClockUiState())
    val uiState: StateFlow<ClockUiState> = _uiState.asStateFlow()

    init {
        startClockTicker()
    }

    private fun startClockTicker() {
        // Use viewModelScope (which uses Main dispatcher by default).
        // In JVM unit tests, injecting a test dispatcher or guard prevents Looper missing errors.
        viewModelScope.launch(dispatcher) {
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
        val offset = BurnInOffsetCalculator.calculateOffset(currentTimeMs)

        val colonBlink = (second % 2 == 0)

        _uiState.update {
            it.copy(
                hourTens = hTens,
                hourUnits = hUnits,
                minuteTens = mTens,
                minuteUnits = mUnits,
                colonVisible = colonBlink,
                burnInOffset = offset
            )
        }
    }

    fun setShowLeadingZero(show: Boolean) {
        _uiState.update { it.copy(showLeadingZero = show) }
        updateTimeAndOffset()
    }
}