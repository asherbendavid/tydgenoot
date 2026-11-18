# Tydgenoot

**Tydgenoot** (`cvc.dashingdog.tydgenoot`) is a minimalist, full-screen digital wall clock built with custom-rendered LED segments using Jetpack Compose. It is specifically designed to repurpose older Android tablets (such as the Lenovo TB3-710F running Android 5.0.1) into a dedicated, clean wall clock without network bloat, ambient clutter, or sleep/dim interruptions.

## Features (Completed & Tested)
- **Custom 7-Segment Canvas Engine:** Renders hours (`HH`), minutes (`MM`), and a 1 Hz blinking colon with crisp vector path geometry and illuminated/dim ghost segment states (supporting both leading-zero and blank leading-hour modes).
- **System Clock Sync:** Automatically tracks the Android system clock across minute updates, midnight rollovers, and external time adjustments.
- **Burn-In Protection:** Automatically applies a subtle ~24px pseudo-random shift every 5 minutes to prevent image retention on LCD panels.
- **Unobtrusive Operation:** Runs in full-screen sticky immersive mode with `FLAG_KEEP_SCREEN_ON` permanently active on a pure black (`#000000`) backdrop.

## Installation & Running
1. Clone or place the repository in an apostrophe-free path (e.g., `C:\Dev\Tydgenoot`).
2. Open the project in Android Studio (Quail or newer).
3. Run Gradle sync using the bundled version catalog and AGP 9.4.0 / Compose BOM 2024.09.00.
4. Build and sideload the APK onto your target Android device (API 21+).

## Limitations
- Currently displays `HH:MM` only; day-of-week (`MON`–`SUN`), full date (`dd MM yyyy`), orientation-specific layouts, and the double-tap Settings/About overlay are scheduled for upcoming releases.
- Designed strictly as a fixed wall clock display; does not include alarms, weather forecasts, or audio features.

## License & Attribution
- Licensed under the **BSD 3-Clause License** (see `LICENSE`).
- See `ATTRIBUTION_GUIDELINES.md` for non-binding recommendations regarding attribution.
- See `CREDITS.md` for project inspirations and library credits.