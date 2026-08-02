// Named for the expect declaration and the target, which is the multiplatform
// convention, rather than for the class it holds.
@file:Suppress("MatchingDeclarationName")

package dev.yuyuyuyuyu.mycomposables

class WasmPlatform : Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()
