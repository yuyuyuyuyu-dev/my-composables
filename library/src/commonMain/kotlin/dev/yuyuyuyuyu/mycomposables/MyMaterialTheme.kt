package dev.yuyuyuyuyu.mycomposables

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import dev.yuyuyuyuyu.createtypography.createTypographyFrom
import dev.yuyuyuyuyu.mycomposables.generated.resources.Res
import dev.yuyuyuyuyu.mycomposables.generated.resources.Yomogi_Regular
import org.jetbrains.compose.resources.Font

@Suppress("unused")
@Composable
fun MyMaterialTheme(content: @Composable () -> Unit) =
    MyMaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkScheme else lightScheme,
        typography = createTypographyFrom(FontFamily(Font(Res.font.Yomogi_Regular))),
        content = content,
    )

@Composable
internal fun MyMaterialTheme(
    colorScheme: ColorScheme,
    typography: Typography,
    content: @Composable (() -> Unit),
) = MaterialTheme(
    colorScheme = colorScheme,
    typography = typography,
    content = content,
)
