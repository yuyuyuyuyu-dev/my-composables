package dev.yuyuyuyuyu.mycomposables

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import dev.yuyuyuyuyu.createtypography.createTypographyFrom
import dev.yuyuyuyuyu.mycomposables.fonts.GoogleFonts

@Suppress("unused")
@Composable
fun MyMaterialTheme(content: @Composable () -> Unit) =
    MyMaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkScheme else lightScheme,
        typography = createTypographyFrom(GoogleFonts.Yomogi),
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
