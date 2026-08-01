# MyComposables

My personal collection of reusable Composables for Android and Compose Multiplatform,
published as a single artifact `dev.yuyuyuyuyu:mycomposables`.

Targets: Android, iOS, Desktop (JVM), Web (Wasm / JS).

## Installation

`gradle/libs.versions.toml`

```toml
[versions]
myComposables = "x.x.x" # Please replace with the latest version.

[libraries]
myComposables = { module = "dev.yuyuyuyuyu:mycomposables", version.ref = "myComposables" }
```

### Android

`app/build.gradle.kts`

```kotlin
dependencies {
    implementation(libs.myComposables)
}
```

### Compose Multiplatform

`composeApp/build.gradle.kts`

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.myComposables)
        }
    }
}
```

## Sample apps

The `androidApp`, `desktopApp`, `webApp`, and `iosApp` modules are sample apps that
demonstrate the library (via the `shared` module). They are not published.

- Android: `./gradlew :androidApp:assembleDebug`
- Desktop: `./gradlew :desktopApp:run`
- Web (Wasm): `./gradlew :webApp:wasmJsBrowserDevelopmentRun`
- iOS: open [/iosApp](./iosApp) in Xcode and run.

## Public API

The public ABI of `:library` is recorded in [`library/api`](./library/api) and checked on
every build, so a change to it fails CI until the dump is updated. This is what catches
changes that still compile for callers but break them at run time, such as reordering
parameters or adding a default value to an existing function.

After deliberately changing the API, refresh the dump and commit it with the change:

```bash
./gradlew :library:updateKotlinAbi
```

The Android target is not covered, because the dump only includes the JVM target and the
klib targets. `MyMaterialDynamicTheme` is therefore unchecked.

## Publishing

Publishing to Maven Central is handled by the
[vanniktech/gradle-maven-publish-plugin](https://github.com/vanniktech/gradle-maven-publish-plugin)
on the `:library` module, and runs automatically from
[`.github/workflows/publish.yml`](./.github/workflows/publish.yml) when a GitHub Release is published.

Local verification:

```bash
./gradlew :library:checkSigningConfiguration
./gradlew :library:checkPomFileForKotlinMultiplatformPublication
./gradlew :library:publishToMavenLocal
```

All publications are signed, so the last two commands need a GPG key to be configured
locally. Without one, use the per-target compile tasks to check the build instead.

## License

Apache License 2.0

```
Copyright 2026 yu

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
