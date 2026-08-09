package dev.yuyuyuyuyu.mycomposables

import androidx.compose.ui.window.ComposeUIViewController

// Named in PascalCase because iosApp calls it from Swift as `MainViewController()`,
// which is the name the Kotlin Multiplatform template expects.
@Suppress("ktlint:standard:function-naming", "FunctionNaming")
fun MainViewController() = ComposeUIViewController { App() }
