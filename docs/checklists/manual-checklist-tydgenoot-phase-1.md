# Manual Checklist — Tydgenoot Phase 1

- **Project:** Tydgenoot (`cvc.dashingdog.tydgenoot`)
- **Phase:** 1 — Core 7-Segment Clock & Burn-In Shift
- **Build / Version:** `0.1.0` (`versionCode = 1`, debug APK)
- **Target Device:** Lenovo TB3-710F (Android 5.0.1 / API 21)

## Checklist Items

1. **Sideload & Launch (`T-013`, `T-014`)**
   - **Action:** Sideload the built APK onto the Lenovo TB3-710F and launch the app.
   - **Expected:** Opens cleanly into full-screen immersive mode on a pure black (`#000000`) background displaying custom vector-drawn Cyan 7-segment `HH:MM` digits (with convex outward-pointed middle segment tips) and dim ghost segments, plus the discreet `Tydgenoot v0.1.0` About stub in the bottom-right corner.
   - **Traces to:** Spec — *Segmented Clock & Date Display*, *Immersive Display & LCD Burn-In Protection*, *About*.
   - **Result:** [x] Pass — App installed and launched cleanly; digits, convex middle segment tips, and ghost segments rendered crisply.

2. **1 Hz Colon Blink & System Time Sync (`T-005`, `T-013`)**
   - **Action:** Observe the colon between hours and minutes over several minutes and compare minute transitions against the Android system clock.
   - **Expected:** Colon toggles smoothly once per second without stutter, frame drops, or wall-clock minute drift.
   - **Traces to:** Spec — *Segmented Clock & Date Display*.
   - **Result:** [x] Pass — 1 Hz colon blink observed cleanly with accurate minute rollover.

3. **Screen Wake Lock (`T-014`)**
   - **Action:** Leave the clock running continuously on the Lenovo TB3-710F for >15 minutes without touching the screen.
   - **Expected:** `FLAG_KEEP_SCREEN_ON` prevents the screen from dimming, locking, or sleeping.
   - **Traces to:** Spec — *Immersive Display & LCD Burn-In Protection*; Constitution — *Global must-never rules*.
   - **Result:** [x] Pass — Screen remained continuously awake with no dimming or sleep.

4. **Immersive Bar Re-Hide (`T-014`)**
   - **Action:** Swipe inward from the top or bottom edge of the screen to reveal the Android system status/navigation bars, then wait without touching the screen.
   - **Expected:** System bars appear briefly and automatically re-hide via `SYSTEM_UI_FLAG_IMMERSIVE_STICKY` and `onWindowFocusChanged`.
   - **Traces to:** Spec — *Immersive Display & LCD Burn-In Protection* (Edge cases).
   - **Result:** [x] Pass — System bars automatically re-hid cleanly.

5. **5-Minute Burn-In Shift & Zero Clipping (`T-006`, `T-014`)**
   - **Action:** Observe the clock position across a 5-minute interval boundary.
   - **Expected:** The entire clock shifts subtly within `[-24px, +24px]` without any outer segment clipping off the screen edge.
   - **Traces to:** Spec — *Immersive Display & LCD Burn-In Protection*.
   - **Result:** [x] Pass — Subtle ~24px shift occurred across the 5-minute boundary with zero clipping.

6. **Must-Never Verification (`T-004`, `T-013`, `T-014`)**
   - **Action:** Inspect the main clock face visually and inspect project assets/fonts.
   - **Expected:** No temperature readouts, alarm icons, DST badges, persistent settings buttons, or `.ttf`/`.otf` clock fonts exist.
   - **Traces to:** Spec — *Must never* rules; Constitution — *Global must-never rules*.
   - **Result:** [x] Pass — None of the forbidden elements are present.