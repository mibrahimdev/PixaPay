package io.github.mohamedisoliman.pixapay

import java.io.InputStreamReader


object Util {

    fun jsonAsString(path: String): String {
        val resourceAsStream = javaClass.classLoader?.getResourceAsStream(path)
        val reader = InputStreamReader(resourceAsStream)
        return reader.use { it.readText() }
    }
}