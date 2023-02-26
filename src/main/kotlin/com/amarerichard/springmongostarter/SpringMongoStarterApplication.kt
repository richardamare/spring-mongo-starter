package com.amarerichard.springmongostarter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringMongoStarterApplication

fun main(args: Array<String>) {
    runApplication<SpringMongoStarterApplication>(*args)
}
