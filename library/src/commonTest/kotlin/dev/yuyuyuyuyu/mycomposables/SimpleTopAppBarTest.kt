package dev.yuyuyuyuyu.mycomposables

import androidx.compose.material3.Text
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import kotlin.test.assertTrue

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
            onNodeWithTag(SimpleTopAppBarTestTags.NAVIGATE_BACK_BUTTON).assertIsDisplayed()
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
            onNodeWithTag(SimpleTopAppBarTestTags.NAVIGATE_BACK_BUTTON).assertDoesNotExist()
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
            onNodeWithTag(SimpleTopAppBarTestTags.NAVIGATE_BACK_BUTTON).performClick()

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
                )
            }

            // Act
            onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).performClick()
            onNodeWithTag(SimpleTopAppBarTestTags.OPEN_SOURCE_LICENSES_BUTTON).performClick()

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
            onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).performClick()

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
            onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).performClick()

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
                    onSourceCodeButtonClick = { isCalled = true },
                )
            }

            // Act
            onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).performClick()
            onNodeWithTag(SimpleTopAppBarTestTags.SOURCE_CODE_BUTTON).performClick()

            // Assert
            assertTrue(isCalled)
        }

    @Test
    fun `should be able to find the navigate back button by its test tag`() =
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
            onNodeWithTag(SimpleTopAppBarTestTags.NAVIGATE_BACK_BUTTON).assertExists()
        }

    @Test
    fun `should be able to find the menu button by its test tag`() =
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
            onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).assertExists()
        }

    @Test
    fun `should be able to find the menu by its test tag`() =
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
            onNodeWithTag(SimpleTopAppBarTestTags.MENU).assertExists()
        }

    @Test
    fun `should be able to find the open source licenses button by its test tag`() =
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
            onNodeWithTag(SimpleTopAppBarTestTags.OPEN_SOURCE_LICENSES_BUTTON).assertExists()
        }

    @Test
    fun `should be able to find the source code button by its test tag`() =
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

            // Act
            onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).performClick()

            // Assert
            onNodeWithTag(SimpleTopAppBarTestTags.SOURCE_CODE_BUTTON).assertExists()
        }
}
