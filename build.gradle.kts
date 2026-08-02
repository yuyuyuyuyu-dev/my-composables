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
}
