# Handoff — Tydgenoot Phase 1

## 1. Phase goal
Implement the core 7-segment digital wall clock engine for Tydgenoot (`cvc.dashingdog.tydgenoot`): pure Kotlin 7-segment digit mapping (`T-004`), custom Jetpack Compose `Canvas` 7-segment and blinking colon rendering (`T-013`), system-clock-driven `ClockViewModel` (`T-005`), 5-minute ~24px burn-in shift math (`T-006`), full-screen sticky immersive `MainActivity` with `FLAG_KEEP_SCREEN_ON` (`T-014`), and a minimal About stub verified via the Phase 1 manual checklist on the target Lenovo TB3-710F tablet (`T-015`).

## 2. Files added
- `app/src/main/java/cvc/dashingdog/tydgenoot/model/SegmentMapper.kt` — Pure Kotlin `SegmentState` data model and `SegmentMapper` mapping digits `0`–`9` and blank leading hours to boolean segments `a`–`g`.
- `app/src/main/java/cvc/dashingdog/tydgenoot/model/BurnInOffsetCalculator.kt` — Pure Kotlin 5-minute interval pseudo-random `[-24px, +24px]` burn-in offset generator.
- `app/src/main/java/cvc/dashingdog/tydgenoot/ui/clock/ClockViewModel.kt` — `ViewModel` + `StateFlow<ClockUiState>` syncing `HH:MM`, 1 Hz colon blink, leading-zero preference, and burn-in offset from the system clock.
- `app/src/main/java/cvc/dashingdog/tydgenoot/ui/clock/SegmentClockRenderer.kt` — Jetpack Compose `Canvas` composables (`SegmentDigit` and `BlinkingColon`) rendering active and dim ghost segments with convex middle-segment tips.
- `app/src/test/java/cvc/dashingdog/tydgenoot/model/SegmentMapperTest.kt` — JVM unit tests for digits `0`–`9`, out-of-range inputs, minutes, and leading-zero vs. blank-leading-hour modes.
- `app/src/test/java/cvc/dashingdog/tydgenoot/model/BurnInOffsetCalculatorTest.kt` — JVM unit tests verifying `[-24px, +24px]` clamping and intra-interval stability.
- `app/src/test/java/cvc/dashingdog/tydgenoot/ui/clock/ClockViewModelTest.kt` — JVM unit tests verifying system time sync and midnight rollover (`23:59` -> `00:00`).
- `manual-checklist-tydgenoot-phase-1.md` — Executed Phase 1 verification checklist for the Lenovo TB3-710F.
- `handoff-tydgenoot-phase-1.md` — Phase 1 completion handoff.

## 3. Files changed
- `app/src/main/java/cvc/dashingdog/tydgenoot/MainActivity.kt` — Replaced template scaffold with sticky immersive full-screen mode (`FLAG_KEEP_SCREEN_ON`, `onWindowFocusChanged` bar re-hiding), pure `#000000` canvas host applying burn-in offset, and discreet `Tydgenoot v0.1.0` bottom-right About stub.
- `README.md` — Updated completed features and current limitations to accurately reflect Phase 1 verified functionality.

## 4. Item status
- `T-004`: **done** — Implemented `SegmentState` and `SegmentMapper` with JVM unit tests.
- `T-013`: **done** — Implemented `SegmentDigit` and `BlinkingColon` Compose `Canvas` renderers and refined middle segment (`g`) geometry to convex outward-pointed tips after device visual inspection.
- `T-005`: **done** — Implemented `ClockViewModel` with injectable `timeProvider` and `CoroutineDispatcher` and passing JVM unit tests.
- `T-006`: **done** — Implemented `BurnInOffsetCalculator` with JVM unit tests.
- `T-014`: **done** — Wired `MainActivity` immersive mode, `FLAG_KEEP_SCREEN_ON`, focus restoration, and clock rendering; verified on target hardware.
- `T-015`: **done** — Added minimal bottom-right version/name stub (`Tydgenoot v0.1.0`) and completed the Phase 1 manual checklist on the Lenovo TB3-710F.

## 5. Unplanned work done
- Refined `SegmentPos.MIDDLE` path geometry in `SegmentClockRenderer.kt` from concave inward tips to convex outward-pointed tips following visual inspection on the Lenovo TB3-710F hardware.
- Made `CoroutineDispatcher` injectable in `ClockViewModel` constructor (defaulting to `Dispatchers.Main`) so JVM unit tests can run cleanly with `Dispatchers.Unconfined` without requiring `kotlinx-coroutines-test` or Android `Looper` mocks.

