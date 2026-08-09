package dev.yuyuyuyuyu.mycomposables

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            AndroidApp()
        }
    }
}

// MyMaterialDynamicTheme is published for Android only, so this is the one
// place the sample can run under it.
@Composable
private fun AndroidApp() {
    var dynamicColorIsUsed by rememberSaveable { mutableStateOf(false) }

    App(
        theme = { content ->
            if (dynamicColorIsUsed) {
                MyMaterialDynamicTheme(content)
            } else {
                MyMaterialTheme(content)
            }
        },
        platformToggles = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("MyMaterialDynamicTheme")
                Switch(checked = dynamicColorIsUsed, onCheckedChange = { dynamicColorIsUsed = it })
            }
        },
    )
}

@Preview
@Composable
private fun AppAndroidPreview() {
    AndroidApp()
}
