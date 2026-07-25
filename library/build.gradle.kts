import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "dev.yuyuyuyuyu"
version = "0.1.0"

kotlin {
    jvm()

    iosArm64()
    iosSimulatorArm64()

    js {
        browser()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    android {
        namespace = "dev.yuyuyuyuyu.mycomposables.library"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
        androidResources {
            enable = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(compose.materialIconsExtended)
            implementation(libs.createTypography)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

// Generate the resources accessor (Res) under a predictable package.
compose.resources {
    packageOfResClass = "dev.yuyuyuyuyu.mycomposables.generated.resources"
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(group.toString(), "mycomposables", version.toString())

    pom {
        name = "MyComposables"
        description = "My personal collection of reusable Composables for Compose Multiplatform."
        inceptionYear = "2026"
        url = "https://github.com/yuyuyuyuyu-dev/my-composables"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "yuyuyuyuyu"
                name = "yu"
                email = "a@yuyuyuyuyu.dev"
                url = "https://yuyuyuyuyu.dev"
                organization = "yu"
                organizationUrl = "https://yuyuyuyuyu.dev"
            }
        }
        scm {
            url = "https://github.com/yuyuyuyuyu-dev/my-composables"
            connection = "scm:git:git://github.com/yuyuyuyuyu-dev/my-composables.git"
            developerConnection = "scm:git:ssh://git@github.com/yuyuyuyuyu-dev/my-composables.git"
        }
    }
}
