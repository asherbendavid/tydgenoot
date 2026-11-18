# Starter prompt: Tydgenoot — Coordinator (Project Kickoff)

1. Attach alongside
   - `workflow.md`
   - `project-reliability-guide.md`
   - `phase-protocol.md`
   - `guideline-android.md`
   - `CREDITS_TEMPLATE.md` and `ATTRIBUTION_GUIDELINES.md`
   - Draft `constitution-tydgenoot.md` and draft `spec-tydgenoot.md`

2. Orientation
   - **Tydgenoot** (`cvc.dashingdog.tydgenoot`) is a minimalist, full-screen Jetpack Compose digital wall clock built with custom Canvas-rendered 7-segment digits, designed to repurpose a Lenovo TB3-710F tablet (Android 5.0.1 / API 21).
   - Current state: Feasibility is complete; draft constitution and draft spec are ready; no repository or code exists yet.

3. Settled decisions
   - **Stack:** Kotlin, Jetpack Compose, ViewModel + StateFlow, Jetpack DataStore (Preferences), targeting minSdk 21.
   - **App ID & Namespace:** `cvc.dashingdog.tydgenoot`
   - **Clock Rendering:** Custom Compose `Canvas` 7-segment geometry (active + dim ghost segments); no `.ttf` clock fonts.
   - **Display Content:** `HH:MM` (blinking colon), `MON`–`SUN` indicator, and `dd MM yyyy` full date. No temperature, no alarms, no DST indicator.
   - **Layouts:** Both Landscape (modeled on classic LED wall clock layout) and Portrait (vertical stack).
   - **Screen Protection:** `FLAG_KEEP_SCREEN_ON`, full-screen immersive mode, and a ~24px random burn-in/image-retention shift every 5 minutes.
   - **Customization & Navigation:** Double-tap anywhere on the clock opens Settings (plus About screen link). Background options: Black (`#000000`), Deep Dark Navy, Charcoal. Segment color: Cyan/Teal defaults + color picker.
   - **About Form:** Android About screen rendering embedded `ABOUT.md`, `CREDITS.md`, and `LICENSE` via a Compose markdown library.

4. Open decisions for Phase 0
   - **Jetpack Compose & AGP compatibility on API 21:** Verify exact Compose BOM / Kotlin / AGP versions that compile cleanly and run smoothly on Android 5.0.1 (Lenovo TB3-710F) without runtime `NoClassDefFoundError` or performance lag.
   - **Color picker implementation on API 21:** Android 5.0 does not include a standardized system color picker intent; confirm a lightweight Compose color picker component or HSV/preset picker to satisfy the color selection requirement cleanly.
   - **Markdown library & Gradle asset copy task (S-00):** Select an API 21-compatible Compose markdown library and verify the Gradle task wiring that copies `ABOUT.md`, `CREDITS.md`, and `LICENSE` from the repo root into packaged assets.

5. Provisional tier hints (inputs to the plan, not assignments)
   - **Important:** System clock ticker & `dd MM yyyy` / day-of-week formatting; `FLAG_KEEP_SCREEN_ON` and immersive mode lifecycle; DataStore color persistence; orientation state preservation; Settings overlay back-press handling.
   - **Cosmetic:** 7-segment Canvas path geometry and glow/ghost alpha tuning; ~24px 5-minute burn-in shift math; landscape/portrait spacing proportions; About screen layout.

6. Phase 0 recommendation
   - **Recommended.** While the app logic is compact, verifying Jetpack Compose runtime stability on the physical Android 5.0.1 Lenovo TB3-710F (alongside the Android 10 primary and Android 11 Go secondary devices) and settling the S-00 spike (Compose markdown library + root file Gradle copy task) before freezing the constitution and spec will prevent toolchain surprises.

7. Risks and constraints
   - **Target hardware:** Lenovo TB3-710F is low-end 2015 hardware running Android 5.0.1 (API 21). Canvas redraws (1 Hz colon blink) and Compose recompositions must stay lightweight to avoid frame drops or thermal buildup while plugged in 24/7.
   - **Testing environment:** Primary phone (Android 10) uses LocalSend sideloading with no Logcat; Hisense Android 11 Go supports WiFi debugging and Logcat for instrumented DataStore tests; physical Lenovo TB3-710F is available for target verification.
   - **Windows path constraint:** Repo must be created in an apostrophe-free path (e.g., `C:\Dev\Tydgenoot`).

8. Name and icon outcomes
   - **Chosen Name:** Tydgenoot (Afrikaans for "contemporary" / "companion of time").
   - **Icon Concept:** Minimalist monogram featuring a bold, sharp-cornered letter "T" formed out of two illuminated cyan LED bar segments against a dark navy background.

9. Credits material (for CREDITS.md)
   - **Inspired by:** Commercial LED digital wall clocks (specifically minimalist desk/wall LED clock displays showing time, day-of-week column, and date) and the practical need to repurpose an older Lenovo TB3-710F tablet.
   - **Patterned on:** Classic 7-segment LED hardware displays, simplified by removing temperature, alarm, and DST clutter and adding the 4-digit year.
   - **Previously used apps:** None.
   - **Built with:** Kotlin, Jetpack Compose, AndroidX ViewModel, Jetpack DataStore, and the Compose markdown library chosen in S-00.
   - **Media:** Custom minimalist monogram icon generated during project design.