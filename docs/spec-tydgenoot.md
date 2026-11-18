# Tydgenoot — Spec

*What the product must do, and why. Frozen when Phase 0 is accepted. After
that it changes only through logged amendments.*

## Identity
- Name: Tydgenoot
- App ID: cvc.dashingdog.tydgenoot
- Icon concept: A minimalist monogram featuring a bold, sharp-cornered letter "T" formed out of two illuminated cyan LED bar segments against a dark navy background.
- A dedicated, distraction-free full-screen digital wall clock built with custom-rendered LED segments for repurposed Android tablets.

## Problem
- Repurposing an older Android 5.0.1 tablet (Lenovo TB3-710F) as a dedicated wall clock requires a high-contrast, burn-in-safe, full-screen display without ads, alarms, weather clutter, or network bloat. Existing clock apps either require newer Android versions, clutter the screen with unused features, or rely on font files that scale inconsistently.

## Happy path (end to end)
1. Launch: App opens directly into full-screen immersive mode with `FLAG_KEEP_SCREEN_ON` active, displaying the current system time (`HH:MM`), highlighted day of the week (`MON`–`SUN`), and full date (`dd MM yyyy`) using custom-rendered LED segments.
2. Continuous operation: The colon blinks once per second, the time/date updates automatically with the system clock, and the entire display subtly shifts position (~24px) every 5 minutes to prevent image retention on the LCD panel.
3. Orientation change: Rotating the tablet between landscape and portrait seamlessly reflows the layout while preserving proportional segment scaling and current settings.
4. Customization: User double-taps anywhere on the clock face to open the Settings overlay, selects an active segment color (default Cyan/Teal, or via color picker) and a background color (`#000000` Black, Deep Dark Navy, or Charcoal), and closes Settings to return immediately to the clock.

## Input format
- n/a: the app takes no external file or network inputs. Its only inputs are the Android system clock, device orientation, user touch gestures (double-tap), and persisted user color preferences.

## Features

### Segmented Clock & Date Display
**What:** Renders hours and minutes (`HH:MM` with a blinking colon), a 7-day vertical or horizontal indicator (`MON` through `SUN` with the active day illuminated and inactive days dimmed), and the full date formatted as `dd MM yyyy` (including the 4-digit year to balance visual space). All digits are drawn using custom 7-segment geometry with illuminated active segments and faintly visible dim "ghost" segments.
**Why:** Delivers authentic hardware LED clock aesthetics that scale cleanly to any tablet resolution without font-rendering artifacts.
**Must never:**
- Render temperature readouts, alarm icons/indicators, or a DST indicator.
- Use `.ttf`/`.otf` seven-segment fonts for the clock or date digits.
- Clip or overlap digits in either landscape or portrait orientation.
**Edge cases:**
- Single-digit hours/days/months (leading zero handling vs. blank leading segment consistency).
- Midnight rollover (`23:59` to `00:00`) updating both the date (`dd MM yyyy`) and the highlighted day of the week simultaneously.
- System clock changes (manual adjustment or automatic network/GPS time sync).

### Responsive Orientation Layouts (Landscape & Portrait)
**What:** Supports both landscape and portrait orientations. Landscape mirrors the classic wall-clock arrangement (days of the week stacked on the left, large `HH:MM` on the top right, `dd MM yyyy` on the bottom right). Portrait reflows the components vertically (days of the week across the top, large `HH:MM` centered, `dd MM yyyy` along the bottom).
**Why:** Allows the tablet to be mounted horizontally or vertically depending on wall space and charging cable routing.
**Must never:**
- Lock the app to a single screen orientation.
- Lose active theme settings or reset the display state during rotation.
**Edge cases:**
- Rapid back-to-back orientation flips on older API 21 hardware.

### Immersive Display & LCD Burn-In Protection
**What:** Runs in full-screen immersive mode (hiding system status and navigation bars), keeps the screen awake continuously via `FLAG_KEEP_SCREEN_ON`, and applies a periodic ~24px pseudo-random translation offset every 5 minutes across safe screen margins.
**Why:** Ensures unobstructed wall-clock visibility and prevents static image retention (ghosting) on the tablet's LCD screen over months of continuous power.
**Must never:**
- Allow the tablet display to dim to sleep or lock while the clock screen is visible.
- Shift the display so far that outer segments clip off the edge of the screen.
**Edge cases:**
- Immersive system bars reappearing after a system notification or edge swipe; the app must restore immersive flags cleanly.

### Color Customization & Settings Overlay
**What:** Double-tapping anywhere on the clock face opens a clean Settings overlay (and back-press or a close button dismisses it back to the clock). Users can choose:
1. Active segment color: Cyan (default) or Teal presets, plus a color picker (system color picker if available on API 21, or a built-in Compose color picker fallback).
2. Background color: Pure Black (`#000000`), Deep Dark Navy, or Charcoal.
3. Access to the About screen.
Preferences persist locally via Jetpack DataStore.
**Why:** Lets the user match the clock's glow and backdrop to the room's lighting without cluttering the main clock face with persistent buttons.
**Must never:**
- Show persistent on-screen settings buttons or chrome on the main clock display.
- Corrupt or lose saved color preferences across app restarts or device reboots.
**Edge cases:**
- Single accidental taps on the screen (must not trigger Settings; requires an intentional double-tap).
- Pressing the hardware/system Back button while the Settings overlay is open (must dismiss Settings to the clock rather than exiting the app).
- API 21 lacking a native system color picker intent (must fall back gracefully without crashing).

## About
The app shows its name and version, plus the about text, the credits and the license, taken from the real files in the repo root (`ABOUT.md`, `CREDITS.md` and `LICENSE`), embedded where possible. Form: the About screen from the Android guideline, accessible from the Settings overlay.
Must never: show a copy of the credits or license that differs from the repo files.

## Out of scope
- Temperature display or weather APIs.
- Alarms, timers, or audio playback.
- DST (Daylight Saving Time) indicators.
- Auto-dimming via ambient light sensor or front-facing camera presence/brightness detection.
- Network NTP/GPS syncing logic inside the app (relies on Android OS system time).

## Open questions
- None. All feasibility questions were settled; technical verifications are queued for Phase 0.

## Amendments log
- (empty at the start)