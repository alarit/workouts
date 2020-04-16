package com.github.alarit.workouts

import com.github.alarit.workouts.model.Workout
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import java.time.LocalDate


@Profile("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests(@Autowired val restTemplate: TestRestTemplate) {

    @Test
    @Order(1)
    fun `Create a workout`() {
        val workout = Workout(55L, 1, LocalDate.now(), 100)
        val headers = HttpHeaders()
        headers["X-COM-PERSIST"] = "true"
        val request: HttpEntity<Workout> = HttpEntity<Workout>(workout, headers)
        val response = restTemplate.postForEntity("/api/workouts/", request, String::class.java)
        assertEquals(response.statusCode, HttpStatus.CREATED)
    }

    @Test
    @Order(2)
    fun `Update a workout`() {
        val workout = Workout(55L, 1, LocalDate.now(), 50)
        val headers = HttpHeaders()
        headers["X-COM-PERSIST"] = "true"
        val request: HttpEntity<Workout> = HttpEntity<Workout>(workout, headers)
        val response = restTemplate.exchange("/api/workouts/1", HttpMethod.PUT, request, String::class.java)
        assertEquals(response.statusCode, HttpStatus.NO_CONTENT)
    }

    @Test
    @Order(3)
    fun `Find all workouts`() {
        val response = restTemplate.getForEntity<String>("/api/workouts/")
        assertEquals(response.statusCode, HttpStatus.OK)
        assertThat(response.body).contains("\"reps\":100")
    }

    @Test
    @Order(4)
    fun `Find all workouts by type`() {
        val response = restTemplate.getForEntity<String>("/api/workouts/types/1")
        assertEquals(response.statusCode, HttpStatus.OK)
        assertThat(response.body).contains("\"reps\":100")
    }

}