# Tydgenoot — Tasks

*Workflow set v2.0. Maintained by the coordinator. Updated after: handoff-tydgenoot-phase-0.md.*

Highest number issued: T-015

## Phase plan
| Phase | Scope | Target version | Status |
|---|---|---|---|
| 0 | Foundation & Spike S-00 | 1.0.0 | complete |
| 1 | Core 7-Segment Clock & Burn-In Shift | 1.0.0 | current |
| 2 | Orientation Layouts & Date Integration | 1.0.0 | planned |
| 3 | Settings Overlay, DataStore & About Screen | 1.0.0 | planned |

## Phase 1: Core 7-Segment Clock & Burn-In Shift
- [ ] T-004 Create pure Kotlin 7-segment state mapper (digits `0`–`9` and blank leading space to boolean segment arrays `a`–`g`) with JVM unit tests (spec: Segmented Clock & Date Display; tier: Important)
- [ ] T-013 Build Jetpack Compose `Canvas` 7-segment digit and blinking colon renderer supporting active and dim ghost segment alpha states (needs T-004) (spec: Segmented Clock & Date Display; tier: Cosmetic)
- [ ] T-005 Implement `ClockViewModel` + `StateFlow` driven by the Android system clock (`HH:MM` with 1 Hz blinking colon) and unit tests for midnight rollover and system time jumps (spec: Segmented Clock & Date Display; tier: Important)
- [ ] T-006 Implement pure Kotlin ~24px pseudo-random burn-in offset generator (5-minute interval, safe screen margin bounds) with JVM unit tests (spec: Immersive Display & LCD Burn-In Protection; tier: Cosmetic)
- [ ] T-014 Wire `MainActivity` full-screen immersive mode (`FLAG_KEEP_SCREEN_ON`, hiding status/navigation bars, restoring flags on focus/edge swipe) hosting the core clock view (needs T-005, T-006, T-013) (spec: Immersive Display & LCD Burn-In Protection; tier: Important)
- [ ] T-015 Add minimal About stub reachable for verification and run Phase 1 manual checklist on target hardware (spec: About; tier: Cosmetic)

## Phase 2: Orientation Layouts & Date Integration (coarse)
- [ ] P-007 Build responsive Landscape and Portrait layout containers
- [ ] P-008 Implement full date (`dd MM yyyy`) and 7-day column indicator (`MON`–`SUN`)

## Phase 3: Settings Overlay, DataStore & About Screen (coarse)
- [ ] P-009 Implement double-tap gesture listener to trigger Settings overlay
- [ ] P-010 Wire Jetpack DataStore preferences for background and segment color selection
- [ ] P-011 Integrate Markdown About screen reading packaged assets (`ABOUT.md`, `CREDITS.md`, `LICENSE`)
- [ ] P-012 Final verification, APK release build signing, and manual checklist run

## Phase 0: complete, see handoff-tydgenoot-phase-0.md (T-001 to T-003)