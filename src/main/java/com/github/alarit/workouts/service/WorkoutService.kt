package com.github.alarit.workouts.service

import com.github.alarit.workouts.model.Workout
import com.github.alarit.workouts.repository.WorkoutRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class WorkoutService(private val workoutRepository: WorkoutRepository) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun save(workout: Workout): Workout {
        return workoutRepository.save(workout)
    }

    fun update(id: Long, workout: Workout): Optional<Workout> {
        val w = workoutRepository.findById(id)
        if(!w.isPresent) {
            log.warn("Workout $id does not exist")
            return Optional.empty()
        }

        workout.id = id
        return Optional.of(save(workout))
    }

    fun findAll(): Collection<Workout> {
        return workoutRepository.findAll()
    }

    fun findByType(workoutType: Long): Collection<Workout> {
        return workoutRepository.findWorkoutsByTypeOrderByDateDesc(workoutType)
    }

    fun delete(id: Long): Boolean {
        val w = workoutRepository.findById(id)
        if(!w.isPresent) {
            log.warn("Workout $id does not exist")
            return false
        }

        workoutRepository.deleteById(id)
        return true
    }

}
