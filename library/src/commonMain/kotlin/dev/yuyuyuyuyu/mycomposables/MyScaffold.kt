package dev.yuyuyuyuyu.mycomposables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import dev.yuyuyuyuyu.mycomposables.generated.resources.Res
import dev.yuyuyuyuyu.mycomposables.generated.resources.open_source_licenses
import dev.yuyuyuyuyu.mycomposables.generated.resources.source_code
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.jetbrains.compose.resources.stringResource

/**
 * A [Scaffold] with [SimpleTopAppBar] as its app bar and an open source licenses
 * screen the app bar's menu navigates to.
 *
 * No theme is applied. Wrap the call site in [MyMaterialTheme], or any other
 * theme, the same way a bare [Scaffold] would be wrapped.
 *
 * @param libraries the dependency data to list on the licenses screen, as
 *   produced by the AboutLibraries Gradle plugin. `null` renders an empty list,
 *   which is what `produceLibraries` holds while it is still reading the data.
 * @param content the main screen, receiving the padding of the app bar above it.
 */
@Composable
fun MyScaffold(
    title: String,
    libraries: Libs?,
    modifier: Modifier = Modifier,
    openSourceLicensesButtonLabel: @Composable () -> Unit = { Text(stringResource(Res.string.open_source_licenses)) },
    sourceCodeButtonLabel: @Composable () -> Unit = {
        Row {
            Icon(Icons.AutoMirrored.Default.OpenInNew, null)
            Spacer(Modifier.width(1.dp))
            Text(stringResource(Res.string.source_code))
        }
    },
    onSourceCodeButtonClick: (() -> Unit)? = null,
    content: @Composable (innerPadding: PaddingValues) -> Unit,
) {
    val backStack = rememberNavBackStack(routeSavedStateConfiguration, MyScaffoldRoute.Main)
    val openSourceLicensesTitle = stringResource(Res.string.open_source_licenses)

    Scaffold(
        modifier = modifier,
        topBar = {
            SimpleTopAppBar(
                title =
                    when (backStack.lastOrNull()) {
                        MyScaffoldRoute.OpenSourceLicenses -> openSourceLicensesTitle
                        else -> title
                    },
                navigateBackIsPossible = backStack.size > 1,
                onNavigateBackButtonClick = { backStack.removeLastOrNull() },
                onOpenSourceLicensesButtonClick = {
                    // The app bar is shared by both screens, so its menu is
                    // reachable from the licenses screen as well.
                    if (backStack.lastOrNull() != MyScaffoldRoute.OpenSourceLicenses) {
                        backStack.add(MyScaffoldRoute.OpenSourceLicenses)
                    }
                },
                openSourceLicensesButtonLabel = openSourceLicensesButtonLabel,
                sourceCodeButtonLabel = sourceCodeButtonLabel,
                onSourceCodeButtonClick = onSourceCodeButtonClick,
            )
        },
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider =
                entryProvider {
                    entry<MyScaffoldRoute.Main> { content(innerPadding) }
                    entry<MyScaffoldRoute.OpenSourceLicenses> {
                        LibrariesContainer(
                            libraries = libraries,
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .testTag(MyScaffoldTestTags.OPEN_SOURCE_LICENSES_SCREEN),
                            contentPadding = innerPadding,
                        )
                    }
                },
        )
    }
}

// The screens are fixed by this composable, so a caller never names one. They
// are private for that reason, which in turn means this composable is the only
// place that can register them for the polymorphic serialization
// `rememberNavBackStack` needs outside Android.
@Serializable
private sealed interface MyScaffoldRoute : NavKey {
    @Serializable
    data object Main : MyScaffoldRoute

    @Serializable
    data object OpenSourceLicenses : MyScaffoldRoute
}

private val routeSavedStateConfiguration =
    SavedStateConfiguration {
        serializersModule =
            SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(MyScaffoldRoute.Main::class)
                    subclass(MyScaffoldRoute.OpenSourceLicenses::class)
                }
            }
    }

// The licenses screen is furnished by this composable rather than by a caller,
// so a caller's own tests have no handle on it. See SimpleTopAppBarTestTags for
// why the values rather than the constant names are the contract.
object MyScaffoldTestTags {
    const val OPEN_SOURCE_LICENSES_SCREEN = "MyScaffold.OpenSourceLicensesScreen"
}
