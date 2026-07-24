package dev.yuyuyuyuyu.mycomposables

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform