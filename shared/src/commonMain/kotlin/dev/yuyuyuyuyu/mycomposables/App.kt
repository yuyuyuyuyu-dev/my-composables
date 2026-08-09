package dev.yuyuyuyuyu.mycomposables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// The theme is a parameter so that androidApp can also run the sample under
// MyMaterialDynamicTheme, which the library publishes for Android only.
@Composable
fun App(
    modifier: Modifier = Modifier,
    theme: @Composable (content: @Composable () -> Unit) -> Unit = { MyMaterialTheme(it) },
    platformToggles: @Composable () -> Unit = {},
) {
    var navigateBackIsPossible by rememberSaveable { mutableStateOf(true) }
    var sourceCodeEntryIsShown by rememberSaveable { mutableStateOf(true) }
    var lastClickedButton by rememberSaveable { mutableStateOf("none") }

    theme {
        Scaffold(
            modifier = modifier,
            topBar = {
                // Both labels are left at their defaults here. The bar in the
                // content passes its own.
                SimpleTopAppBar(
                    title = "MyComposables",
                    navigateBackIsPossible = navigateBackIsPossible,
                    onNavigateBackButtonClick = { lastClickedButton = "navigate back" },
                    onOpenSourceLicensesButtonClick = { lastClickedButton = "open source licenses" },
                    onSourceCodeButtonClick =
                        if (sourceCodeEntryIsShown) {
                            { lastClickedButton = "source code" }
                        } else {
                            null
                        },
                )
            },
        ) { innerPadding ->
            SampleContent(
                lastClickedButton = lastClickedButton,
                navigateBackIsPossible = navigateBackIsPossible,
                onNavigateBackIsPossibleChange = { navigateBackIsPossible = it },
                sourceCodeEntryIsShown = sourceCodeEntryIsShown,
                onSourceCodeEntryIsShownChange = { sourceCodeEntryIsShown = it },
                onButtonClick = { lastClickedButton = it },
                modifier = Modifier.padding(innerPadding),
                platformToggles = platformToggles,
            )
        }
    }
}

@Composable
private fun SampleContent(
    lastClickedButton: String,
    navigateBackIsPossible: Boolean,
    onNavigateBackIsPossibleChange: (Boolean) -> Unit,
    sourceCodeEntryIsShown: Boolean,
    onSourceCodeEntryIsShownChange: (Boolean) -> Unit,
    onButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    platformToggles: @Composable () -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("Running on ${getPlatform().name}")
        Text("Last clicked: $lastClickedButton", style = MaterialTheme.typography.bodyLarge)

        Toggle(
            label = "navigateBackIsPossible",
            checked = navigateBackIsPossible,
            onCheckedChange = onNavigateBackIsPossibleChange,
        )
        Toggle(
            label = "onSourceCodeButtonClick is set",
            checked = sourceCodeEntryIsShown,
            onCheckedChange = onSourceCodeEntryIsShownChange,
        )
        platformToggles()

        HorizontalDivider()

        Text("With labels and a modifier of its own", style = MaterialTheme.typography.titleMedium)
        SimpleTopAppBar(
            title = "Custom labels",
            navigateBackIsPossible = navigateBackIsPossible,
            onNavigateBackButtonClick = { onButtonClick("navigate back (custom)") },
            onOpenSourceLicensesButtonClick = { onButtonClick("open source licenses (custom)") },
            modifier = Modifier.fillMaxWidth(),
            openSourceLicensesButtonLabel = { Text("Licenses") },
            sourceCodeButtonLabel = { Text("Source") },
            onSourceCodeButtonClick = { onButtonClick("source code (custom)") },
        )
    }
}

@Composable
private fun Toggle(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(label)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
@Preview
private fun AppPreview() {
    App()
}
