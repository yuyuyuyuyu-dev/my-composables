package dev.yuyuyuyuyu.mycomposables

import androidx.compose.material3.Text
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.entity.Library
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class MyScaffoldTest {
    private val libraries =
        Libs(
            libraries =
                listOf(
                    Library(
                        uniqueId = "dev.yuyuyuyuyu:mycomposables",
                        artifactVersion = "0.1.0",
                        name = "MyComposables",
                        description = null,
                        website = null,
                        developers = emptyList(),
                        organization = null,
                        scm = null,
                    ),
                ),
            licenses = emptySet(),
        )

    @Test
    fun `should display the title it was given`() =
        runComposeUiTest {
            // Arrange
            setContent {
                MyScaffold(title = "title", libraries = libraries) {}
            }

            // Assert
            onNodeWithText("title").assertIsDisplayed()
        }

    @Test
    fun `should display the content it was given`() =
        runComposeUiTest {
            // Arrange
            setContent {
                MyScaffold(title = "title", libraries = libraries) { Text("main screen") }
            }

            // Assert
            onNodeWithText("main screen").assertIsDisplayed()
        }

    @Test
    fun `should not display the navigate back button on the main screen`() =
        runComposeUiTest {
            // Arrange
            setContent {
                MyScaffold(title = "title", libraries = libraries) {}
            }

            // Assert
            onNodeWithTag(SimpleTopAppBarTestTags.NAVIGATE_BACK_BUTTON).assertDoesNotExist()
        }

    @Test
    fun `should navigate to the open source licenses screen when the open source licenses button is clicked`() =
        runComposeUiTest {
            // Arrange
            setContent {
                MyScaffold(title = "title", libraries = libraries) { Text("main screen") }
            }

            // Act
            onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).performClick()
            onNodeWithTag(SimpleTopAppBarTestTags.OPEN_SOURCE_LICENSES_BUTTON).performClick()

            // Assert
            onNodeWithTag(MyScaffoldTestTags.OPEN_SOURCE_LICENSES_SCREEN).assertIsDisplayed()
            onNodeWithText("main screen").assertDoesNotExist()
        }

    @Test
    fun `should display the libraries it was given on the open source licenses screen`() =
        runComposeUiTest {
            // Arrange
            setContent {
                MyScaffold(title = "title", libraries = libraries) {}
            }

            // Act
            onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).performClick()
            onNodeWithTag(SimpleTopAppBarTestTags.OPEN_SOURCE_LICENSES_BUTTON).performClick()

            // Assert
            onNodeWithText("MyComposables").assertIsDisplayed()
        }

    @Test
    fun `should navigate back to the main screen when the navigate back button is clicked`() =
        runComposeUiTest {
            // Arrange
            setContent {
                MyScaffold(title = "title", libraries = libraries) { Text("main screen") }
            }
            onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).performClick()
            onNodeWithTag(SimpleTopAppBarTestTags.OPEN_SOURCE_LICENSES_BUTTON).performClick()

            // Act
            onNodeWithTag(SimpleTopAppBarTestTags.NAVIGATE_BACK_BUTTON).performClick()

            // Assert
            onNodeWithText("main screen").assertIsDisplayed()
            onNodeWithTag(MyScaffoldTestTags.OPEN_SOURCE_LICENSES_SCREEN).assertDoesNotExist()
        }

    @Test
    fun `should stay on the open source licenses screen when the open source licenses button is clicked again`() =
        runComposeUiTest {
            // Arrange
            setContent {
                MyScaffold(title = "title", libraries = libraries) { Text("main screen") }
            }
            onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).performClick()
            onNodeWithTag(SimpleTopAppBarTestTags.OPEN_SOURCE_LICENSES_BUTTON).performClick()

            // Act
            onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).performClick()
            onNodeWithTag(SimpleTopAppBarTestTags.OPEN_SOURCE_LICENSES_BUTTON).performClick()
            onNodeWithTag(SimpleTopAppBarTestTags.NAVIGATE_BACK_BUTTON).performClick()

            // Assert
            onNodeWithText("main screen").assertIsDisplayed()
        }

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
    fun `should be able to find the open source licenses screen by its test tag`() =
        runComposeUiTest {
            // Arrange
            setContent {
                MyScaffold(title = "title", libraries = libraries) {}
            }

            // Act
            onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).performClick()
            onNodeWithTag(SimpleTopAppBarTestTags.OPEN_SOURCE_LICENSES_BUTTON).performClick()

            // Assert
            onNodeWithTag(MyScaffoldTestTags.OPEN_SOURCE_LICENSES_SCREEN).assertExists()
        }
}
