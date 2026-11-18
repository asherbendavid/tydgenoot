# Starter prompt: Tydgenoot, phase 1: Core 7-Segment Clock & Burn-In Shift

1. Attach alongside
   - Always: `project-reliability-guide.md`, `phase-protocol.md`, `constitution-tydgenoot.md`, `spec-tydgenoot.md`, `plan-tydgenoot.md`, `tasks-tydgenoot.md`
   - Guideline files: `guideline-android.md`
   - Spike files: none
   - Source files: `app/build.gradle.kts`, `app/src/main/AndroidManifest.xml`, `app/src/main/java/cvc/dashingdog/tydgenoot/MainActivity.kt`

2. Orientation
   - **Tydgenoot** (`cvc.dashingdog.tydgenoot`) is a minimalist, full-screen Jetpack Compose digital wall clock built with custom Canvas-rendered 7-segment digits, targeting a Lenovo TB3-710F tablet (Android 5.0.1 / API 21).
   - Phase 0 is complete (`phase-0` tagged). Phase 1 builds the core clock engine: 7-segment Canvas rendering (`HH:MM` + blinking colon), `ClockViewModel` system time sync, 5-minute ~24px LCD burn-in shift math, and `MainActivity` full-screen immersive mode with `FLAG_KEEP_SCREEN_ON`.
   - Read `constitution-tydgenoot.md`, `spec-tydgenoot.md`, `plan-tydgenoot.md`, and `tasks-tydgenoot.md` first.

3. Scope
   - `T-004`: Pure Kotlin 7-segment state mapper (`0`–`9` + blank leading hour option) with JVM unit tests.
   - `T-013`: Jetpack Compose `Canvas` 7-segment digit and blinking colon renderer with illuminated active segments and dim ghost segments.
   - `T-005`: `ClockViewModel` + `StateFlow` driven strictly by the Android system clock (`HH:MM` + 1 Hz colon blink), with JVM unit tests covering midnight rollover (`23:59` -> `00:00`) and external system time jumps.
   - `T-006`: Pure Kotlin ~24px pseudo-random burn-in offset generator (5-minute interval, bounded within safe margins so outer segments never clip) with JVM unit tests.
   - `T-014`: Wire `MainActivity` full-screen immersive mode (`FLAG_KEEP_SCREEN_ON`, hidden system status/navigation bars, automatic re-hide after edge swipe/focus loss) displaying the centered `HH:MM` clock on a pure black (`#000000`) background with default Cyan segments.
   - `T-015`: Minimal About stub (e.g., discreet version/info trigger or placeholder route to be moved into Settings in Phase 3) and Phase 1 manual checklist execution.
   - **Decision to settle with developer before coding:** Leading hour zero behavior on `HH:MM` (e.g., `08:30` with leading zero lit vs. ` 8:30` with leading digit showing only dim ghost segments).

4. Phase-specific design and test expectations
   - **Components in play & tiers:** `model/` (7-segment mapping, clock state, burn-in offset math) and `ui/clock/` (`Canvas` renderer, `ClockViewModel`) — `Important` tier for time/date accuracy, wake lock, and zero clipping; `Cosmetic` tier for segment proportions, ghost alpha, and burn-in shift styling.
   - **Branches and states to enumerate before code:**
     - Segment bitmasks for all 10 digits (`0`–`9`) and blank/off state.
     - Colon blink toggle (`true`/`false` at 1 Hz without drifting wall-clock minutes).
     - Midnight rollover (`23:59` -> `00:00`) and manual/NTP clock jumps backward or forward.
     - Burn-in offset clamping (`[-24px, +24px]` within outer screen padding so digits never clip).
     - Activity window focus changes (`onWindowFocusChanged` restoring immersive sticky flags on API 21).
   - **Manual-only checks:**
     - Sideload onto physical Lenovo TB3-710F (Android 5.0.1) and verify smooth 1 Hz colon blink without frame drops or UI lag.
     - Verify `FLAG_KEEP_SCREEN_ON` prevents screen dim/sleep over an extended soak window (>15 minutes).
     - Swipe from screen edge to reveal system bars and confirm immersive mode automatically hides them again.
     - Verify visible ~24px shift after 5 minutes without any segment clipping.

5. References
   - `spec-tydgenoot.md` -> "Segmented Clock & Date Display" and "Immersive Display & LCD Burn-In Protection".
   - `guideline-android.md` -> "Setup and structure", "Testing", and "Platform verification checks".
   - `tydgenoot-inspiration.jpg` (from feasibility) -> sharp-angled classic 7-segment proportions and vertical/horizontal segment bevels.

6. Fences
   - Do NOT build the `MON`–`SUN` day column, `dd MM yyyy` date row, or Portrait/Landscape split layouts yet (reserved for Phase 2).
   - Do NOT build the double-tap Settings overlay, custom color picker, or Jetpack DataStore persistence yet (reserved for Phase 3).
   - Must-never: Never display temperature, alarm indicators, or DST badges; never use `.ttf`/`.otf` fonts for clock digits; never allow the screen to sleep while active.

7. Verification and deliverable additions
   - Follow `phase-protocol.md` verification checklist (items 1–12).
   - Commit each completed task (`T-004`, `T-013`, `T-005`, `T-006`, `T-014`, `T-015`) individually on `dev`.
   - Deliver `handoff-tydgenoot-phase-1.md`, `manual-checklist-tydgenoot-phase-1.md`, updated `README.md` / `CREDITS.md`, and a summary of commits made.