## 6. Verification outcome
1. **Compiles & tests pass:** Pass — Gradle sync, debug APK build, and all JVM unit tests (`SegmentMapperTest`, `BurnInOffsetCalculatorTest`, `ClockViewModelTest`) passed with zero errors.
2. **Launches on target:** Pass — Sideloaded onto physical Lenovo TB3-710F (Android 5.0.1 / API 21) and launched cleanly without crashes.
3. **Happy path walkthrough:** Pass — Displays pure `#000000` backdrop, Cyan 7-segment `HH:MM` digits with dim ghost segments, 1 Hz blinking colon, and 5-minute burn-in shift.
4. **Must-never checks:** Pass — Verified absence of temperature readouts, alarm icons, DST badges, persistent settings buttons, screen sleep, and `.ttf`/`.otf` clock fonts.
5. **Spec traceability:** Pass — All Phase 1 requirements and edge cases trace to JVM unit tests (`SegmentMapperTest`, `BurnInOffsetCalculatorTest`, `ClockViewModelTest`) or `manual-checklist-tydgenoot-phase-1.md`.
6. **Platform checks:** Pass — Sideloaded onto physical API 21 device; verified wake lock and sticky immersive behavior.
7. **Placeholder audit:** Pass — One intentional placeholder (`Tydgenoot v0.1.0` text stub in `MainActivity.kt` for `T-015`), listed in Section 15.
8. **Manual checklist:** Pass — Executed and recorded in `manual-checklist-tydgenoot-phase-1.md`.
9. **Regression pass:** Pass — Phase 0 Gradle build and asset copy task (`copyRootDocsToAssets`) remain intact and compiling.
10. **Real-service or real-content check:** Pass — Tested live against the physical Lenovo TB3-710F system clock.
11. **New tests prove their worth:** Pass — `ClockViewModelTest` initially exposed missing `Dispatchers.Main` initialization on JVM, leading to explicit dispatcher injection in `ClockViewModel`.
12. **Fresh-eyes re-check:** Pass — Reviewed `SegmentMapper`, `ClockViewModel`, `BurnInOffsetCalculator`, and `MainActivity` against `constitution-tydgenoot.md` and `spec-tydgenoot.md`; all rules satisfied.
- **Manual checklist pointer:** `manual-checklist-tydgenoot-phase-1.md`
- **tag-ready:** yes

## 7. Deviations from plan
- None in module boundaries or architecture. Added `showLeadingZero: Boolean` to `ClockUiState` and `ClockViewModel.setShowLeadingZero(Boolean)` in Phase 1 so the underlying mapper and state model are ready for the Phase 3 Settings toggle requested by the developer.

## 8. Decisions made
Decision (new)
Type:       choice
Decision:   Make leading-hour zero display (08:30 vs. blank leading digit 8:30) user-configurable in the Settings overlay, defaulting to true (08:30) in Phase 1.
Reasoning:  Developer wished to support both aesthetic preferences rather than hardcoding one behavior; SegmentMapper and ClockViewModel now support toggling dynamically, ready to bind to DataStore and Settings UI in Phase 3.
Phase:      1

## 9. Decisions not to fix
- None.

## 10. Spike results
- None.

## 11. Deferred items
- Wiring `ClockViewModel.setShowLeadingZero(Boolean)` to a Settings toggle and persisting it in Jetpack DataStore (deferred to Phase 3 alongside `P-009` and `P-010`).

## 12. Proposed spec amendments
- Under **Color Customization & Settings Overlay** in `spec-tydgenoot.md`, add item 4 to the user-configurable settings list: *"4. Leading hour zero toggle (displaying `08:30` with a lit leading zero vs. ` 8:30` with dim ghost segments on the leading digit)."*

## 13. Proposed changes to other project files
- In `tasks-tydgenoot.md` (Phase 3), note under `P-009` / `P-010` that the Settings overlay and DataStore preferences should also expose and persist the `showLeadingZero` boolean toggle added in `ClockViewModel`.
- Minor typo noted in `app/build.gradle.kts` line 9 (`generated/assest/rootDocs` -> `generated/assets/rootDocs`); works consistently as-is because both sourceSet and copy task reference `generatedDocsDir`, can be tidied in a future build touch if desired.

## 14. Unforeseen files needed
- None.

## 15. Placeholders left
- `MainActivity.kt` (lines 76–86): Minimal bottom-right `Text("Tydgenoot v0.1.0")` About stub (`T-015`). Will be removed from the main clock face and replaced by the full Markdown About screen inside the double-tap Settings overlay in Phase 3 (`P-009`, `P-011`).

## 16. Global lessons
- **Target file & section:** `guideline-android.md` -> `Testing`
- **Text:**
  > **Inject `CoroutineDispatcher` into `ViewModel` classes that launch coroutines in `init`.** Calling `viewModelScope.launch` without an injected dispatcher defaults to `Dispatchers.Main`, which throws `IllegalStateException: Module with the Main dispatcher had failed to initialize` in JVM unit tests when `kotlinx-coroutines-test` is not on the test classpath. Defaulting a constructor parameter `dispatcher: CoroutineDispatcher = Dispatchers.Main` lets JVM tests pass `Dispatchers.Unconfined` cleanly.
- **Why:** Prevents JVM unit test initialization failures on `ViewModel` classes without adding extra test dependencies.
- **Origin:** Tydgenoot, Phase 1.

## 17. Notes for the next phase
- Phase 2 (`P-007`, `P-008`) will add the `MON`–`SUN` day-of-week indicator and `dd MM yyyy` date row, plus responsive Landscape and Portrait layouts.
- Watch outer padding/margins when composing the full Landscape and Portrait layouts so that the `[-24px, +24px]` burn-in translation offset (`uiState.burnInOffset`) still never clips any outer day label, clock digit, or date digit on the Lenovo TB3-710F screen edges.