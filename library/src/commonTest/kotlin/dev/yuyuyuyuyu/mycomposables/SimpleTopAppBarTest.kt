package dev.yuyuyuyuyu.mycomposables

import androidx.compose.material3.Text
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import kotlin.test.assertTrue

// The labels are passed rather than left at their defaults, so that finding a
// menu item does not depend on which locale the test happens to run under.
@OptIn(ExperimentalTestApi::class)
class SimpleTopAppBarTest {
    @Test
    fun `should call onOpenSourceLicensesButtonClick when the open source licenses item is clicked`() =
        runComposeUiTest {
            // Arrange
            var openSourceLicensesIsRequested = false
            setContent {
                SimpleTopAppBar(
                    title = "title",
                    navigateBackIsPossible = false,
                    onNavigateBackButtonClick = {},
                    onOpenSourceLicensesButtonClick = { openSourceLicensesIsRequested = true },
                    openSourceLicensesButtonLabel = { Text("licenses") },
                )
            }

            // Act
            onNodeWithContentDescription("menu").performClick()
            onNodeWithText("licenses").performClick()

            // Assert
            assertTrue(openSourceLicensesIsRequested)
        }

    @Test
    fun `should close the menu once the open source licenses item is clicked`() =
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
            onNodeWithText("licenses").performClick()

            // Assert
            onAllNodesWithText("licenses").assertCountEquals(0)
        }

    @Test
    fun `should call onNavigateBackButtonClick when the navigate back button is clicked`() =
        runComposeUiTest {
            // Arrange
            var navigatingBackIsRequested = false
            setContent {
                SimpleTopAppBar(
                    title = "title",
                    navigateBackIsPossible = true,
                    onNavigateBackButtonClick = { navigatingBackIsRequested = true },
                    onOpenSourceLicensesButtonClick = {},
                )
            }

            // Act
            onNodeWithContentDescription("navigate back").performClick()

            // Assert
            assertTrue(navigatingBackIsRequested)
        }

    @Test
    fun `should show no navigate back button when navigating back is not possible`() =
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
            onAllNodesWithContentDescription("navigate back").assertCountEquals(0)
        }

    @Test
    fun `should call onSourceCodeButtonClick when the source code item is clicked`() =
        runComposeUiTest {
            // Arrange
            var sourceCodeIsRequested = false
            setContent {
                SimpleTopAppBar(
                    title = "title",
                    navigateBackIsPossible = false,
                    onNavigateBackButtonClick = {},
                    onOpenSourceLicensesButtonClick = {},
                    openSourceLicensesButtonLabel = { Text("licenses") },
                    sourceCodeButtonLabel = { Text("source code") },
                    onSourceCodeButtonClick = { sourceCodeIsRequested = true },
                )
            }

            // Act
            onNodeWithContentDescription("menu").performClick()
            onNodeWithText("source code").performClick()

            // Assert
            assertTrue(sourceCodeIsRequested)
        }

    @Test
    fun `should show no source code item when onSourceCodeButtonClick is not set`() =
        runComposeUiTest {
            // Arrange
            setContent {
                SimpleTopAppBar(
                    title = "title",
                    navigateBackIsPossible = false,
                    onNavigateBackButtonClick = {},
                    onOpenSourceLicensesButtonClick = {},
                    openSourceLicensesButtonLabel = { Text("licenses") },
                    sourceCodeButtonLabel = { Text("source code") },
                )
            }

            // Act
            onNodeWithContentDescription("menu").performClick()

            // Assert
            onAllNodesWithText("source code").assertCountEquals(0)
        }

    @Test
    fun `should show the title it was given`() =
        runComposeUiTest {
            // Arrange
            setContent {
                SimpleTopAppBar(
                    title = "MyComposables",
                    navigateBackIsPossible = false,
                    onNavigateBackButtonClick = {},
                    onOpenSourceLicensesButtonClick = {},
                )
            }

            // Assert
            onAllNodesWithText("MyComposables").assertCountEquals(1)
        }
}
