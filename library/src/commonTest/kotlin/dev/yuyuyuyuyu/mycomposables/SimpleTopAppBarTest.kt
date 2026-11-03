package dev.yuyuyuyuyu.mycomposables

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class SimpleTopAppBarTest {
    @Test
    fun `should not display the source code button when onSourceCodeButtonClick is not given`() =
        runComposeUiTest {
            // Arrange
            setContent {
                SimpleTopAppBar(
                    title = "title",
                    navigateBackIsPossible = false,
                    onNavigateBackButtonClick = {},
                    onOpenSourceLicensesButtonClick = {},
                )
            }

            // Act
            onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).performClick()

            // Assert
            onNodeWithTag(SimpleTopAppBarTestTags.SOURCE_CODE_BUTTON).assertDoesNotExist()
        }

    @Test
    fun `should close the menu when the open source licenses button is clicked`() =
        runComposeUiTest {
            // Arrange
            setContent {
                SimpleTopAppBar(
                    title = "title",
                    navigateBackIsPossible = false,
                    onNavigateBackButtonClick = {},
                    onOpenSourceLicensesButtonClick = {},
                )
            }
            onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).performClick()

            // Act
            onNodeWithTag(SimpleTopAppBarTestTags.OPEN_SOURCE_LICENSES_BUTTON).performClick()

            // Assert
            onNodeWithTag(SimpleTopAppBarTestTags.MENU).assertDoesNotExist()
        }

    @Test
    fun `should close the menu when the source code button is clicked`() =
        runComposeUiTest {
            // Arrange
            setContent {
                SimpleTopAppBar(
                    title = "title",
                    navigateBackIsPossible = false,
                    onNavigateBackButtonClick = {},
                    onOpenSourceLicensesButtonClick = {},
                    onSourceCodeButtonClick = {},
                )
            }
            onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).performClick()

            // Act
            onNodeWithTag(SimpleTopAppBarTestTags.SOURCE_CODE_BUTTON).performClick()

            // Assert
            onNodeWithTag(SimpleTopAppBarTestTags.MENU).assertDoesNotExist()
        }
}
