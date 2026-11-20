package cvc.dashingdog.tydgenoot.data

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.themePreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "tydgenoot_theme_preferences"
)

object ThemeDefaults {
    const val CYAN_ARGB: Long = 0xFF00E5FFL
    const val TEAL_ARGB: Long = 0xFF1DE9B6L
    const val DEFAULT_ACTIVE_COLOR_ARGB: Long = CYAN_ARGB
    val DEFAULT_BACKGROUND: BackgroundPreset = BackgroundPreset.BLACK
    const val DEFAULT_SHOW_LEADING_ZERO: Boolean = true
}

enum class BackgroundPreset(val label: String, val colorArgb: Long) {
    BLACK("Pure Black", 0xFF000000L),
    NAVY("Deep Dark Navy", 0xFF060D1AL),
    CHARCOAL("Charcoal", 0xFF181A1DL);

    fun toComposeColor(): Color = Color(colorArgb)

    companion object {
        fun fromStoredName(raw: String?): BackgroundPreset {
            if (raw.isNullOrBlank()) return ThemeDefaults.DEFAULT_BACKGROUND
            return entries.firstOrNull { it.name.equals(raw.trim(), ignoreCase = true) }
                ?: ThemeDefaults.DEFAULT_BACKGROUND
        }
    }
}

data class ThemePreferences(
    val activeColorArgb: Long = ThemeDefaults.DEFAULT_ACTIVE_COLOR_ARGB,
    val backgroundPreset: BackgroundPreset = ThemeDefaults.DEFAULT_BACKGROUND,
    val showLeadingZero: Boolean = ThemeDefaults.DEFAULT_SHOW_LEADING_ZERO
) {
    fun activeComposeColor(): Color = Color(sanitizeColorArgb(activeColorArgb))

    /**
     * Derives a subtle dim "ghost" segment color blended against the active background.
     * Scales smoothly across bright and darker user-chosen segment colors so unlit
     * segments stay faintly visible without washing out.
     */
    fun ghostComposeColor(): Color {
        val active = activeComposeColor()
        val bg = backgroundPreset.toComposeColor()
        val lum = active.luminance()
        // Bright colors need lower alpha (~0.10-0.13) to stay subtle; darker colors need slightly higher (~0.18-0.22)
        val alpha = (0.22f - (lum * 0.11f)).coerceIn(0.10f, 0.24f)
        return Color(
            red = bg.red + (active.red - bg.red) * alpha,
            green = bg.green + (active.green - bg.green) * alpha,
            blue = bg.blue + (active.blue - bg.blue) * alpha,
            alpha = 1f
        )
    }

    companion object {
        fun sanitizeColorArgb(raw: Long): Long {
            // Force full opacity (0xFF alpha channel) while keeping RGB bits intact
            return (raw and 0x00FFFFFFL) or 0xFF000000L
        }
    }
}

interface ThemePreferencesRepository {
    val preferencesFlow: Flow<ThemePreferences>

    suspend fun setActiveColorArgb(colorArgb: Long)
    suspend fun setBackgroundPreset(preset: BackgroundPreset)
    suspend fun setShowLeadingZero(show: Boolean)
}

class DataStoreThemePreferencesRepository(
    private val dataStore: DataStore<Preferences>
) : ThemePreferencesRepository {

    private object Keys {
        val ACTIVE_COLOR_ARGB = longPreferencesKey("active_color_argb")
        val BACKGROUND_PRESET = stringPreferencesKey("background_preset")
        val SHOW_LEADING_ZERO = booleanPreferencesKey("show_leading_zero")
    }

    override val preferencesFlow: Flow<ThemePreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            val storedColor = prefs[Keys.ACTIVE_COLOR_ARGB] ?: ThemeDefaults.DEFAULT_ACTIVE_COLOR_ARGB
            val storedBg = BackgroundPreset.fromStoredName(prefs[Keys.BACKGROUND_PRESET])
            val storedLeadingZero = prefs[Keys.SHOW_LEADING_ZERO] ?: ThemeDefaults.DEFAULT_SHOW_LEADING_ZERO

            ThemePreferences(
                activeColorArgb = ThemePreferences.sanitizeColorArgb(storedColor),
                backgroundPreset = storedBg,
                showLeadingZero = storedLeadingZero
            )
        }

    override suspend fun setActiveColorArgb(colorArgb: Long) {
        val sanitized = ThemePreferences.sanitizeColorArgb(colorArgb)
        dataStore.edit { prefs ->
            prefs[Keys.ACTIVE_COLOR_ARGB] = sanitized
        }
    }

    override suspend fun setBackgroundPreset(preset: BackgroundPreset) {
        dataStore.edit { prefs ->
            prefs[Keys.BACKGROUND_PRESET] = preset.name
        }
    }

    override suspend fun setShowLeadingZero(show: Boolean) {
        dataStore.edit { prefs ->
            prefs[Keys.SHOW_LEADING_ZERO] = show
        }
    }
}