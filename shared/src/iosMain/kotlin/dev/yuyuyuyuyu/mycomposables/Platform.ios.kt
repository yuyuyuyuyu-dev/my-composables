// Named for the expect declaration and the target, which is the multiplatform
// convention, rather than for the class it holds.
@file:Suppress("MatchingDeclarationName")

package dev.yuyuyuyuyu.mycomposables

import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()
