# Group Software Project - Team 62 - Turris

Turris is an old LWJGL/OpenGL Java tower defence game.

## Implementation

![Main Menu](https://i.gyazo.com/3abd4ef60664e07dcf85210ed60deeb0.png "Main Menu")
![Gameplay](https://i.gyazo.com/9d44e3fb0799f39768d18e34e0ee84ed.png "Gameplay")
![Side panel](https://i.gyazo.com/9d9bdadd575a9c24c75555e7f1838b55.png "Side panel")

## Requirements

- Java JDK 8 or newer
- Maven 3.x
- `make` is optional, but convenient

The project uses Maven to download LWJGL and the platform-specific native jars. The old checked-in `lib/`, `natives/`, and `target/` outputs are no longer needed for builds.

## Build

Build for the current machine:

```sh
mvn package
```

Or with the Makefile:

```sh
make package
```

The executable jar is written to `target/` and includes the selected LWJGL native libraries.

## Run locally

The game currently loads assets from a filesystem path named `assets/...`, so run the jar from inside `target/`, where Maven copies the packaged `assets/` directory:

```sh
mvn package
cd target
java -jar turris-1.0.0-natives-macos-arm64.jar
```

Use the jar that matches your platform from inside `target/`:

```text
turris-1.0.0-natives-windows.jar       Windows x64
turris-1.0.0-natives-windows-x86.jar   Windows x86
turris-1.0.0-natives-macos.jar         macOS Intel
turris-1.0.0-natives-macos-arm64.jar   macOS Apple Silicon
turris-1.0.0-natives-linux.jar         Linux x64
turris-1.0.0-natives-linux-arm32.jar   Linux ARM32
turris-1.0.0-natives-linux-arm64.jar   Linux ARM64
```

On Apple Silicon Macs, use:

```sh
cd target
java -jar turris-1.0.0-natives-macos-arm64.jar
```

On Intel Macs, use:

```sh
cd target
java -jar turris-1.0.0-natives-macos.jar
```

## Build all release jars

To build all supported platform jars:

```sh
make package-all
```

Equivalent Maven commands:

```sh
mvn clean
mvn -P windows-x64 package
mvn -P windows-x86 package
mvn -P macos-x64 package
mvn -P macos-arm64 package
mvn -P linux-x64 package
mvn -P linux-arm32 package
mvn -P linux-arm64 package
```

Release artifacts are created in `target/`.

## Automated GitHub releases

This repository has a GitHub Actions release workflow in `.github/workflows/release.yml`.

On every push to `main`, the workflow:

1. calculates a version with GitVersion using `GitVersion.yml`
2. creates a release git tag
3. builds all supported platform jars
4. packages each jar with the `assets/` directory
5. builds native launchers/installers where GitHub-hosted runners support them
6. publishes all artifacts to a GitHub Release

The workflow can also be started manually from the GitHub Actions tab with `workflow_dispatch`.

Release assets include jar bundles for all supported LWJGL native classifiers, plus native launcher packages for common desktop targets:

```text
Turris-windows-x64-<version>.zip              Jar bundle
Turris-windows-x86-<version>.zip              Jar bundle
Turris-macos-arm64-<version>.zip              Jar bundle
Turris-macos-x64-<version>.zip                Jar bundle
Turris-linux-x64-<version>.zip                Jar bundle
Turris-linux-arm32-<version>.zip              Jar bundle
Turris-linux-arm64-<version>.zip              Jar bundle
Turris-windows-x64-exe-<version>.zip          Windows app image containing Turris.exe
Turris-macos-arm64-dmg-<version>.dmg          macOS Apple Silicon DMG
Turris-macos-x64-dmg-<version>.dmg            macOS Intel DMG
Turris-linux-x64-executable-<version>.tar.gz  Linux app image containing executable launcher
```

GitHub-hosted runners do not provide a 32-bit Windows or 32-bit Linux environment for `jpackage`, so the native launcher artifacts are currently x64/Apple-Silicon only. Windows x86 is still published as a jar bundle.

## Packaging a release manually

Each released platform needs two things:

1. the matching `target/turris-1.0.0-*.jar`
2. the `target/assets/` directory next to it, renamed or copied as `assets/`

Example release layout:

```text
Turris-macos-arm64/
├── assets/
└── turris-1.0.0-natives-macos-arm64.jar
```

Run from inside that release directory:

```sh
java -jar turris-1.0.0-natives-macos-arm64.jar
```

Example packaging command:

```sh
make package-all
mkdir -p release/Turris-macos-arm64
cp target/turris-1.0.0-natives-macos-arm64.jar release/Turris-macos-arm64/
cp -R target/assets release/Turris-macos-arm64/assets
```

Repeat with the relevant jar for each target platform.

## Notes

- LWJGL `3.3.1` does not publish a 32-bit x86 Linux native classifier, so true Linux x86/32-bit is not currently supported via Maven Central.
- Linux ARM32 is supported as `natives-linux-arm32`.
- The game uses old fixed-function OpenGL and filesystem-based asset loading. If asset loading is modernized later, the `assets` directory may no longer need to sit beside the jar.
