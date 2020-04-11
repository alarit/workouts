package com.github.alarit.workouts.controller

import com.github.alarit.workouts.model.Workout
import com.github.alarit.workouts.service.WorkoutService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/workouts")
class WorkoutController(private val workoutService: WorkoutService) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody workout: Workout): Mono<Workout> {
        log.info("saving workout")
        return workoutService.save(workout)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Long, @RequestBody workout: Workout): Mono<Workout> {
        log.info("updating workout {}", workout.id)
        return workoutService.update(id, workout)
    }

    @GetMapping("/")
    fun findAll(): Flux<Workout> {
        log.info("Finding all workouts")
        return workoutService.findAll()
    }

    @GetMapping("/types/{type}")
    fun findByName(@PathVariable type: Long): Flux<Workout> {
        log.info("Finding all {}", type)
        return workoutService.findByType(type)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        log.info("deleting workout {}", id)
        workoutService.delete(id)
    }
}
