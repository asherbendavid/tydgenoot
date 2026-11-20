# Tydgenoot — Plan

*Workflow set v2.0. Maintained by the coordinator.*

Highest numbers issued: D-06, S-00

## Architecture (as built)
| Module or file | Responsibility | Tier |
|---|---|---|
| `model/` | Pure Kotlin 7-segment bitmasks (`SegmentMapper`), date/day formatting (`DateFormatter`), burn-in offset math (`BurnInOffsetCalculator`) | Important |
| `ui/clock/` | `ClockViewModel` + `StateFlow`, custom 7-segment `Canvas` rendering (`SegmentClockRenderer`), `DayOfWeekIndicator`, `DateRow`, `LandscapeClockLayout`, `PortraitClockLayout`, burn-in pixel clamping | Important |
| `data/` | Jetpack DataStore preferences repository for segment color, background color, and leading-zero persistence (Phase 3) | Important |
| `ui/settings/` | Double-tap Settings overlay, preset + custom Compose color picker, background selector, leading-zero switch, and navigation to About (Phase 3) | Important |
| `about/` | Embedded asset loader and `multiplatform-markdown-renderer-m3` About screen rendering `ABOUT.md`, `CREDITS.md`, and `LICENSE` (Phase 3) | Cosmetic |

## Decision log
| ID | Type | Decision | Reasoning | Phase | Status |
|---|---|---|---|---|---|
| D-01 | choice | Use nagskof stack (AGP 9.4.0, Kotlin 2.0.21, Compose BOM 2024.09.00) | Proven stable and compatible with API 21 on target hardware | Phase 0 | active |
| D-02 | choice | Custom Canvas 7-segment geometry over external fonts | Guarantees sharp scaling and avoids font-loading anomalies on API 21 | Phase 0 | active |
| D-03 | choice | ~24px random burn-in shift every 5 minutes | Prevents LCD ghosting during 24/7 docked wall display | Phase 0 | active |
| D-04 | choice | User-configurable leading-hour zero toggle | Supports both `08:30` and ` 8:30` aesthetic preferences | Phase 1 | active |
| D-05 | choice | Use standard `Text` composables for day-of-week labels (`MON`–`SUN` in Landscape, compact `M`–`S` in Portrait) and scale landscape time ~0.6x height, date ~0.5x time height, and day labels ~0.17x time height | Keeps day labels crisp without hand-drawing letter geometry, fits all 7 days cleanly in narrow portrait width, and matches `tydgenoot-inspiration.jpg` proportions | Phase 2 | active |
| D-06 | choice | Clamp burn-in translation offsets dynamically against container vs. content slack in pixels before applying `graphicsLayer` translation | Guarantees that the periodic ~24px burn-in shift never pushes outer segments or labels off-screen even in tight viewports | Phase 2 | active |

## Spike log
### S-00
- Question: Can the chosen Compose BOM and a lightweight Markdown library compile and render embedded `ABOUT.md` on API 21 without runtime linkage errors?
- Method: Scaffold standalone project in `C:\Dev\Tydgenoot`, wire Gradle asset copy task (`copyRootDocsToAssets`), and verify sync/build on Android 5.0.1 target stack.
- Result: Verified. `multiplatform-markdown-renderer-m3` (`0.27.0`) and `copyRootDocsToAssets` compile cleanly under AGP 9.4.0 / Gradle 9.6+ with zero errors or deprecation warnings.
- Measured: Clean Gradle sync and build; root docs copied to `build/generated/assets/rootDocs`.
- Files: `app/build.gradle.kts`, `gradle/libs.versions.toml`
- Phase: Phase 0
- Status: complete

## Build sequence
1. Phase 0: Foundation spike (S-00), scaffold repo structure, and build verification (complete).
2. Phase 1: Core 7-segment clock engine, system clock sync, immersive wake lock, and burn-in shift logic (complete).
3. Phase 2: Layout responsiveness (Landscape & Portrait modes) and day-of-week / date formatting (complete).
4. Phase 3: DataStore persistence, double-tap Settings overlay, color customization, About screen integration, and release verification.
