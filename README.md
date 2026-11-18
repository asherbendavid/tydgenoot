# Tydgenoot

**Tydgenoot** (`cvc.dashingdog.tydgenoot`) is a minimalist, full-screen digital wall clock built with custom-rendered LED segments using Jetpack Compose. It is specifically designed to repurpose older Android tablets (such as the Lenovo TB3-710F running Android 5.0.1) into a dedicated, clean wall clock without network bloat, ambient clutter, or sleep/dim interruptions[cite: 1, 8, 9].

## Features (Completed & Tested)
- **Custom 7-Segment Canvas Engine:** Renders hours, minutes, and the blinking colon with crisp vector path geometry and illuminated/dim ghost segment states.
- **Burn-In Protection:** Automatically applies a subtle ~24px pseudo-random shift every 5 minutes to prevent image retention on LCD panels.
- **Unobtrusive Operation:** Runs in full-screen immersive mode with `FLAG_KEEP_SCREEN_ON` permanently active[cite: 8, 9].
- **Embedded Documentation:** Built-in About screen rendering real root repository documentation (`ABOUT.md`, `CREDITS.md`, and `LICENSE`) dynamically via Markdown[cite: 9].

## Installation & Running
1. Clone or place the repository in an apostrophe-free path (e.g., `C:\Dev\Tydgenoot`)[cite: 8].
2. Open the project in Android Studio (Quail or newer).
3. Run Gradle sync using the bundled version catalog and AGP 9.4.0 / Compose BOM 2024.09.00[cite: 11, 12].
4. Build and sideload the APK onto your target Android device (API 21+)[cite: 1, 8].

## Limitations
- Designed strictly as a fixed wall clock display; does not include alarms, weather forecasts, or audio features[cite: 9].

## License & Attribution
- Licensed under the **BSD 3-Clause License** (see `LICENSE`)[cite: 6].
- See `ATTRIBUTION_GUIDELINES.md` for non-binding recommendations regarding attribution[cite: 6].
- See `CREDITS.md` for project inspirations and library credits[cite: 1].
```[cite: 8, 9]

---

### 2. `CREDITS.md`
```markdown
Credits & Inspiration
=====================

Inspired by:
------------
Commercial minimalist digital LED wall clocks showing time, day-of-week indicator, and full calendar dates, alongside the practical need to repurpose older hardware like the Lenovo TB3-710F tablet[cite: 1, 8, 9].

Patterned on:
-------------
Classic 7-segment hardware LED displays, adapted for vector Canvas rendering in Jetpack Compose without font file scaling artifacts[cite: 1, 8, 9].

Previously used app for this purpose:
-------------------------------------
None.

Built-in libraries & tools:
---------------------------
- Jetpack Compose, AndroidX ViewModel, and Jetpack DataStore (Preferences)[cite: 1, 8]
- Multiplatform Markdown Renderer (`com.mikepenz:multiplatform-markdown-renderer-m3`) for About screen asset rendering
- Agentic engineering assistance from Claude (Anthropic) and Gemini (Google)[cite: 1, 7]

Media credits:
--------------
**Sound effects**
- None.

**Thanks to:**
- Everyone contributing to the Android open-source ecosystem and Jetpack Compose toolchain.
```[cite: 1, 7, 8, 9]

---

### 3. `LICENSE`
```text
BSD 3-Clause License

Copyright (c) 2026, asherbendavid
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be endorsed or promoted to derive products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
```[cite: 6]

---

### 4. `ATTRIBUTION_GUIDELINES.md`
```markdown
Attribution Guidelines (Non-Binding) — v1.0
===========================================

This project is licensed under BSD 3-Clause (see `LICENSE`). That license is
the only legally binding term governing use of this code. Nothing in this
document is a license term, and nothing here is enforceable — it's a note about
the spirit this project is offered in.

What the author would genuinely appreciate
------------------------------------------

- **Keep attribution visible to end users**, not just buried in source
  comments. A mention in an About screen, credits page, or README goes a long
  way — something like "includes code originally by asherbendavid."

- **If you release a paid or substantially improved version**, consider
  linking back to the free original somewhere a user could reasonably find it.
  The idea: if someone builds something better and charges for it, that's
  great — but people should still be able to discover a free version exists
  and choose for themselves.

- **Endorsement isn't implied.** Using the author's name doesn't mean they've
  reviewed, approved, or partnered on your product — just that your work is
  built on theirs.

Why this exists as a separate file
----------------------------------

Keeping the project on a standard, recognized open-source license means tooling,
companies, and courts treat it predictably. This file exists to be transparent
about what the author *hopes* people do, beyond what's legally required — a
request from one developer to another, not a rule.

*This document is based on the "Attribution Guidelines" template (v1.0) — see
[github.com/asherbendavid/attribution-guidelines] for the source and permission
to reuse.*
```[cite: 6]

---

### 5. `ABOUT.md`
```markdown
# About Tydgenoot

**Tydgenoot** is a distraction-free, full-screen digital wall clock engineered specifically to breathe new life into older Android tablets mounted as permanent room fixtures[cite: 8, 9]. 

## Design Philosophy
- **Authentic Aesthetics:** Recreates the charm of classic hardware LED segment displays using lightweight vector drawing instructions rather than rigid bitmap font files[cite: 8, 9].
- **Zero Clutter:** Excludes weather widgets, alarms, and unnecessary network services to focus purely on displaying time and date cleanly[cite: 9].
- **Hardware Longevity:** Incorporates automatic screen burn-in shifting to protect older display panels during 24/7 plugged-in operation[cite: 8, 9].

## Built With
- Kotlin & Jetpack Compose[cite: 8]
- AndroidX ViewModel & Jetpack DataStore[cite: 1, 8]
- Multiplatform Markdown Renderer[cite: 7]
```[cite: 1, 7, 8, 9]

---

<FollowUp label="Shall we complete the Phase 0 sign-off, draft the handoff and manual checklist, and move on to Phase 1?" query="Yes, complete the Phase 0 sign-off and draft the handoff and manual checklist documents."/>