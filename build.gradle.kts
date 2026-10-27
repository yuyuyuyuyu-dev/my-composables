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
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ktlint)
    alias(libs.plugins.versionCatalogUpdate)
}

val composeVersion = libs.versions.composeMultiplatform.get()
val composeGroups =
    setOf(
        "org.jetbrains.compose.animation",
        "org.jetbrains.compose.components",
        "org.jetbrains.compose.foundation",
        "org.jetbrains.compose.material",
        "org.jetbrains.compose.runtime",
        "org.jetbrains.compose.ui",
    )

allprojects {
    val guardedGroups = composeGroups
    val newerThanCompose = "($composeVersion,)"

    dependencies.components.all {
        allVariants {
            withDependencies {
                filter { it.group in guardedGroups }.forEach { it.version { reject(newerThanCompose) } }
            }
        }
    }
}

ktlint {
    version = libs.versions.ktlintCli.get()
}
