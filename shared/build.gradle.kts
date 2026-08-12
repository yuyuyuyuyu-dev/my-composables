import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.aboutLibraries)
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    jvm()

    js {
        browser()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    android {
        namespace = "dev.yuyuyuyuyu.mycomposables.shared"
        compileSdk =
            providers
                .gradleProperty("compileSdk")
                .get()
                .toInt()
        minSdk =
            providers
                .gradleProperty("minSdk")
                .get()
                .toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
        androidResources {
            enable = true
        }
        withHostTest {
            isIncludeAndroidResources = true
        }
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.compose.uiTooling)
        }
        commonMain.dependencies {
            implementation(project(":library"))
            implementation(libs.aboutLibraries.composeCore)
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jsMain.dependencies {
            implementation(libs.wrappers.browser)
        }
    }
}

// MyScaffold's licenses screen is fed by the AboutLibraries Gradle plugin, which
// can only see the dependencies of the module it is applied to. That is this
// module rather than :library, so the sample generates its own list here.
// Not `generated/aboutLibraries`, which the plugin already uses for its own
// per-variant output: its `androidMain` directory would then sit next to
// `files` and be read as a Compose Resources resource type.
val aboutLibrariesResources = layout.buildDirectory.dir("generated/aboutLibrariesComposeResources")

aboutLibraries {
    export {
        outputFile = aboutLibrariesResources.map { it.file("files/aboutlibraries.json") }
    }
}

compose.resources {
    customDirectory(
        sourceSetName = "commonMain",
        // Going through the task provider is what makes the export run before
        // the resources are packaged.
        directoryProvider = tasks.named("exportLibraryDefinitions").map { aboutLibrariesResources.get() },
    )
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}
