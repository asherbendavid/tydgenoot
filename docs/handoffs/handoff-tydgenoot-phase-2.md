# Handoff: Tydgenoot — Phase 2 (Orientation Layouts & Date Integration)

## 1. Phase goal
Implement responsive orientation layouts (Landscape wall-clock arrangement vs. Portrait vertical stack), the 7-day indicator (`MON`–`SUN` / compact `M`–`S`), and full date display (`dd MM yyyy`) driven by the Android system clock while preserving burn-in offset safety and Phase 1 behavior.

## 2. Files added
- `app/src/main/java/cvc/dashingdog/tydgenoot/model/DateFormatter.kt`: Pure Kotlin formatter for `dd MM yyyy` dates and `MON`–`SUN` / `M`–`S` Monday-first day-of-week indexing.
- `app/src/main/java/cvc/dashingdog/tydgenoot/ui/clock/DayOfWeekIndicator.kt`: Compose component rendering vertical (`MON`–`SUN`) or horizontal (compact `M`–`S`) day indicators with active/inactive styling.
- `app/src/main/java/cvc/dashingdog/tydgenoot/ui/clock/DateRow.kt`: Compose row rendering `dd MM yyyy` using 7-segment `SegmentDigit` components and spacing groups.
- `app/src/main/java/cvc/dashingdog/tydgenoot/ui/clock/LandscapeClockLayout.kt`: Landscape layout container (day column left, `HH:MM` top-right, `dd MM yyyy` bottom-right) with safe burn-in translation offset clamping.
- `app/src/main/java/cvc/dashingdog/tydgenoot/ui/clock/PortraitClockLayout.kt`: Portrait vertical stack layout container (days across top, `HH:MM` centered, `dd MM yyyy` along bottom) with safe burn-in translation offset clamping.
- `app/src/test/java/cvc/dashingdog/tydgenoot/model/DateFormatterTest.kt`: JVM unit tests covering calendar day mapping, out-of-range day fallback, `dd MM yyyy` formatting/clamping, and midnight rollover.
- `app/src/test/java/cvc/dashingdog/tydgenoot/ui/clock/DayOfWeekIndicatorLogicTest.kt`: JVM unit tests verifying standard/compact day label lists and active day index coercion bounds.
- `app/src/test/java/cvc/dashingdog/tydgenoot/ui/clock/BurnInClampTest.kt`: JVM unit tests verifying `clampBurnInOffsetPx` behavior under generous slack, tight viewports, and zero/negative slack.
- `manual-checklist-tydgenoot-phase-2.md`: Phase 2 manual verification checklist with recorded device results.

## 3. Files changed
- `app/src/main/java/cvc/dashingdog/tydgenoot/ui/clock/ClockViewModel.kt`: Added `formattedDate` and `dayOfWeekIndex` to `ClockUiState` and updated them alongside time and burn-in offset on each tick.
- `app/src/main/java/cvc/dashingdog/tydgenoot/MainActivity.kt`: Wired dynamic `PackageManager` version lookup (`versionName`), switched between `LandscapeClockLayout` and `PortraitClockLayout` based on configuration orientation, and preserved immersive mode and `FLAG_KEEP_SCREEN_ON`.
- `README.md`: Updated completed/tested features, installation notes, and limitations to reflect Phase 2 capabilities.

## 4. Item status
- `T-016`: done.
- `T-017`: done.
- `T-018`: done.
- `T-019`: done.

## 5. Unplanned work done
- Updated the bottom-right version label in `MainActivity.kt` to read `versionName` dynamically from installed package info rather than a hardcoded string literal.

## 6. Verification outcome
- **1. Compiles & unit tests pass:** Passed; all JVM unit tests (`DateFormatterTest`, `DayOfWeekIndicatorLogicTest`, `BurnInClampTest`) and APK builds succeeded after resolving `SegmentMapper.mapDigit` signature, `Dp` min/coercion chain, and `BoxWithConstraints` scope receiver usage.
- **2. Launches on target:** Passed on Lenovo TB3-710F (API 21) and initial smoke check on Huawei phone.
- **3. Happy path walkthrough:** Passed on Lenovo TB3-710F across Landscape and Portrait modes.
- **4. Must-never checks:** Passed; no temperature/alarm/DST indicators rendered, custom 7-segment digits used for time and date (standard `Text` for day labels), no single-orientation lock, no clipping/state loss across rotation or burn-in shift, single tap does not open Settings, and no Phase 3 features included.
- **5. Spec traceability:** All Phase 2 branches, edge cases, and must-never items map to JVM unit tests or manual checklist items 1–7; no unaddressed gaps.
- **6. Platform checks:** Rotation tested across orientations; launch verified on target device; no debug screens left in build.
- **7. Placeholder audit:** None (the bottom-right `Tydgenoot v<versionName>` label remains from T-015 until the full About screen is built in Phase 3).
- **8. Manual checklist:** Passed items 1–7 in `manual-checklist-tydgenoot-phase-2.md`.
- **9. Regression pass:** Passed; Phase 1 clock rendering, 1 Hz colon blink, `FLAG_KEEP_SCREEN_ON`, immersive flags, and burn-in shift remained intact.
- **10. Real-service or real-content check:** n/a (no external services or external document files used in Phase 2).
- **11. New tests prove their worth:** Passed; unit tests verified day mapping fallbacks, midnight/date rollovers, label bounds, and burn-in offset clamping.
- **12. Fresh-eyes re-check:** Passed; layout containers, `DateFormatter`, and `DayOfWeekIndicator` match Phase 2 scope and spec requirements.
- **tag-ready: yes**

## 7. Deviations from plan
- Day-of-week labels render via standard Compose `Text` rather than custom-drawn segment paths, and Portrait uses compact single-letter labels (`M`–`S`) across the top row to preserve horizontal spacing.

## 8. Decisions made
Decision (new)
  Type:       choice
  Decision:   Use standard Text composables for day-of-week labels (MON-SUN in Landscape, compact M-S in Portrait) and scale landscape time ~0.6x height, date ~0.5x time height, and day labels ~0.17x time height.
  Reasoning:  Keeps day labels crisp and legible without hand-drawing letter geometry, fits all 7 days cleanly in narrow portrait width, and matches visual proportions from tydgenoot-inspiration.jpg.
  Phase:      Phase 2

  Decision (new)
  Type:       choice
  Decision:   Clamp burn-in translation offsets dynamically against container vs. content slack in pixels before applying graphicsLayer translation.
  Reasoning:  Guarantees that the periodic ~24px burn-in shift never pushes outer segments or labels off-screen even in tight viewports.
  Phase:      Phase 2

 ## 9. Decisions not to fix
None.

## 10. Spike results
None.

## 11. Deferred items
Full double-tap Settings overlay, color/background customization, leading-zero toggle wiring, and full Markdown About screen (ABOUT.md, CREDITS.md, LICENSE) are deferred to Phase 3 per scope.

## 12. Proposed spec amendments
None.

## 13. Proposed changes to other project files
None.

## 14. Unforeseen files needed
None.

## 15. Placeholders left
Minimal bottom-right version text (Tydgenoot v<versionName>) in MainActivity.kt from T-015 until replaced by Phase 3 Settings/About integration.

## 16. Global lessons
None.

## 17. Notes for the next phase
Phase 3 (P-009 to P-012) will add the double-tap gesture listener, Settings overlay, Jetpack DataStore persistence (background color, active segment color, leading-hour zero toggle), Markdown About screen reading embedded root docs, and final release verification. Remember to run DataStore tests as instrumented tests rather than JVM tests.