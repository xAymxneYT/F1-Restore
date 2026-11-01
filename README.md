# F1 Restore

A client-side mod for **Minecraft 1.8.9, Ornithe Gen2 and OneClient**.

It retains Minecraft's F1 hide-GUI state and draws selected vanilla HUD components separately, bypassing the shared HUD render entry point during optimized mode.

## Controls

- **F1:** normal HUD hiding.
- **F4:** optimized mode with your selected HUD elements.
- **Right Shift → F1 Restore:** native OneClient settings, including key rebinding.

Pressing F1 from optimized mode switches to clean F1. Pressing either mode's key again returns to normal view.

## Features

- Per-element hotbar, crosshair, chat, status-bar, scoreboard and overlay controls.
- Optional PolyCrosshair integration and first-person hand support.
- Native OneConfig settings and rebindable mode keys.
- Experimental scoped hotbar depth clear and optional CPU timing report.
- Add-on HUD rendering exists in v1.4.1 but is OFF by default because invoking OneConfig's full HUD renderer can remove the performance benefit.

This version does **not** cache HUD images. FPS improvements depend on your configuration and hardware. Key/rendering regression checks used an instrumented backend; full OneClient integration is tested by users.

## Install

Put the built JAR into your OneClient instance's `mods` folder, remove older F1 Restore versions and restart. This is not a Forge mod or a modern Fabric Minecraft mod.

## Build

Requirements: Java 17, Python 3, a POSIX shell and network access.

```sh
python3 tools/fetch_dependencies.py
bash build.sh
```

Output: `build/libs/f1-restore-1.8.9-ornithe-1.4.1.jar`.

The dependency script downloads the official Minecraft 1.8.9 client, Calamus Gen2 mappings, ECJ and compile-time API dependencies. It remaps Minecraft locally and prepares OneConfig compile signatures. No Minecraft, OneConfig or other dependency classes are bundled in the mod JAR or tracked in this repository.

GitHub Actions also builds the JAR and uploads it as a workflow artifact.

## Development notes

The source currently targets Calamus Gen2 intermediary names directly. The build uses ECJ rather than Gradle/Loom. Existing OneConfig preferences keep their `f1restore.json` identity; legacy visibility settings use `config/f1restore.properties`.

## License

MIT. See [LICENSE](LICENSE).
