package org.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder


@SpringBootApplication // спринг приложение
open class Main

public fun main(args: Array<String>) {
    val builder = SpringApplicationBuilder(Main::class.java)
    builder.headless(false)
    val context = builder.run(*args)
}