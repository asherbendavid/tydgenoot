# Tydgenoot — Tasks

*Workflow set v2.0. Maintained by the coordinator. Updated after: handoff-tydgenoot-phase-1.md.*

Highest number issued: T-019

## Phase plan
| Phase | Scope | Target version | Status |
|---|---|---|---|
| 0 | Foundation & Spike S-00 | 1.0.0 | complete |
| 1 | Core 7-Segment Clock & Burn-In Shift | 1.0.0 | complete |
| 2 | Orientation Layouts & Date Integration | 1.0.0 | current |
| 3 | Settings Overlay, DataStore & About Screen | 1.0.0 | planned |

## Phase 2: Orientation Layouts & Date Integration
- [ ] T-016 Create pure Kotlin date and day-of-week formatting models (full date `dd MM yyyy`, day-of-week index `0`–`6` for `MON`–`SUN`) with JVM unit tests (spec: Segmented Clock & Date Display; tier: Important)
- [ ] T-017 Build Jetpack Compose `Canvas` or text-based vertical 7-day column indicator (`MON` through `SUN` with active day illuminated and inactive days dimmed) with JVM unit tests (spec: Segmented Clock & Date Display; tier: Cosmetic)
- [ ] T-018 Build responsive Landscape layout container (classic wall clock arrangement: day column on left, large `HH:MM` on top-right, `dd MM yyyy` on bottom-right) ensuring burn-in offset translation margins do not clip (spec: Responsive Orientation Layouts & Landscape & Portrait; tier: Important)
- [ ] T-019 Build responsive Portrait layout container (vertical stack: day column across top, large `HH:MM` centered, `dd MM yyyy` along bottom) and verify seamless rotation reflow on device (spec: Responsive Orientation Layouts & Landscape & Portrait; tier: Important)

## Phase 3: Settings Overlay, DataStore & About Screen (coarse)
- [ ] P-009 Implement double-tap gesture listener to trigger Settings overlay
- [ ] P-010 Wire Jetpack DataStore preferences for background and segment color selection, plus leading zero toggle
- [ ] P-011 Integrate Markdown About screen reading packaged assets (`ABOUT.md`, `CREDITS.md`, `LICENSE`)
- [ ] P-012 Final verification, APK release build signing, and manual checklist run

## Phase 0: complete, see handoff-tydgenoot-phase-0.md (T-001 to T-003)
## Phase 1: complete, see handoff-tydgenoot-phase-1.md (T-004 to T-015)