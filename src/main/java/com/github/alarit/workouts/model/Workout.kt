package com.github.alarit.workouts.model

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "workout")
data class Workout(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,
        var type: Long = 1,
        var date: LocalDate = LocalDate.now(),
        var reps: Int = 0
)