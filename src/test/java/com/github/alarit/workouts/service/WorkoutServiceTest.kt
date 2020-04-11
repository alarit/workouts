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
        val result = service.save(workoutMock).block()
        assertEquals(result?.id, workoutMock.id)
    }

    @Test
    fun update() {
        Mockito.`when`(repo.findById(any(Long::class.java))).thenReturn(Optional.empty())
        var result = service.update(workoutMock.id, workoutMock).block()
        assertNull(result)

        Mockito.`when`(repo.findById(any(Long::class.java))).thenReturn(Optional.of(workoutMock))
        Mockito.`when`(repo.save(any(Workout::class.java))).thenReturn(workoutMock)
        result = service.save(workoutMock).block()
        assertEquals(result?.id, workoutMock.id)
    }

    @Test
    fun findAll() {
        val workoutMocks = listOf(Workout(55L),Workout(155L))
        Mockito.`when`(repo.findAll()).thenReturn(workoutMocks)

        val result = service.findAll().collectList().block() ?: emptyList()
        assertEquals(result.size, workoutMocks.size)
        for((index, mock) in result.withIndex()) {
            assertEquals(mock, workoutMocks[index])
        }
    }

    @Test
    fun findByType() {
        val workoutMocks = listOf(Workout(55L),Workout(155L))
        val workoutType = 1L

        var result = service.findByType(-1L).collectList().block()
        assertTrue(result.isEmpty())

        Mockito.`when`(repo.findWorkoutsByTypeOrderByDateDesc(workoutType)).thenReturn(workoutMocks)
        result = service.findByType(workoutType).collectList().block() ?: emptyList()
        assertEquals(result.size, workoutMocks.size)
        for((index, mock) in result.withIndex()) {
            assertEquals(mock, workoutMocks[index])
        }
    }

}
