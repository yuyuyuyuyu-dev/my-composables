package dev.yuyuyuyuyu.mycomposables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import dev.yuyuyuyuyu.mycomposables.generated.resources.Res
import dev.yuyuyuyuyu.mycomposables.generated.resources.more_options
import dev.yuyuyuyuyu.mycomposables.generated.resources.navigate_back
import dev.yuyuyuyuyu.mycomposables.generated.resources.open_source_licenses
import dev.yuyuyuyuyu.mycomposables.generated.resources.source_code
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(
    title: String,
    navigateBackIsPossible: Boolean,
    onNavigateBackButtonClick: () -> Unit,
    onOpenSourceLicensesButtonClick: () -> Unit,
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
) = TopAppBar(
    title = { Text(title) },
    modifier = modifier,
    navigationIcon = {
        if (navigateBackIsPossible) {
            IconButton(
                content = { Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(Res.string.navigate_back)) },
                modifier = Modifier.testTag(SimpleTopAppBarTestTags.NAVIGATE_BACK_BUTTON),
                onClick = onNavigateBackButtonClick,
            )
        }
    },
    actions = {
        var menuIsExpanded by rememberSaveable { mutableStateOf(false) }

        IconButton(
            content = { Icon(Icons.Default.MoreVert, stringResource(Res.string.more_options)) },
            modifier = Modifier.testTag(SimpleTopAppBarTestTags.MENU_BUTTON),
            onClick = { menuIsExpanded = true },
        )

        DropdownMenu(
            expanded = menuIsExpanded,
            onDismissRequest = { menuIsExpanded = false },
            modifier = Modifier.testTag(SimpleTopAppBarTestTags.MENU),
        ) {
            DropdownMenuItem(
                text = openSourceLicensesButtonLabel,
                onClick = {
                    onOpenSourceLicensesButtonClick()
                    menuIsExpanded = false
                },
                modifier = Modifier.testTag(SimpleTopAppBarTestTags.OPEN_SOURCE_LICENSES_BUTTON),
            )
            if (onSourceCodeButtonClick != null) {
                DropdownMenuItem(
                    text = sourceCodeButtonLabel,
                    onClick = {
                        onSourceCodeButtonClick()
                        menuIsExpanded = false
                    },
                    modifier = Modifier.testTag(SimpleTopAppBarTestTags.SOURCE_CODE_BUTTON),
                )
            }
        }
    },
)

// The navigation and overflow buttons are fixed by this composable, so their
// descriptions are facts about it rather than a caller's choice, and are
// localised here instead of being taken as arguments. That leaves a caller's
// own tests with no locale independent handle on the nodes, which is what
// these tags are for. The menu items are tagged as well, since a caller that
// leaves the labels at their defaults is in the same position.
//
// The values are the contract, not the constant names: renaming a constant
// fails to compile for a caller, but changing a value breaks their tests at run
// time instead.
object SimpleTopAppBarTestTags {
    const val NAVIGATE_BACK_BUTTON = "SimpleTopAppBar.NavigateBackButton"
    const val MENU_BUTTON = "SimpleTopAppBar.MenuButton"
    const val MENU = "SimpleTopAppBar.Menu"
    const val OPEN_SOURCE_LICENSES_BUTTON = "SimpleTopAppBar.OpenSourceLicensesButton"
    const val SOURCE_CODE_BUTTON = "SimpleTopAppBar.SourceCodeButton"
}
