plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidMultiplatformLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.vanniktech.mavenPublish) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.versionCatalogUpdate)
}

// Resolved here because the version catalog accessor is not available inside
// the `allprojects` block.
val composeRulesKtlint = libs.composeRules.ktlint

val composeVersion = libs.versions.composeMultiplatform.get()
val material3Version = libs.versions.material3.get()
val composeGroups =
    setOf(
        "org.jetbrains.compose.animation",
        "org.jetbrains.compose.components",
        "org.jetbrains.compose.foundation",
        "org.jetbrains.compose.material",
        "org.jetbrains.compose.runtime",
        "org.jetbrains.compose.ui",
    )

check(composeVersion.split(".").take(2) == material3Version.split(".").take(2)) {
    "material3 $material3Version is not on the line of Compose Multiplatform $composeVersion."
}

allprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    detekt {
        // Without a config file detekt reports nothing at all, and without
        // this flag the file replaces the defaults instead of adding to them.
        buildUponDefaultConfig = true
        config.setFrom(rootProject.file("config/detekt/detekt.yml"))
        // The default source dirs follow the JVM layout, so every multiplatform
        // source set (commonMain, androidMain, iosMain, ...) would be skipped.
        source.setFrom(files("src"))
    }

    ktlint {
        filter {
            // Compose Resources generates sources under `build`, and they are
            // picked up as part of the Kotlin source sets.
            exclude { it.file.path.contains("${File.separator}build${File.separator}") }
        }
    }

    dependencies {
        add("ktlintRuleset", composeRulesKtlint)
    }

    val guardedGroups = composeGroups
    val newerThanCompose = "($composeVersion,)"
    val nextMaterial3Line =
        composeVersion.split(".").let { (major, minor) -> "[$major.${minor.toInt() + 1},)" }

    dependencies.components.all {
        allVariants {
            withDependencies {
                filter { it.group in guardedGroups }.forEach { it.version { reject(newerThanCompose) } }
                filter { it.group == "org.jetbrains.compose.material3" }.forEach { it.version { reject(nextMaterial3Line) } }
            }
        }
    }
}
