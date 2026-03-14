package io.github.wlara.ktor.server.examples.basic.features.users

import io.github.wlara.ktor.server.examples.basic.module
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class UserRoutesTest {

    @Test
    fun `GET users returns 200 and seeded users`() = testApplication {
        application { module() }

        val response = client.get("/api/v1/users")
        val body = response.bodyAsText()

        assertEquals(HttpStatusCode.OK, response.status)
        assertContains(body, "Alice Johnson")
        assertContains(body, "Bob Smith")
        assertContains(body, "Charlie Day")
        assertContains(body, "Dana Scully")
    }

    @Test
    fun `GET user by id returns matching user only`() = testApplication {
        application { module() }

        val response = client.get("/api/v1/users/550e8400-e29b-41d4-a716-446655440000")
        val body = response.bodyAsText()

        assertEquals(HttpStatusCode.OK, response.status)
        assertContains(body, "Alice Johnson")
        assertFalse(body.contains("Bob Smith"))
        assertFalse(body.contains("Charlie Day"))
        assertFalse(body.contains("Dana Scully"))
    }

    @Test
    fun `GET unknown user id returns 404`() = testApplication {
        application { module() }

        val unknownId = "00000000-0000-0000-0000-000000000000"
        val response = client.get("/api/v1/users/$unknownId")
        val body = response.bodyAsText()

        assertEquals(HttpStatusCode.NotFound, response.status)
        assertContains(body, "User with id $unknownId not found")
        assertFalse(body.contains("Alice Johnson"))
        assertFalse(body.contains("Bob Smith"))
        assertFalse(body.contains("Charlie Day"))
        assertFalse(body.contains("Dana Scully"))
    }
}
