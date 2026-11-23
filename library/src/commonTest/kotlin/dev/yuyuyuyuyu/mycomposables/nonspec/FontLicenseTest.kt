package dev.yuyuyuyuyu.mycomposables.nonspec

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import dev.yuyuyuyuyu.mycomposables.generated.resources.Res
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class FontLicenseTest {
    @Test
    fun `should ship the Yomogi copyright notice and the SIL Open Font License with the font`() =
        runComposeUiTest {
            // Act
            val license = Res.readBytes("files/licenses/Yomogi/OFL.txt").decodeToString()

            // Assert
            assertTrue(license.contains("Copyright 2020 The Yomogi Project Authors"))
            assertTrue(license.contains("SIL OPEN FONT LICENSE Version 1.1"))
        }
}
