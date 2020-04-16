package com.github.alarit.workouts.service

import com.github.alarit.workouts.model.Workout
import com.github.alarit.workouts.repository.WorkoutRepository
import junit.framework.TestCase.*
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import java.util.*

internal class WorkoutServiceTest{


    private val repo = Mockito.mock(WorkoutRepository::class.java)
    private val service = WorkoutService(repo)
    private val workoutMock = Workout(55L)

    @Test
    fun save() {
        Mockito.`when`(repo.save(any(Workout::class.java))).thenReturn(workoutMock)
        val result = service.save(workoutMock)
        assertEquals(result.id, workoutMock.id)
    }

    @Test
    fun update() {
        Mockito.`when`(repo.findById(any(Long::class.java)))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(workoutMock))

        var result = service.update(workoutMock.id, workoutMock)
        assertFalse(result.isPresent)

        Mockito.`when`(repo.save(any(Workout::class.java))).thenReturn(workoutMock)
        result = service.update(workoutMock.id, workoutMock)
        assertTrue(result.isPresent)
        assertEquals(result.get().id, workoutMock.id)
    }

    @Test
    fun findAll() {
        val workoutMocks = listOf(Workout(55L),Workout(155L))
        Mockito.`when`(repo.findAll()).thenReturn(workoutMocks)

        val result = service.findAll()
        assertEquals(result, workoutMocks)
    }

    @Test
    fun findByType() {
        val workoutMocks = listOf(Workout(55L),Workout(155L))
        val workoutType = 1L

        Mockito.`when`(repo.findWorkoutsByTypeOrderByDateDesc(workoutType)).thenReturn(workoutMocks)
        val result = service.findByType(workoutType)
        assertEquals(result, workoutMocks)
    }

    @Test
    fun delete() {
        Mockito.`when`(repo.findById(any(Long::class.java)))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(workoutMock))

        var result = service.delete(workoutMock.id)
        assertFalse(result)

        result = service.delete(workoutMock.id)
        assertTrue(result)
    }

}
