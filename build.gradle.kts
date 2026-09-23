import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

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
val composeModules =
    listOf(
        "org.jetbrains.compose.animation:animation",
        "org.jetbrains.compose.components:components-resources",
        "org.jetbrains.compose.foundation:foundation",
        "org.jetbrains.compose.material:material",
        "org.jetbrains.compose.runtime:runtime",
        "org.jetbrains.compose.ui:ui",
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

    val composeGuard = configurations.dependencyScope("composeGuard")

    dependencies {
        composeModules.forEach { module ->
            constraints.add(composeGuard.name, module) {
                version { reject("($composeVersion,)") }
                because("Compose Multiplatform $composeVersion bundles the Skiko runtime of its own version only.")
            }
        }
    }

    plugins.withId("org.jetbrains.kotlin.multiplatform") {
        extensions.getByType<KotlinMultiplatformExtension>().targets.configureEach {
            compilations.configureEach {
                listOfNotNull(compileDependencyConfigurationName, runtimeDependencyConfigurationName).forEach { name ->
                    configurations.named(name) { extendsFrom(composeGuard.get()) }
                }
            }
        }
    }
}
