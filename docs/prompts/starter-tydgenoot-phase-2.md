# Starter prompt: Tydgenoot, phase 2: Orientation Layouts & Date Integration

1. Attach alongside
   - Always: `project-reliability-guide.md`, `phase-protocol.md`, `constitution-tydgenoot.md`, `spec-tydgenoot.md`, `plan-tydgenoot.md`, `tasks-tydgenoot.md`
   - Guideline files: `guideline-android.md`
   - Spike files: none
   - Source files: `app/build.gradle.kts`, `app/src/main/java/cvc/dashingdog/tydgenoot/MainActivity.kt`, `app/src/main/java/cvc/dashingdog/tydgenoot/ui/clock/ClockViewModel.kt`, `app/src/main/java/cvc/dashingdog/tydgenoot/ui/clock/SegmentClockRenderer.kt`

2. Orientation
   - **Tydgenoot** (`cvc.dashingdog.tydgenoot`) is a minimalist, full-screen digital wall clock built with custom Canvas-rendered 7-segment digits, targeting a Lenovo TB3-710F tablet (Android 5.0.1 / API 21).
   - Phase 1 is complete (`phase-1` tagged). Phase 2 implements responsive orientation layouts (Landscape classic wall-clock arrangement vs. Portrait vertical stack), the 7-day column indicator (`MON`–`SUN`), and the full date row (`dd MM yyyy`).
   - Read `constitution-tydgenoot.md`, `spec-tydgenoot.md`, `plan-tydgenoot.md`, and `tasks-tydgenoot.md` first.

3. Scope
   - `T-016`: Pure Kotlin date formatter (`dd MM yyyy` with 4-digit year) and day-of-week index mapper (`MON`–`SUN`) with JVM unit tests.
   - `T-017`: Compose component for the vertical 7-day column indicator (`MON` through `SUN`, active day illuminated in Cyan, inactive days dimmed in ghost alpha) with unit tests.
   - `T-018`: Landscape layout container implementing the classic commercial LED wall clock arrangement (day column on left, large `HH:MM` on top-right, `dd MM yyyy` on bottom-right), ensuring the `[-24px, +24px]` burn-in translation offset applies safely without clipping outer screen edges.
   - `T-019`: Portrait layout container implementing the vertical stack layout (day column across top, large `HH:MM` centered, `dd MM yyyy` along bottom) and verifying seamless rotation reflow on the physical device.
   - **Decision to settle with developer before coding:** Exact font/sizing scale proportions for date digits and day-of-week labels relative to the main `HH:MM` clock digits in both orientations.

4. Phase-specific design and test expectations
   - **Components in play & tiers:** `model/` (date/day formatters) and `ui/clock/` (day column, date row, landscape/portrait layout containers) — `Important` tier for correct date rollover at midnight and zero UI clipping across orientation changes; `Cosmetic` tier for visual spacing and label alignment.
   - **Branches and states to enumerate before code:**
     - Day-of-week indexing (handling Java `Calendar.DAY_OF_WEEK` mapping to Monday-first vs. Sunday-first arrays).
     - Midnight rollover updating time, day-of-week, and `dd MM yyyy` simultaneously.
     - Orientation changes (`Configuration.ORIENTATION_LANDSCAPE` vs `ORIENTATION_PORTRAIT`) preserving state and burn-in translation offsets.
     - Burn-in offset boundary safety in tight portrait/landscape viewports so outer margins do not push content off-screen.
   - **Manual-only checks:**
     - Rotate physical Lenovo TB3-710F between landscape and portrait repeatedly; verify smooth reflow without crashing, lagging, or resetting state.
     - Verify day-of-week column correctly highlights today while dimming the other 6 days.
     - Verify full date (`dd MM yyyy`) displays correctly and updates at midnight.

5. References
   - `spec-tydgenoot.md` -> "Segmented Clock & Date Display" and "Responsive Orientation Layouts (Landscape & Portrait)".
   - `guideline-android.md` -> "Rotation" verification checks.
   - `tydgenoot-inspiration.jpg` -> visual reference for day column position and date placement.

6. Fences
   - Do NOT build the double-tap Settings overlay, custom color picker, or Jetpack DataStore preferences yet (reserved for Phase 3).
   - Must-never: Never lock the app to a single orientation; never lose settings or reset display state during rotation; never render temperature, alarm indicators, or DST badges.

7. Verification and deliverable additions
   - Follow `phase-protocol.md` verification checklist (items 1–12).
   - Commit each completed task (`T-016`, `T-017`, `T-018`, `T-019`) individually on `dev`.
   - Deliver `handoff-tydgenoot-phase-2.md`, `manual-checklist-tydgenoot-phase-2.md`, updated `README.md`, and a summary of commits made.