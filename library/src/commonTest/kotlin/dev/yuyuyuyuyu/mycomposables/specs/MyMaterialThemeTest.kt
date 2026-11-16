package dev.yuyuyuyuyu.mycomposables.specs

import androidx.compose.material3.Text
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import dev.yuyuyuyuyu.mycomposables.MyMaterialTheme
import kotlin.test.Test
import kotlin.time.TimeSource

@OptIn(ExperimentalTestApi::class)
class MyMaterialThemeTest {
    @Test
    fun `should display the content it was given`() =
        runComposeUiTest {
            val testStart = TimeSource.Monotonic.markNow()
            // Arrange
            step("T0", "00 setContent") {
                setContent {
                    MyMaterialTheme {
                        Text("content")
                    }
                }
            }

            // Assert
            step("T0", "01 assertIsDisplayed content") {
                onNodeWithText("content").assertIsDisplayed()
            }
            println("STEP|T0|total-in-block|${testStart.elapsedNow().inWholeMilliseconds}ms")
        }
}

@OptIn(ExperimentalTestApi::class)
private fun ComposeUiTest.step(
    test: String,
    name: String,
    block: ComposeUiTest.() -> Unit,
) {
    val virtualStart = mainClock.currentTime
    val mark = TimeSource.Monotonic.markNow()
    block()
    waitForIdle()
    val elapsed = mark.elapsedNow().inWholeMilliseconds
    val virtual = mainClock.currentTime - virtualStart
    println("STEP|$test|$name|${elapsed}ms|virtual=${virtual}ms")
}
