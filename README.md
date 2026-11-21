# Tydgenoot

**Tydgenoot** (`cvc.dashingdog.tydgenoot`) is a minimalist, full-screen digital wall clock built with custom-rendered LED segments using Jetpack Compose. It is specifically designed to repurpose older Android tablets (such as the Lenovo TB3-710F running Android 5.0.1) into a dedicated, clean wall clock without network bloat, ambient clutter, or sleep/dim interruptions[cite: 2].

## Features
- **Custom 7-Segment Canvas Engine:** Renders hours (`HH`), minutes (`MM`), full date (`dd MM yyyy`), and a blinking colon with custom vector segment geometry and illuminated/dim ghost segment states.
- **Day-of-Week Indicator:** Displays Monday through Sunday (`MON`–`SUN` vertically in landscape; compact `M`–`S` across the top in portrait) with the active day illuminated and inactive days dimmed.
- **Responsive Orientation Layouts:** Adapts seamlessly between a classic landscape wall-clock arrangement (days left, `HH:MM` top-right, `dd MM yyyy` bottom-right) and a portrait vertical stack (days top, `HH:MM` centered, `dd MM yyyy` bottom) without losing display state.
- **Theme Presets & Custom HSV Picker:** Includes Cyan (`#00E5FF`) and Teal (`#1DE9B6`) segment presets, a built-in Hue/Saturation/Value (HSV) slider picker with a live 7-segment preview swatch, and background presets for Pure Black (`#000000`), Deep Dark Navy (`#060D1A`), and Charcoal (`#181A1D`).
- **Leading Hour Zero Toggle:** Switch single-digit hours between leading zero (`08:30`) and blank/dimmed leading zero (` 8:30`) in Settings.
- **Double-Tap Settings & About Screen:** Double-tap the clock face to open Settings (ignoring single taps; dismissible via `✕` or system Back), and open **About** to view packaged `ABOUT.md`, `CREDITS.md`, and `LICENSE` alongside the app name and version.
- **System Clock Sync & Burn-In Protection:** Tracks the Android system clock across minute updates and midnight rollovers while applying periodic subtle position shifts to reduce display retention.
- **Unobtrusive Operation:** Runs in full-screen sticky immersive mode with `FLAG_KEEP_SCREEN_ON` kept active.

## Installation & Running
1. Clone or place the repository in an apostrophe-free path (e.g., `C:\Dev\Tydgenoot` or `C:\Android\Projects\Tydgenoot`).
2. Open the project in Android Studio (Quail or newer).
3. Sync Gradle using the bundled version catalog and AGP 9.4.0 / Compose BOM 2024.09.00.
4. Build and install the APK onto your target Android device (API 21+).

## Scope
- Designed strictly as a fixed wall clock display; does not include alarms, weather forecasts, timers, or audio features.

## License & Attribution
- Licensed under the **BSD 3-Clause License** (see `LICENSE`).
- See `ATTRIBUTION_GUIDELINES.md` for non-binding recommendations regarding attribution.
- See `CREDITS.md` for project inspirations and library credits.