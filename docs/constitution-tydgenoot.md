# Tydgenoot — Constitution

*Workflow set v2.0. Project-wide rules that apply regardless of which feature is being built.*

## Platform & stack
- Kotlin, Android, Jetpack Compose.
- Minimum supported version: API 21 (Android 5.0.1, matching the target Lenovo TB3-710F).
- Key dependencies: Android SDK, Jetpack Compose (UI & Canvas), Kotlin Coroutines, AndroidX ViewModel, and Jetpack DataStore (Preferences). Plus a lightweight Compose markdown library for the About screen.

## Guidelines
- guideline-android.md

## Architecture rules
- Screen state follows the ViewModel + StateFlow pattern.
- All clock digits and colons are drawn programmatically via Jetpack Compose `Canvas` segments (7-segment geometry).
- Time and date values are driven strictly by the Android system clock.
- The root project directory must live at an apostrophe-free path on Windows (e.g., C:\Dev\Tydgenoot).

## Testing rules
- Logic decoupled from Android UI/DataStore is tested via JVM unit tests.
- Any test touching Jetpack DataStore must run as an instrumented test on a physical device or emulator.

## Reliability tiers
- Critical: none.
- Important: correct time/date rendering, screen wake lock (`FLAG_KEEP_SCREEN_ON`), DataStore color persistence, orientation state preservation, Settings overlay back-press handling.
- Cosmetic: segment spacing, ghost-segment alpha tuning, burn-in shift offset math, About screen layout.

## Global must-never rules
- Never display temperature, alarm indicators, or DST badges.
- Never allow the screen to turn off or sleep while the clock screen is active.
- Never leave temporary debug screens or diagnostic helpers in a release build.