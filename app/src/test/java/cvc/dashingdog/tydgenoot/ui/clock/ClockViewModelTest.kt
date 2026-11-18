package cvc.dashingdog.tydgenoot.ui.clock

import cvc.dashingdog.tydgenoot.model.SegmentState
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Calendar

class ClockViewModelTest {

    @Test
    fun testViewModelSystemTimeSyncAndMidnightRollover() {
        val calendar = Calendar.getInstance().apply {
            set(2026, Calendar.NOVEMBER, 18, 23, 59, 30)
            set(Calendar.MILLISECOND, 0)
        }
        // Pass Dispatchers.Unconfined so viewModelScope launches immediately on the test thread
        val viewModel = ClockViewModel(
            timeProvider = { calendar.timeInMillis },
            dispatcher = Dispatchers.Unconfined
        )

        viewModel.updateTimeAndOffset(calendar.timeInMillis)
        var state = viewModel.uiState.value
        assertEquals(SegmentState.DIGIT_2, state.hourTens)
        assertEquals(SegmentState.DIGIT_3, state.hourUnits)
        assertEquals(SegmentState.DIGIT_5, state.minuteTens)
        assertEquals(SegmentState.DIGIT_9, state.minuteUnits)

        // Simulate midnight rollover (00:00)
        calendar.set(2026, Calendar.NOVEMBER, 19, 0, 0, 0)
        viewModel.updateTimeAndOffset(calendar.timeInMillis)
        state = viewModel.uiState.value
        assertEquals(SegmentState.DIGIT_0, state.hourTens)
        assertEquals(SegmentState.DIGIT_0, state.hourUnits)
        assertEquals(SegmentState.DIGIT_0, state.minuteTens)
        assertEquals(SegmentState.DIGIT_0, state.minuteUnits)
    }
}