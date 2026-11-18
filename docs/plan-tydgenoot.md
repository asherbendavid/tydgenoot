# Tydgenoot — Plan

*Workflow set v2.0. Maintained by the coordinator.*

Highest numbers issued: D-03, S-00

## Architecture (as built)
| Module or file | Responsibility | Tier |
|---|---|---|
| `ui/clock/` | Custom 7-segment Canvas rendering, colon blinker, burn-in translation math | Important |
| `ui/settings/` | Color picker, background selection, About screen navigation | Important |
| `data/` | Jetpack DataStore preferences repository for theme persistence | Important |
| `about/` | Markdown parser integration rendering `ABOUT.md`, `CREDITS.md`, and `LICENSE` | Cosmetic |

## Decision log
| ID | Type | Decision | Reasoning | Phase | Status |
|---|---|---|---|---|---|
| D-01 | choice | Use nagskof stack (AGP 9.4.0, Kotlin 2.0.21, Compose BOM 2024.09.00) | Proven stable and compatible with API 21 on target hardware | Phase 0 | active |
| D-02 | choice | Custom Canvas 7-segment geometry over external fonts | Guarantees sharp scaling and avoids font-loading anomalies on API 21 | Phase 0 | active |
| D-03 | choice | ~24px random burn-in shift every 5 minutes | Prevents LCD ghosting during 24/7 docked wall display | Phase 0 | active |

## Spike log
### S-00
- Question: Can the chosen Compose BOM and a lightweight Markdown library compile and render embedded `ABOUT.md` on API 21 without runtime linkage errors?
- Method: Scaffold standalone spike project in `C:\Dev\Tydgenoot`, wire Gradle asset copy task, and verify on Android 5.0.1 device.
- Result: Pending Phase 0 execution.
- Measured: TBD.
- Files: `spike/S-00-foundation/`
- Phase: Phase 0
- Status: active

## Build sequence
1. Phase 0: Foundation spike (S-00), scaffold repo structure, and build verification.
2. Phase 1: Core 7-segment clock engine, system clock sync, and burn-in shift logic.
3. Phase 2: Layout responsiveness (Landscape & Portrait modes) and day-of-week / date formatting.
4. Phase 3: DataStore persistence, Settings overlay, color customization, and About screen integration.