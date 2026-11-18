# Manual Checklist — Tydgenoot Phase 0 (Foundation)
- **Build Version:** 0.1.0-debug
- **Device:** Lenovo TB3-710F (Android 5.0.1, API 21) / Emulator

| Item | Action | Expected | Result |
|---|---|---|---|
| 1 | Sync Gradle in Android Studio | Syncs successfully with zero errors or deprecated warnings | [ ] Pass |
| 2 | Build debug APK | APK compiles successfully with custom timestamp/version naming | [ ] Pass |
| 3 | Sideload and launch on physical device | App launches on Android 5.0.1 without `NoClassDefFoundError` or crashes | [ ] Pass |
| 4 | Verify `.gitignore` and `spike/` | Root `.gitignore` correctly ignores build artifacts and local files (`git check-ignore -v`) | [ ] Pass |
