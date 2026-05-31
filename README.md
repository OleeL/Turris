# Turris

Turris is a Java/LWJGL tower defence game made for Team 62's group software project.

Pick a map, place towers, start waves, and stop enemies from reaching the end of the path. Earn coins from kills, upgrade or sell towers, and survive each round.

![Main Menu](https://i.gyazo.com/3abd4ef60664e07dcf85210ed60deeb0.png "Main Menu")
![Gameplay](https://i.gyazo.com/9d44e3fb0799f39768d18e34e0ee84ed.png "Gameplay")
![Side panel](https://i.gyazo.com/9d9bdadd575a9c24c75555e7f1838b55.png "Side panel")

## Playing

Use the menu to start a new game, continue a save, change settings, or view credits.

In game:

- Open the side panel to buy towers, sell towers, save, change settings, or quit.
- Left click places the selected tower on a valid tile.
- Right click a tower to upgrade it if you have enough coins.
- Use **Sell** from the side panel, then left click a tower to remove it.
- Use **Start / Pause / Play** to control waves.
- Use the speed button to cycle game speed.
- Press **Esc** to cancel the current selection.

## Download

Download the latest release from the GitHub Releases page.

Recommended packages:

- Windows: `Turris-windows-x64-exe-<version>.zip`
- macOS Apple Silicon: `Turris-macos-arm64-dmg-<version>.dmg`
- macOS Intel: `Turris-macos-x64-dmg-<version>.dmg`
- Linux x64: `Turris-linux-x64-executable-<version>.tar.gz`

## Build from source

Requirements:

- Java JDK 8 or newer
- Maven 3.x
- `make` is optional

Build for your current platform:

```sh
mvn package
```

Or:

```sh
make package
```

Run the built jar from the project root:

```sh
java -jar target/turris-1.0.0-natives-<platform>.jar
```

Common jar names:

```text
turris-1.0.0-natives-windows.jar       Windows x64
turris-1.0.0-natives-windows-x86.jar   Windows x86
turris-1.0.0-natives-macos.jar         macOS Intel
turris-1.0.0-natives-macos-arm64.jar   macOS Apple Silicon
turris-1.0.0-natives-linux.jar         Linux x64
turris-1.0.0-natives-linux-arm32.jar   Linux ARM32
turris-1.0.0-natives-linux-arm64.jar   Linux ARM64
```

Build every supported jar:

```sh
make package-all
```

## Notes

The game uses filesystem-based asset loading, so the `assets/` directory must be available beside the packaged jar or native app image.
