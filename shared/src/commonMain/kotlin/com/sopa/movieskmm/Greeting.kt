package com.sopa.movieskmm

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.*


class Greeting {
    private val client = HttpClient()

    suspend fun greeting(): String {
        val response = client.get("https://ktor.io/docs/")
        return response.bodyAsText()
    }
}