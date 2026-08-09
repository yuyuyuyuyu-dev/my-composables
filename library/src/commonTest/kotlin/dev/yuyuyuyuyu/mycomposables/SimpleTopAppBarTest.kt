package dev.yuyuyuyuyu.mycomposables

import androidx.compose.material3.Text
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import kotlin.test.assertTrue

// A menu item is located by its label, so the tests that reach one pass a label
// of their own rather than depend on the locale the test runs under.
@OptIn(ExperimentalTestApi::class)
class SimpleTopAppBarTest {
    @Test
    fun `should display the title it was given`() =
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

            // Assert
            onNodeWithText("title").assertIsDisplayed()
        }

    @Test
    fun `should display the navigate back button when navigating back is possible`() =
        runComposeUiTest {
            // Arrange
            setContent {
                SimpleTopAppBar(
                    title = "title",
                    navigateBackIsPossible = true,
                    onNavigateBackButtonClick = {},
                    onOpenSourceLicensesButtonClick = {},
                )
            }

            // Assert
            onNodeWithContentDescription("navigate back").assertIsDisplayed()
        }

    @Test
    fun `should not display the navigate back button when navigating back is not possible`() =
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

            // Assert
            onNodeWithContentDescription("navigate back").assertDoesNotExist()
        }

    @Test
    fun `should call onNavigateBackButtonClick when the navigate back button is clicked`() =
        runComposeUiTest {
            // Arrange
            var isCalled = false
            setContent {
                SimpleTopAppBar(
                    title = "title",
                    navigateBackIsPossible = true,
                    onNavigateBackButtonClick = { isCalled = true },
                    onOpenSourceLicensesButtonClick = {},
                )
            }

            // Act
            onNodeWithContentDescription("navigate back").performClick()

            // Assert
            assertTrue(isCalled)
        }

    @Test
    fun `should call onOpenSourceLicensesButtonClick when the open source licenses button is clicked`() =
        runComposeUiTest {
            // Arrange
            var isCalled = false
            setContent {
                SimpleTopAppBar(
                    title = "title",
                    navigateBackIsPossible = false,
                    onNavigateBackButtonClick = {},
                    onOpenSourceLicensesButtonClick = { isCalled = true },
                    openSourceLicensesButtonLabel = { Text("licenses") },
                )
            }

            // Act
            onNodeWithContentDescription("menu").performClick()
            onNodeWithText("licenses").performClick()

            // Assert
            assertTrue(isCalled)
        }

    @Test
    fun `should display the open source licenses button label it was given`() =
        runComposeUiTest {
            // Arrange
            setContent {
                SimpleTopAppBar(
                    title = "title",
                    navigateBackIsPossible = false,
                    onNavigateBackButtonClick = {},
                    onOpenSourceLicensesButtonClick = {},
                    openSourceLicensesButtonLabel = { Text("licenses") },
                )
            }

            // Act
            onNodeWithContentDescription("menu").performClick()

            // Assert
            onNodeWithText("licenses").assertIsDisplayed()
        }

    @Test
    fun `should display the source code button label it was given`() =
        runComposeUiTest {
            // Arrange
            setContent {
                SimpleTopAppBar(
                    title = "title",
                    navigateBackIsPossible = false,
                    onNavigateBackButtonClick = {},
                    onOpenSourceLicensesButtonClick = {},
                    sourceCodeButtonLabel = { Text("source code") },
                    onSourceCodeButtonClick = {},
                )
            }

            // Act
            onNodeWithContentDescription("menu").performClick()

            // Assert
            onNodeWithText("source code").assertIsDisplayed()
        }

    @Test
    fun `should call onSourceCodeButtonClick when the source code button is clicked`() =
        runComposeUiTest {
            // Arrange
            var isCalled = false
            setContent {
                SimpleTopAppBar(
                    title = "title",
                    navigateBackIsPossible = false,
                    onNavigateBackButtonClick = {},
                    onOpenSourceLicensesButtonClick = {},
                    sourceCodeButtonLabel = { Text("source code") },
                    onSourceCodeButtonClick = { isCalled = true },
                )
            }

            // Act
            onNodeWithContentDescription("menu").performClick()
            onNodeWithText("source code").performClick()

            // Assert
            assertTrue(isCalled)
        }
}
