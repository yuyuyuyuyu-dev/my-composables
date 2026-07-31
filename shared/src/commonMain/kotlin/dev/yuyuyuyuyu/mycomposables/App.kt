package dev.yuyuyuyuyu.mycomposables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

// Sample app that showcases the composables published by the :library module.
@Composable
fun App(modifier: Modifier = Modifier) {
    MyMaterialTheme {
        Scaffold(
            modifier = modifier,
            topBar = {
                SimpleTopAppBar(
                    title = "MyComposables",
                    navigateBackIsPossible = false,
                    onNavigateBackButtonClick = {},
                    onOpenSourceLicensesButtonClick = {},
                    onSourceCodeButtonClick = {},
                )
            },
        ) { innerPadding ->
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text("Running on ${getPlatform().name}")
            }
        }
    }
}

@Composable
@Preview
private fun AppPreview() {
    App()
}
