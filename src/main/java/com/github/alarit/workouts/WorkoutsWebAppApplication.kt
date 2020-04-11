package com.github.alarit.workouts

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class WorkoutsWebAppApplication

fun main(args: Array<String>) {
    runApplication<WorkoutsWebAppApplication>(*args)
}
