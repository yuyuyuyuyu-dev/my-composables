package dev.yuyuyuyuyu.mycomposables

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.mikepenz.aboutlibraries.Libs
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class MyScaffoldTest {
    private val libraries = Libs(libraries = emptyList(), licenses = emptySet())

    @Test
    fun `should call onSourceCodeButtonClick when the source code button is clicked`() =
        runComposeUiTest {
            // Arrange
            var isCalled = false
            setContent {
                MyScaffold(
                    title = "title",
                    libraries = libraries,
                    onSourceCodeButtonClick = { isCalled = true },
                ) {}
            }

            // Act
            onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).performClick()
            onNodeWithTag(SimpleTopAppBarTestTags.SOURCE_CODE_BUTTON).performClick()

            // Assert
            assertTrue(isCalled)
        }

    @Test
    fun `should display the title it was given after navigating back to the main screen`() =
        runComposeUiTest {
            // Arrange
            setContent {
                MyScaffold(title = "title", libraries = libraries) {}
            }
            onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).performClick()
            onNodeWithTag(SimpleTopAppBarTestTags.OPEN_SOURCE_LICENSES_BUTTON).performClick()

            // Act
            onNodeWithTag(SimpleTopAppBarTestTags.NAVIGATE_BACK_BUTTON).performClick()

            // Assert
            onNodeWithText("title").assertIsDisplayed()
        }

    @Test
    fun `should not display the source code button when onSourceCodeButtonClick is not given`() =
        runComposeUiTest {
            // Arrange
            setContent {
                MyScaffold(title = "title", libraries = libraries) {}
            }

            // Act
            onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).performClick()

            // Assert
            onNodeWithTag(SimpleTopAppBarTestTags.SOURCE_CODE_BUTTON).assertDoesNotExist()
        }
}
