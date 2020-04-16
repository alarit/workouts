package com.github.alarit.workouts.controller

import com.github.alarit.workouts.model.Workout
import com.github.alarit.workouts.service.WorkoutService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/workouts")
class WorkoutController(private val workoutService: WorkoutService) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping("/")
    fun save(@RequestBody workout: Workout): ResponseEntity<Workout> {
        log.info("saving workout")
        val savedWorkout = workoutService.save(workout)
        return ResponseEntity.created(URI.create("/api/workouts/${savedWorkout.id}")).body(savedWorkout)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody workout: Workout): ResponseEntity<Workout> {
        log.info("updating workout {}", workout.id)
        val result = workoutService.update(id, workout)
        if(!result.isPresent) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/")
    fun findAll(): ResponseEntity<Iterable<Workout>> {
        log.info("Finding all workouts")
        return ResponseEntity.ok().body(workoutService.findAll())
    }

    @GetMapping("/types/{type}")
    fun findByType(@PathVariable type: Long): ResponseEntity<Iterable<Workout>> {
        log.info("Finding all {}", type)
        return ResponseEntity.ok().body(workoutService.findByType(type))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Workout> {
        log.info("deleting workout {}", id)
        if(workoutService.delete(id)) {
            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.notFound().build()
    }
}
