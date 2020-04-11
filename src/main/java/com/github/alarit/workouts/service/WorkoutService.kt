package com.github.alarit.workouts.service

import com.github.alarit.workouts.model.Workout
import com.github.alarit.workouts.repository.WorkoutRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class WorkoutService(private val workoutRepository: WorkoutRepository) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun save(workout: Workout): Mono<Workout> {
        return Mono.just(workoutRepository.save(workout))
    }

    fun update(id: Long, workout: Workout): Mono<Workout> {
        val w = workoutRepository.findById(id)
        if(!w.isPresent) {
            log.warn("Workout $id does not exist")
            return Mono.empty()
        }

        return save(workout)
    }

    fun findAll(): Flux<Workout> {
        return Mono.just(workoutRepository.findAll()).flatMapMany{ Flux.fromIterable(it) }
    }

    fun findByType(workoutType: Long): Flux<Workout> {
        if (workoutMapper!!.containsKey(workoutType)) {
            return Mono.just(workoutRepository.findWorkoutsByTypeOrderByDateDesc(workoutType)).flatMapMany{ Flux.fromIterable(it) }
        }
        log.warn("workout $workoutType does not exist!")
        return Flux.empty()
    }

    fun delete(id: Long) {
        workoutRepository.deleteById(id)
    }

    companion object {
        private var workoutMapper: MutableMap<Long, String>? = null

        init {
            workoutMapper = HashMap()
            workoutMapper!![1L] = "push-ups"
        }
    }

}
