# Tydgenoot — Tasks

*Workflow set v2.0. Maintained by the coordinator. Updated after: handoff-tydgenoot-phase-3.md.*

Highest number issued: T-022

## Phase plan
| Phase | Scope | Target version | Status |
|---|---|---|---|
| 0 | Foundation & Spike S-00 | 1.0.0 | complete |
| 1 | Core 7-Segment Clock & Burn-In Shift | 1.0.0 | complete |
| 2 | Orientation Layouts & Date Integration | 1.0.0 | complete |
| 3 | Settings Overlay, DataStore & About Screen | 1.0.0 | complete |

## Phase 3: Settings Overlay, DataStore & About Screen (complete, see handoff-tydgenoot-phase-3.md)
- [x] T-010 Implement `ThemePreferencesRepository` backed by Jetpack DataStore (`Preferences`) storing active segment color ARGB, background preset (`BLACK`, `NAVY`, `CHARCOAL`), and `showLeadingZero` boolean, with instrumented tests run on the Android 11 Go device (spec: Color Customization & Settings Overlay; tier: Important)
- [x] T-020 Wire `ClockViewModel` to observe `ThemePreferencesRepository` on startup and persist theme/leading-zero updates immediately, with JVM unit tests using an in-memory fake repository (needs T-010) (spec: Color Customization & Settings Overlay; tier: Important)
- [x] T-009 Add double-tap gesture detector on the main clock face (ignoring single taps) to open the Settings overlay, plus back-press (`BackHandler`) and close-button dismissal back to the clock (needs T-020) (spec: Color Customization & Settings Overlay; tier: Important)
- [x] T-021 Build the Settings overlay UI: Cyan/Teal presets + lightweight built-in Compose HSV/RGB color picker, background selector (`#000000` Black, Deep Dark Navy, Charcoal), leading-hour zero toggle, and About button (needs T-009) (spec: Color Customization & Settings Overlay; tier: Important)
- [x] T-011 Build the Markdown About screen rendering packaged assets (`ABOUT.md`, `CREDITS.md`, and `LICENSE` in a fenced code/plain block) plus dynamic app name & `versionName` header (needs T-021) (spec: About; tier: Cosmetic)
- [x] T-022 Verify upgrade path from Phase 2 APK (higher `versionCode` + same signing key), review `CREDITS.md` and `README.md`, and execute final Phase 3 manual checklist on target devices (needs T-011) (spec: About; tier: Important)
- [x] T-012 Build signed/release APK (`v1.0.0`) and complete final verification pass (needs T-022) (spec: Identity; tier: Important)

## Phase 0: complete, see handoff-tydgenoot-phase-0.md (T-001 to T-003)
## Phase 1: complete, see handoff-tydgenoot-phase-1.md (T-004 to T-015)
## Phase 2: complete, see handoff-tydgenoot-phase-2.md (T-016 to T-019)