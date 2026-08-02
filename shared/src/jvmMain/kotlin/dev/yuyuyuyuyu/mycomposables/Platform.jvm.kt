// Named for the expect declaration and the target, which is the multiplatform
// convention, rather than for the class it holds.
@file:Suppress("MatchingDeclarationName")

package dev.yuyuyuyuyu.mycomposables

class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()
