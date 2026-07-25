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

## Composables

### `MyMaterialTheme`

A `MaterialTheme` wrapper with a custom color scheme (light / dark) and the bundled Yomogi typography.

```kotlin
MyMaterialTheme {
    // your content
}
```

On Android, `MyMaterialDynamicTheme` additionally uses the Material You dynamic color scheme
on Android 12 (API 31) and above, falling back to the custom scheme on older versions.

```kotlin
MyMaterialDynamicTheme {
    // your content
}
```

### `SimpleTopAppBar`

A simple `TopAppBar` with an optional navigate-back button and a kebab (overflow) menu
containing "Open source licenses" and an optional "Source code" entry.

```kotlin
Scaffold(
    topBar = {
        SimpleTopAppBar(
            title = "Home",
            navigateBackIsPossible = navController.previousBackStackEntry != null,
            onNavigateBackButtonClick = { navController.navigateUp() },
            onOpenSourceLicensesButtonClick = { /* navigate to licenses */ },
            onSourceCodeButtonClick = { /* open source code (optional) */ },
        )
    },
) { innerPadding ->
    // ...
}
```

## Sample apps

The `androidApp`, `desktopApp`, `webApp`, and `iosApp` modules are sample apps that
demonstrate the library (via the `shared` module). They are not published.

- Android: `./gradlew :androidApp:assembleDebug`
- Desktop: `./gradlew :desktopApp:run`
- Web (Wasm): `./gradlew :webApp:wasmJsBrowserDevelopmentRun`
- iOS: open [/iosApp](./iosApp) in Xcode and run.

## Publishing

Publishing to Maven Central is handled by the
[vanniktech/gradle-maven-publish-plugin](https://github.com/vanniktech/gradle-maven-publish-plugin)
on the `:library` module, and runs automatically from
[`.github/workflows/publish.yml`](./.github/workflows/publish.yml) when a GitHub Release is published.

Local verification:

```bash
./gradlew :library:checkSigningConfiguration
./gradlew :library:checkPomFileForMavenPublication
./gradlew :library:publishToMavenLocal
```

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
