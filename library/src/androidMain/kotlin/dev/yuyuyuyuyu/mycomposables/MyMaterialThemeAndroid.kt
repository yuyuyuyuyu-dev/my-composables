package dev.yuyuyuyuyu.mycomposables

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import dev.yuyuyuyuyu.createtypography.createTypographyFrom

@Composable
fun MyMaterialDynamicTheme(content: @Composable () -> Unit) {
    val colorScheme = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isSystemInDarkTheme()) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        isSystemInDarkTheme() -> darkScheme
        else -> lightScheme
    }

    MyMaterialTheme(
        colorScheme = colorScheme,
        typography = createTypographyFrom("Yomogi"),
        content = content,
    )
}
