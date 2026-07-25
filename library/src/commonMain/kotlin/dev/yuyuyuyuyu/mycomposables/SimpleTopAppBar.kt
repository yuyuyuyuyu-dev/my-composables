package dev.yuyuyuyuyu.mycomposables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.yuyuyuyuyu.mycomposables.generated.resources.Res
import dev.yuyuyuyuyu.mycomposables.generated.resources.open_source_licenses
import dev.yuyuyuyuyu.mycomposables.generated.resources.source_code
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigateBackIsPossible: Boolean,
    openSourceLicensesButtonLabel: @Composable () -> Unit = { Text(stringResource(Res.string.open_source_licenses)) },
    sourceCodeButtonLabel: @Composable () -> Unit = {
        Row {
            Icon(Icons.AutoMirrored.Default.OpenInNew, null)
            Spacer(Modifier.width(1.dp))
            Text(stringResource(Res.string.source_code))
        }
    },
    onNavigateBackButtonClick: () -> Unit,
    onOpenSourceLicensesButtonClick: () -> Unit,
    onSourceCodeButtonClick: (() -> Unit)? = null,
) = TopAppBar(
    title = { Text(title) },
    modifier = modifier,
    navigationIcon = {
        if (navigateBackIsPossible) {
            IconButton(
                content = { Icon(Icons.AutoMirrored.Filled.ArrowBack, "navigate back") },
                onClick = onNavigateBackButtonClick,
            )
        }
    },
    actions = {
        var menuIsExpanded by rememberSaveable { mutableStateOf(false) }

        IconButton(
            content = { Icon(Icons.Default.MoreVert, "menu") },
            onClick = { menuIsExpanded = true },
        )

        DropdownMenu(
            expanded = menuIsExpanded,
            onDismissRequest = { menuIsExpanded = false },
        ) {
            DropdownMenuItem(
                text = openSourceLicensesButtonLabel,
                onClick = {
                    onOpenSourceLicensesButtonClick()
                    menuIsExpanded = false
                },
            )
            if (onSourceCodeButtonClick != null) {
                DropdownMenuItem(
                    text = sourceCodeButtonLabel,
                    onClick = {
                        onSourceCodeButtonClick()
                        menuIsExpanded = false
                    },
                )
            }
        }
    },
)
