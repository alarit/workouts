package com.github.alarit.workouts.repository

import com.github.alarit.workouts.model.Workout
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WorkoutRepository : JpaRepository<Workout, Long> {

    fun findWorkoutsByTypeOrderByDateDesc(name: Long?): List<Workout>

}