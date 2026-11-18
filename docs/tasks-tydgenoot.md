# Tydgenoot — Tasks

*Workflow set v2.0. Maintained by the coordinator.*

Highest number issued: T-012

## Phase plan
| Phase | Scope | Target version | Status |
|---|---|---|---|
| 0 | Foundation & Spike S-00 | 1.0.0 | current |
| 1 | Core 7-Segment Clock & Burn-In Shift | 1.0.0 | planned |
| 2 | Orientation Layouts & Date Integration | 1.0.0 | planned |
| 3 | Settings Overlay, DataStore & About Screen | 1.0.0 | planned |

## Phase 0: Foundation
- [ ] T-001 Scaffold project root at `C:\Dev\Tydgenoot` with apostrophe-free path and configure `libs.versions.toml` with nagskof versions (AGP 9.4.0, Kotlin 2.0.21, Compose BOM 2024.09.00) (spec: Identity; tier: Important)
- [ ] T-002 Set up root `.gitignore`, `README.md`, `LICENSE`, `ATTRIBUTION_GUIDELINES.md`, and initial `CREDITS.md` using template (spec: About; tier: Cosmetic)
- [ ] T-003 Create `ABOUT.md` and execute Spike S-00 to test Markdown asset embedding and rendering on API 21 (spec: About; tier: Important)

## Phase 1: Core 7-Segment Clock & Burn-In Shift (coarse)
- [ ] P-004 Implement custom Canvas 7-segment digit drawing path with active and ghost segment states
- [ ] P-005 Wire system clock ticker (`HH:MM` with 1Hz blinking colon) and `FLAG_KEEP_SCREEN_ON`
- [ ] P-006 Implement 5-minute ~24px pseudo-random burn-in shift logic

## Phase 2: Orientation Layouts & Date Integration (coarse)
- [ ] P-007 Build responsive Landscape and Portrait layout containers
- [ ] P-008 Implement full date (`dd MM yyyy`) and 7-day column indicator (`MON`–`SUN`)

## Phase 3: Settings Overlay, DataStore & About Screen (coarse)
- [ ] P-009 Implement double-tap gesture listener to trigger Settings overlay
- [ ] P-010 Wire Jetpack DataStore preferences for background and segment color selection
- [ ] P-011 Integrate Markdown About screen reading packaged assets (`ABOUT.md`, `CREDITS.md`, `LICENSE`)
- [ ] P-012 Final verification, APK release build signing, and manual checklist run