package com.sopa.movieskmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform