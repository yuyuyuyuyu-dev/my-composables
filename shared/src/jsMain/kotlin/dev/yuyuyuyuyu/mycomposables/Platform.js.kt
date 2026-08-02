// Named for the expect declaration and the target, which is the multiplatform
// convention, rather than for the class it holds.
@file:Suppress("MatchingDeclarationName")

package dev.yuyuyuyuyu.mycomposables

import web.navigator.navigator

class JsPlatform : Platform {
    private val userAgent = navigator.userAgent
    private val browserList = listOf("Chrome", "Firefox", "Safari", "Edge")

    override val name: String =
        userAgent
            .findAnyOf(browserList, ignoreCase = true)
            ?.let { (startIndex) -> userAgent.substring(startIndex).substringBefore(" ") }
            ?: "Unknown"
}

actual fun getPlatform(): Platform = JsPlatform()
