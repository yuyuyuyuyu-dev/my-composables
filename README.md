# MyComposables

My personal shared composables library.

## Installation

`gradle/libs.versions.toml`

```toml
[versions]
myComposables = "x.x.x" # Please replace with the latest version.

[libraries]
myComposables = { module = "dev.yuyuyuyuyu:my-composables", version.ref = "myComposables" }
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
