# Manual Checklist — Tydgenoot Phase 2

- **Project:** Tydgenoot (`cvc.dashingdog.tydgenoot`)
- **Phase:** Phase 2 (Orientation Layouts & Date Integration)
- **Build / Version:** `0.2.0` (`versionCode = 2`), branch `dev`
- **Target Device(s):** Lenovo TB3-710F (Android 5.0.1 / API 21) [primary/target tablet]; Huawei phone [initial smoke test]

## Checklist Items & Results

| # | What to Do | Expected Result | Spec / Scope Trace | Result |
|---|---|---|---|---|
| 1 | Launch Tydgenoot on the Lenovo TB3-710F in Landscape orientation | App opens in full-screen immersive mode with `FLAG_KEEP_SCREEN_ON` active, pure black (`#000000`) background, day column (`MON`–`SUN`) on left, large `HH:MM` top-right, `dd MM yyyy` bottom-right, and bottom-right label showing `Tydgenoot v0.2.0`; no temperature, alarm, or DST indicators | Spec: Happy path, Segmented Clock & Date Display, Responsive Orientation Layouts; Scope: T-018 | [x] Passed |
| 2 | Rotate Lenovo TB3-710F between Portrait and Landscape repeatedly (including rapid back-to-back flips) | Layout reflows smoothly between vertical stack (days top, `HH:MM` center, `dd MM yyyy` bottom) and landscape arrangement without crashes, lag, clipping, overlapping digits, or lost clock/burn-in state | Spec: Responsive Orientation Layouts (must-never & edge cases); Guideline: Rotation; Scope: T-019 | [x] Passed |
| 3 | Inspect the 7-day indicator (`MON`–`SUN` in landscape; compact `M`–`S` across top in portrait) | Today's day is brightly illuminated in active color; the other 6 days are dimmed in ghost/inactive styling with no clipping | Spec: Segmented Clock & Date Display; Scope: T-017, T-018, T-019 | [x] Passed |
| 4 | Inspect the full date (`dd MM yyyy`) and blinking colon | Date renders with 2-digit day, 2-digit month, 4-digit year using custom 7-segment digits (no `.ttf`/`.otf` fonts); colon blinks once per second | Spec: Happy path, Segmented Clock & Date Display; Scope: T-016, T-018, T-019 | [x] Passed |
| 5 | Observe or test midnight rollover (`23:59` -> `00:00`) or system clock/date adjustment | Time, highlighted day-of-week, and `dd MM yyyy` update together cleanly without stale day/date lag | Spec: Segmented Clock & Date Display (edge cases); Scope: T-016 | [x] Passed |
| 6 | Observe periodic burn-in shift (~24px every 5 minutes) in both Landscape and Portrait | Display shifts subtly without clipping outer segments, day column, or date row against screen edges | Spec: Immersive Display & LCD Burn-In Protection (must-never); Scope: T-018, T-019 | [x] Passed |
| 7 | Leave device running with screen awake for extended period and confirm single-tap does not open Settings | Screen stays awake (`FLAG_KEEP_SCREEN_ON`); single taps on clock face do nothing; no persistent Settings chrome on screen | Spec: Immersive Display, Color Customization & Settings Overlay; Constitution: Global must-never rules | [x] Passed |