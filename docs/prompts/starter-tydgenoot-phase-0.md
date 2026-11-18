# Starter prompt: Tydgenoot, phase 0: Foundation & Spike S-00

1. Attach alongside
   - Always: `project-reliability-guide.md`, `phase-protocol.md`, `workflow.md`, `constitution-tydgenoot.md`, `spec-tydgenoot.md`, `plan-tydgenoot.md`, `tasks-tydgenoot.md`
   - Guideline files: `guideline-android.md`, `CREDITS_TEMPLATE.md`, `ATTRIBUTION_GUIDELINES.md`
   - Spike files: none yet (will create `spike/S-00-foundation/`)
   - Source files: `gradle/libs.versions.toml`, `app/build.gradle.kts`

2. Orientation
   - **Tydgenoot** (`cvc.dashingdog.tydgenoot`) is a minimalist, full-screen Jetpack Compose digital wall clock built with custom Canvas-rendered 7-segment digits, targeting a Lenovo TB3-710F tablet (Android 5.0.1 / API 21).
   - Phase 0 is the founding step: scaffold the repo in `C:\Dev\Tydgenoot`, verify the nagskof Gradle/Compose stack on API 21, execute Spike S-00 (Markdown library + Gradle root asset packaging), and verify `.gitignore` / `spike/` exclusion.

3. Scope
   - `T-001`: Scaffold project root at `C:\Dev\Tydgenoot` (apostrophe-free path) and apply `libs.versions.toml` and `app/build.gradle.kts` (AGP 9.4.0, Kotlin 2.0.21, Compose BOM 2024.09.00).
   - `T-002`: Create root `.gitignore`, `README.md`, `LICENSE` (BSD 3-Clause), `ATTRIBUTION_GUIDELINES.md`, and initial `CREDITS.md` from template.
   - `T-003`: Create `ABOUT.md` and execute **Spike S-00** to verify:
     1. `spike/` directory sits outside `settings.gradle.kts` and is never synced/built by Gradle.
     2. `.gitignore` properly ignores build artifacts and local secrets templates (`git check-ignore -v`).
     3. `copyRootDocsToAssets` packages `ABOUT.md`, `CREDITS.md`, and `LICENSE` into APK assets and `multiplatform-markdown-renderer-m3` renders them cleanly on the Android 5.0.1 Lenovo TB3-710F without runtime crashes.
   - **Decision to settle with developer:** Confirm the built-in Compose color picker approach for Phase 3 (custom HSV wheel/sliders vs. lightweight library) so it can be logged in `plan-tydgenoot.md` before Phase 0 closes.

4. Phase-specific design and test expectations
   - **Components in play:** Gradle build scripts, root markdown documentation, and a minimal About/Spike verification screen (`Important` tier for asset packaging & API 21 runtime stability).
   - **Manual-only checks:**
     - Install debug APK onto the physical Lenovo TB3-710F (Android 5.0.1) and verify launch without `NoClassDefFoundError` or `VerifyError`.
     - Confirm `ABOUT.md`, `CREDITS.md`, and `LICENSE` text render legibly on the device screen.
     - Run `git check-ignore -v` on spike build/secrets paths and confirm `git status` is clean.

5. References
   - `guideline-android.md` -> "First spike on a new project (S-00)", "About screen", and "Gotchas".
   - `plan-tydgenoot.md` -> Spike S-00 & Decision Log (`D-01` to `D-03`).

6. Fences
   - Do not build the 7-segment clock Canvas, time ticker, burn-in shift math, or DataStore preferences in Phase 0—keep the UI strictly to a minimal shell verifying Spike S-00 and asset rendering.

7. Verification and deliverable additions
   - Follow `phase-protocol.md` verification checklist (items 1–12) and produce `handoff-tydgenoot-phase-0.md`, `manual-checklist-tydgenoot-phase-0.md`, and updated `plan-tydgenoot.md` / `tasks-tydgenoot.md` (with `S-00` measured results filled in).