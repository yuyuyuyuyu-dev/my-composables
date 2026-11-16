package dev.yuyuyuyuyu.mycomposables.specs

import androidx.compose.material3.Text
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.entity.Library
import dev.yuyuyuyuyu.mycomposables.MyScaffold
import dev.yuyuyuyuyu.mycomposables.MyScaffoldTestTags
import dev.yuyuyuyuyu.mycomposables.SimpleTopAppBarTestTags
import dev.yuyuyuyuyu.mycomposables.generated.resources.Res
import dev.yuyuyuyuyu.mycomposables.generated.resources.open_source_licenses
import org.jetbrains.compose.resources.getString
import kotlin.test.Test
import kotlin.time.TimeSource

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
            val testStart = TimeSource.Monotonic.markNow()
            // Arrange
            step("T1", "00 setContent") {
                setContent {
                    MyScaffold(title = "title", libraries = libraries) {}
                }
            }

            // Act
            step("T1", "01 performClick MENU_BUTTON") {
                onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).performClick()
            }
            step("T1", "02 performClick OPEN_SOURCE_LICENSES_BUTTON") {
                onNodeWithTag(SimpleTopAppBarTestTags.OPEN_SOURCE_LICENSES_BUTTON).performClick()
            }

            // Assert
            step("T1", "03 assertIsDisplayed OPEN_SOURCE_LICENSES_SCREEN") {
                onNodeWithTag(MyScaffoldTestTags.OPEN_SOURCE_LICENSES_SCREEN).assertIsDisplayed()
            }
            println("STEP|T1|total-in-block|${testStart.elapsedNow().inWholeMilliseconds}ms")
        }

    @Test
    fun `should do nothing when the open source licenses button is clicked on the open source licenses screen`() =
        runComposeUiTest {
            val testStart = TimeSource.Monotonic.markNow()
            // Arrange
            step("T2", "00 setContent") {
                setContent {
                    MyScaffold(title = "title", libraries = libraries) { Text("main screen") }
                }
            }
            step("T2", "01 performClick MENU_BUTTON") {
                onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).performClick()
            }
            step("T2", "02 performClick OPEN_SOURCE_LICENSES_BUTTON") {
                onNodeWithTag(SimpleTopAppBarTestTags.OPEN_SOURCE_LICENSES_BUTTON).performClick()
            }

            // Act
            step("T2", "03 performClick MENU_BUTTON") {
                onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).performClick()
            }
            step("T2", "04 performClick OPEN_SOURCE_LICENSES_BUTTON") {
                onNodeWithTag(SimpleTopAppBarTestTags.OPEN_SOURCE_LICENSES_BUTTON).performClick()
            }

            // Assert
            // A single step back reaching the main screen is what shows the
            // click left the back stack alone.
            step("T2", "05 assertIsDisplayed OPEN_SOURCE_LICENSES_SCREEN") {
                onNodeWithTag(MyScaffoldTestTags.OPEN_SOURCE_LICENSES_SCREEN).assertIsDisplayed()
            }
            step(
                "T2",
                "06 performClick NAVIGATE_BACK_BUTTON",
            ) { onNodeWithTag(SimpleTopAppBarTestTags.NAVIGATE_BACK_BUTTON).performClick() }
            step("T2", "07 assertIsDisplayed main screen") {
                onNodeWithText("main screen").assertIsDisplayed()
            }
            println("STEP|T2|total-in-block|${testStart.elapsedNow().inWholeMilliseconds}ms")
        }

    @Test
    fun `should navigate back to the main screen from the open source licenses screen`() =
        runComposeUiTest {
            val testStart = TimeSource.Monotonic.markNow()
            // Arrange
            step("T3", "00 setContent") {
                setContent {
                    MyScaffold(title = "title", libraries = libraries) { Text("main screen") }
                }
            }
            step("T3", "01 performClick MENU_BUTTON") {
                onNodeWithTag(SimpleTopAppBarTestTags.MENU_BUTTON).performClick()
            }
            step("T3", "02 performClick OPEN_SOURCE_LICENSES_BUTTON") {
                onNodeWithTag(SimpleTopAppBarTestTags.OPEN_SOURCE_LICENSES_BUTTON).performClick()
            }

            // Act
            step(
                "T3",
                "03 performClick NAVIGATE_BACK_BUTTON",
            ) { onNodeWithTag(SimpleTopAppBarTestTags.NAVIGATE_BACK_BUTTON).performClick() }

            // Assert
            step("T3", "04 assertIsDisplayed main screen") {
                onNodeWithText("main screen").assertIsDisplayed()
            }
            println("STEP|T3|total-in-block|${testStart.elapsedNow().inWholeMilliseconds}ms")
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
