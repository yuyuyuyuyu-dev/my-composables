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
import dev.yuyuyuyuyu.mycomposables.generated.resources.Res
import dev.yuyuyuyuyu.mycomposables.generated.resources.open_source_licenses
import org.jetbrains.compose.resources.getString
import kotlin.test.Test

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
    fun `should navigate to the open source licenses screen`() =
        runComposeUiTest {
            // Arrange
            setContent {
                MyScaffold(title = "title", libraries = libraries) {}
            }

            // Act
            onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).performClick()
            onNodeWithTag(SimpleTopAppBarTestTags.OPEN_SOURCE_LICENSES_BUTTON).performClick()

            // Assert
            onNodeWithTag(MyScaffoldTestTags.OPEN_SOURCE_LICENSES_SCREEN).assertIsDisplayed()
        }

    @Test
    fun `should do nothing when the open source licenses button is clicked on the open source licenses screen`() =
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

            // Assert
            // A single step back reaching the main screen is what shows the
            // click left the back stack alone.
            onNodeWithTag(MyScaffoldTestTags.OPEN_SOURCE_LICENSES_SCREEN).assertIsDisplayed()
            onNodeWithTag(SimpleTopAppBarTestTags.NAVIGATE_BACK_BUTTON).performClick()
            onNodeWithText("main screen").assertIsDisplayed()
        }

    @Test
    fun `should navigate back to the main screen from the open source licenses screen`() =
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
    fun `should display the content it was given on the main screen`() =
        runComposeUiTest {
            // Arrange
            setContent {
                MyScaffold(title = "title", libraries = libraries) { Text("main screen") }
            }

            // Assert
            onNodeWithText("main screen").assertIsDisplayed()
        }

    @Test
    fun `should display the title it was given on the main screen`() =
        runComposeUiTest {
            // Arrange
            setContent {
                MyScaffold(title = "title", libraries = libraries) {}
            }

            // Assert
            onNodeWithText("title").assertIsDisplayed()
        }

    @Test
    fun `should display the localised open source licenses title on the open source licenses screen`() =
        runComposeUiTest {
            // Arrange
            // Compared against the resource rather than against a literal, so
            // that the assertion holds whichever locale the tests run under.
            val openSourceLicensesTitle = getString(Res.string.open_source_licenses)
            setContent {
                MyScaffold(title = "title", libraries = libraries) {}
            }

            // Act
            onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).performClick()
            onNodeWithTag(SimpleTopAppBarTestTags.OPEN_SOURCE_LICENSES_BUTTON).performClick()

            // Assert
            onNodeWithText(openSourceLicensesTitle).assertIsDisplayed()
        }
}
