package com.github.alarit.workouts.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.github.alarit.workouts.model.Workout
import com.github.alarit.workouts.service.WorkoutService
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@RunWith(SpringRunner::class)
@WebMvcTest(WorkoutController::class)
internal class WorkoutControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var service: WorkoutService

    private val apiUrl = "/api/workouts"
    private val workoutMocks = listOf(Workout(55L), Workout(155L))

    /*@Test
    fun save() {
    }*/

    @Test
    fun update() {
        val workout = workoutMocks[0]
        Mockito.`when`(service.update(55, workout)).thenReturn(Optional.empty())

        println(asJsonString(workout))

        mvc.perform(
                MockMvcRequestBuilders.put("$apiUrl/{id}", workout.id)
                        .content(asJsonString(workout))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)

        Mockito.`when`(service.update(workoutMocks[0].id, workoutMocks[0])).thenReturn(Optional.of(workoutMocks[0]))

        mvc.perform(
                MockMvcRequestBuilders.put("$apiUrl/{id}", workout.id)
                        .content(asJsonString(workout))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent)
    }

    @Test
    fun findAll() {
        Mockito.`when`(service.findAll()).thenReturn(workoutMocks)

        val result = mvc.get("$apiUrl/")
                .andReturn()

        assertEquals(result.response.status, HttpStatus.OK.value())

    }

    /*@Test
    fun findByName() {
    }

    @Test
    fun delete() {
    }*/

    private fun asJsonString(obj: Any): String {
        return try {
            val objectMapper = ObjectMapper()
            val javaTimeModule = JavaTimeModule()
            val dateFormat = "yyyy-MM-dd"
            javaTimeModule.addDeserializer(LocalDate::class.java, LocalDateDeserializer(DateTimeFormatter.ofPattern(dateFormat)))
            javaTimeModule.addSerializer(LocalDate::class.java, LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)))
            objectMapper.registerModule(javaTimeModule)
            objectMapper.writeValueAsString(obj)

        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}