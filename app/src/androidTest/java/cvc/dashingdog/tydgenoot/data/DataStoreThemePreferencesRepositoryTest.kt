package cvc.dashingdog.tydgenoot.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.util.UUID

/**
 * Instrumented test for [DataStoreThemePreferencesRepository].
 * Must run on a physical Android device or emulator (never as a Windows JVM test).
 */
@RunWith(AndroidJUnit4::class)
class DataStoreThemePreferencesRepositoryTest {

    private lateinit var context: Context
    private lateinit var testScope: CoroutineScope
    private lateinit var testFile: File
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var repository: DataStoreThemePreferencesRepository

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        testScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        testFile = context.preferencesDataStoreFile("test_theme_prefs_${UUID.randomUUID()}")
        dataStore = PreferenceDataStoreFactory.create(
            scope = testScope,
            produceFile = { testFile }
        )
        repository = DataStoreThemePreferencesRepository(dataStore)
    }

    @After
    fun tearDown() {
        testScope.cancel()
        if (testFile.exists()) {
            testFile.delete()
        }
    }

    @Test
    fun freshInstall_emitsExpectedDefaults() = runBlocking {
        val prefs = repository.preferencesFlow.first()
        assertEquals(ThemeDefaults.CYAN_ARGB, prefs.activeColorArgb)
        assertEquals(BackgroundPreset.BLACK, prefs.backgroundPreset)
        assertTrue(prefs.showLeadingZero)
    }

    @Test
    fun updatesPersistAndSurviveNewRepositoryInstance() = runBlocking {
        repository.setActiveColorArgb(ThemeDefaults.TEAL_ARGB)
        repository.setBackgroundPreset(BackgroundPreset.NAVY)
        repository.setShowLeadingZero(false)

        // Read back through a fresh repository wrapper backed by the same DataStore file
        val secondRepo = DataStoreThemePreferencesRepository(dataStore)
        val reloaded = secondRepo.preferencesFlow.first()

        assertEquals(ThemeDefaults.TEAL_ARGB, reloaded.activeColorArgb)
        assertEquals(BackgroundPreset.NAVY, reloaded.backgroundPreset)
        assertFalse(reloaded.showLeadingZero)
    }

    @Test
    fun invalidBackgroundName_fallsBackSafelyToDefaultBlack() = runBlocking {
        dataStore.edit { raw ->
            raw[stringPreferencesKey("background_preset")] = "CORRUPT_VALUE"
        }

        val prefs = repository.preferencesFlow.first()
        assertEquals(BackgroundPreset.BLACK, prefs.backgroundPreset)
    }

    @Test
    fun transparentColorInput_isSanitizedToOpaqueRgb() = runBlocking {
        // Pass an ARGB value with 0x00 alpha
        repository.setActiveColorArgb(0x00FF5500L)

        val prefs = repository.preferencesFlow.first()
        assertEquals(0xFFFF5500L, prefs.activeColorArgb)
    }
}