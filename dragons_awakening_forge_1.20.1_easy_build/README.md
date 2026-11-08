# Dragon's Awakening (Forge 1.20.1)

A starter mod project for Minecraft Java 1.20.1 / Forge 47.2.0 / Java 17.

## Included in this starter build
- Detects an active Ender Dragon encounter in the End.
- Displays an intro title/fade overlay when a dragon is detected.
- Starts a custom streamed boss-theme sound after the intro.
- Displays a victory title/fade overlay when the dragon dies.

## Add your own boss music
This project intentionally does not include copyrighted music.

1. Create or use an original/ properly licensed OGG music track.
2. Convert it to `.ogg` if needed.
3. Put it at:
   `src/main/resources/assets/dragons_awakening/sounds/boss_theme.ogg`
4. Keep the name exactly `boss_theme.ogg`.

## Build
- Install a Java 17 JDK.
- Install Gradle 8.x (or generate the Forge MDK wrapper for ForgeGradle 6).
- From this folder run: `gradle build`
- The mod jar will be in `build/libs/`.

For development, import the folder as a Gradle project in IntelliJ IDEA or Eclipse.
Run `gradle genIntellijRuns` (or the appropriate IDE run-configuration task) if needed.

## Current limitations / next milestones
This is a first playable foundation, not the final cinematic implementation:
- The intro and victory are HUD overlays, not a fully detached cinematic camera.
- The vanilla dragon death sequence remains active; this mod adds a victory presentation over it.
- The music hook needs your OGG file.
- Multiplayer synchronization and edge cases should be tested on a dedicated server before release.

## Project details
- Minecraft: 1.20.1
- Forge: 47.2.0
- Java: 17
- Mod ID: `dragons_awakening`


## Easiest build option (GitHub Actions)
If you do not want to install Gradle:
1. Create a new GitHub repository.
2. Upload the contents of this ZIP to the repository (not the ZIP itself).
3. Open the repository's **Actions** tab.
4. Select **Build Dragon's Awakening** and click **Run workflow**.
5. When the run finishes, open it and download the artifact named
   `dragons-awakening-forge-1.20.1`.
6. Inside the downloaded artifact ZIP is the mod `.jar`.
