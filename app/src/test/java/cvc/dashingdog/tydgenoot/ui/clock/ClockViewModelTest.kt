package cvc.dashingdog.tydgenoot.ui.clock

import cvc.dashingdog.tydgenoot.data.BackgroundPreset
import cvc.dashingdog.tydgenoot.data.ThemeDefaults
import cvc.dashingdog.tydgenoot.data.ThemePreferences
import cvc.dashingdog.tydgenoot.data.ThemePreferencesRepository
import cvc.dashingdog.tydgenoot.model.SegmentState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import java.util.Calendar

private class FakeThemePreferencesRepository(
    initial: ThemePreferences = ThemePreferences()
) : ThemePreferencesRepository {
    private val state = MutableStateFlow(initial)
    override val preferencesFlow: Flow<ThemePreferences> = state

    val current: ThemePreferences
        get() = state.value

    override suspend fun setActiveColorArgb(colorArgb: Long) {
        state.value = state.value.copy(
            activeColorArgb = ThemePreferences.sanitizeColorArgb(colorArgb)
        )
    }

    override suspend fun setBackgroundPreset(preset: BackgroundPreset) {
        state.value = state.value.copy(backgroundPreset = preset)
    }

    override suspend fun setShowLeadingZero(show: Boolean) {
        state.value = state.value.copy(showLeadingZero = show)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class ClockViewModelTest {

    @Test
    fun observesInitialRepositoryPreferencesOnStartup() {
        val dispatcher = StandardTestDispatcher()
        val testScope = TestScope(dispatcher)
        try {
            val fakeRepo = FakeThemePreferencesRepository(
                ThemePreferences(
                    activeColorArgb = ThemeDefaults.TEAL_ARGB,
                    backgroundPreset = BackgroundPreset.NAVY,
                    showLeadingZero = false
                )
            )
            val morningEightThirtyMs = Calendar.getInstance().apply {
                set(2026, Calendar.NOVEMBER, 20, 8, 30, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

            val vm = ClockViewModel(
                preferencesRepository = fakeRepo,
                timeProvider = { morningEightThirtyMs },
                dispatcher = dispatcher,
                coroutineScope = testScope
            )
            dispatcher.scheduler.runCurrent()

            assertEquals(ThemeDefaults.TEAL_ARGB, vm.uiState.value.activeColorArgb)
            assertEquals(BackgroundPreset.NAVY, vm.uiState.value.backgroundPreset)
            assertFalse(vm.uiState.value.showLeadingZero)
            assertEquals(SegmentState.BLANK, vm.uiState.value.hourTens)
            assertEquals(SegmentState.DIGIT_8, vm.uiState.value.hourUnits)
        } finally {
            testScope.cancel()
        }
    }

    @Test
    fun settersUpdateUiStateAndPersistToFakeRepository() {
        val dispatcher = StandardTestDispatcher()
        val testScope = TestScope(dispatcher)
        try {
            val fakeRepo = FakeThemePreferencesRepository()
            val vm = ClockViewModel(
                preferencesRepository = fakeRepo,
                timeProvider = { 1_795_190_000_000L },
                dispatcher = dispatcher,
                coroutineScope = testScope
            )
            dispatcher.scheduler.runCurrent()

            vm.setActiveColorArgb(ThemeDefaults.TEAL_ARGB)
            vm.setBackgroundPreset(BackgroundPreset.CHARCOAL)
            vm.setShowLeadingZero(false)
            dispatcher.scheduler.runCurrent()

            assertEquals(ThemeDefaults.TEAL_ARGB, vm.uiState.value.activeColorArgb)
            assertEquals(BackgroundPreset.CHARCOAL, vm.uiState.value.backgroundPreset)
            assertFalse(vm.uiState.value.showLeadingZero)

            assertEquals(ThemeDefaults.TEAL_ARGB, fakeRepo.current.activeColorArgb)
            assertEquals(BackgroundPreset.CHARCOAL, fakeRepo.current.backgroundPreset)
            assertFalse(fakeRepo.current.showLeadingZero)
        } finally {
            testScope.cancel()
        }
    }
}