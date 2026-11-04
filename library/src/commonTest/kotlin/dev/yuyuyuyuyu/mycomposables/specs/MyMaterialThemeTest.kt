package dev.yuyuyuyuyu.mycomposables.specs

import androidx.compose.material3.Text
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import dev.yuyuyuyuyu.mycomposables.MyMaterialTheme
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class MyMaterialThemeTest {
    @Test
    fun `should display the content it was given`() =
        runComposeUiTest {
            // Arrange
            setContent {
                MyMaterialTheme {
                    Text("content")
                }
            }

            // Assert
            onNodeWithText("content").assertIsDisplayed()
        }
}
