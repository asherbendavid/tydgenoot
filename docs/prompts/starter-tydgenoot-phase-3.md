# Starter prompt: Tydgenoot, phase 3: Settings Overlay, DataStore & About Screen

1. Attach alongside
   - Always: `project-reliability-guide.md`, `phase-protocol.md`, `constitution-tydgenoot.md`, `spec-tydgenoot.md`, `plan-tydgenoot.md`, `tasks-tydgenoot.md`
   - Guideline files: `guideline-android.md`
   - Spike files: none
   - Source files:
     - `app/build.gradle.kts` (verify DataStore & Markdown dependencies and `versionCode`/`versionName`)
     - `app/src/main/AndroidManifest.xml` (verify activity/window attributes)
     - `app/src/main/java/cvc/dashingdog/tydgenoot/MainActivity.kt` (replace Phase 1 About stub, wire double-tap overlay and dynamic background/segment colors)
     - `app/src/main/java/cvc/dashingdog/tydgenoot/ui/clock/ClockViewModel.kt` (wire DataStore preferences repository and settings state)
     - `app/src/main/java/cvc/dashingdog/tydgenoot/ui/clock/LandscapeClockLayout.kt` (pass dynamic active/ghost segment colors)
     - `app/src/main/java/cvc/dashingdog/tydgenoot/ui/clock/PortraitClockLayout.kt` (pass dynamic active/ghost segment colors)

2. Orientation
   - **Tydgenoot** (`cvc.dashingdog.tydgenoot`) is a minimalist, full-screen Jetpack Compose digital wall clock built with custom Canvas-rendered 7-segment digits, targeting a Lenovo TB3-710F tablet (Android 5.0.1 / API 21).
   - Phases 0, 1, and 2 are complete (`phase-2` tagged). Phase 3 is the final feature and release phase: persistent DataStore theme preferences, double-tap Settings overlay (segment color presets + built-in Compose color picker, background color selector, leading-hour zero toggle), full Markdown About screen (`ABOUT.md`, `CREDITS.md`, `LICENSE`), and `v1.0.0` release verification.
   - Read `constitution-tydgenoot.md`, `spec-tydgenoot.md`, `plan-tydgenoot.md`, and `tasks-tydgenoot.md` first.

3. Scope
   - `T-010`: Implement `ThemePreferencesRepository` using Jetpack DataStore (`Preferences`) to persist active segment color (ARGB `Int`/`Long`), background preset (`BLACK`, `NAVY`, `CHARCOAL`), and `showLeadingZero` (`Boolean`). Write instrumented tests (`androidTest`) to run on the Hisense Android 11 Go device via WiFi debugging.
   - `T-020`: Wire `ClockViewModel` to observe and update `ThemePreferencesRepository` (exposing a clean repository interface so `ClockViewModelTest` runs on JVM with a fake repository).
   - `T-009`: Add double-tap gesture detection (`pointerInput` -> `detectTapGestures(onDoubleTap = ...)`) on the main clock face so single accidental taps are ignored, and handle hardware/system Back button (`BackHandler`) plus a close button to dismiss the Settings overlay back to the clock without exiting the app.
   - `T-021`: Build the Settings overlay UI with Cyan (default) and Teal presets, a lightweight built-in Compose HSV/RGB slider color picker (no external API 21 intent needed), background selector (Pure Black `#000000`, Deep Dark Navy, Charcoal), leading-hour zero switch (`08:30` vs. ` 8:30`), and an "About" button.
   - `T-011`: Build the About screen displaying the dynamic app name and `versionName` from `PackageManager`, rendering packaged assets `ABOUT.md` and `CREDITS.md` via `multiplatform-markdown-renderer-m3`, and displaying `LICENSE` in a fenced code block or plain monospace block. Remove the temporary `Tydgenoot v<versionName>` placeholder text from `MainActivity.kt`.
   - `T-022`: Test upgrade installation over the Phase 2 build (increment `versionCode`, keep same signing key), verify `CREDITS.md` and `README.md` are complete and accurate, and execute the Phase 3 manual checklist on target hardware.
   - `T-012`: Bump `versionName` to `"1.0.0"` (and `versionCode` appropriately), build the final release/debug APKs, and complete the final verification pass.
   - **Decisions to settle with developer before coding:**
     1. Exact hex values for Deep Dark Navy and Charcoal background options (e.g., `#060D1A` and `#181A1D`) and default Teal preset hex.
     2. Built-in Compose color picker layout (HSV Hue/Saturation/Value sliders with live 7-segment preview swatch).

4. Phase-specific design and test expectations
   - **Components in play & tiers:**
     - `data/ThemePreferencesRepository.kt` (`Important`)
     - `ui/settings/` (`Important` for overlay dismissal/BackHandler, state persistence, and zero persistent clock chrome)
     - `about/` (`Cosmetic` for markdown layout and asset rendering)
   - **Branches and states to enumerate before code:**
     - DataStore default values on fresh install vs. corrupt/missing preference keys.
     - Single tap vs. double tap on the clock face (single tap must do nothing).
     - Hardware/system Back press when: (a) clock face is visible (normal system behavior), (b) Settings overlay is open (closes Settings to clock), (c) About screen is open inside Settings (returns to Settings).
     - Orientation change while Settings overlay or About screen is open (must preserve open state and unsaved/saved picker state without crashing).
     - Dim ghost segment alpha derivation when user selects a custom segment color (must scale smoothly across bright and dark user-chosen colors).
   - **Testing rules (strict):**
     - **Never** run DataStore tests as JVM unit tests on Windows (`guideline-android.md` gotcha: `*.pb.tmp` atomic rename fails on Windows). Put DataStore tests in `app/src/androidTest/` and run on the Hisense Android 11 Go device.
     - Use an interface/fake repository in `ClockViewModelTest` so `ClockViewModel` tests remain fast JVM unit tests.
   - **Manual-only checks:**
     - Run instrumented DataStore tests on Hisense Android 11 Go.
     - Sideload onto physical Lenovo TB3-710F (Android 5.0.1): verify double-tap opens Settings, single tap does nothing, color picker updates clock segments live, background changes apply cleanly, Back button dismisses Settings to clock, About renders `ABOUT.md`, `CREDITS.md`, and `LICENSE` without truncation or crash, and settings survive force-stop / tablet reboot.
     - Verify upgrade install over Phase 2 APK preserves existing build install cleanly with incremented `versionCode`.

5. References
   - `spec-tydgenoot.md` -> "Color Customization & Settings Overlay" and "About".
   - `guideline-android.md` -> "Testing", "DataStore and JVM tests do not mix (Windows)", "`versionCode` and signing decide whether an update installs", and "About screen".
   - `plan-tydgenoot.md` -> Decision Log `D-01` through `D-06` and Spike `S-00`.

6. Fences
   - Must-never: Never show persistent settings buttons or chrome on the main clock display; never show a copy of credits/license that differs from the repo root files; never leave temporary debug screens in the build; never add out-of-scope features (alarms, temperature, DST, auto-dimming).

7. Verification and deliverable additions
   - Follow `phase-protocol.md` verification checklist (items 1–12), including upgrade-path verification (`guideline-android.md`).
   - Commit each completed task (`T-010`, `T-020`, `T-009`, `T-021`, `T-011`, `T-022`, `T-012`) individually on `dev`.
   - Deliver `handoff-tydgenoot-phase-3.md`, `manual-checklist-tydgenoot-phase-3.md`, final `README.md` and `CREDITS.md`, and a summary of commits made.
