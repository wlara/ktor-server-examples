package io.github.wlara.ktor.server.examples.helloworld

import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun `GET hello returns 200`() = testApplication {
        application { module() }
        val response = client.get("api/v1/hello")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `GET hello contains Hello World! text`() = testApplication {
        application { module() }
        val response = client.get("api/v1/hello")
        assertContains(response.bodyAsText(),"Hello, World!")
    }
}
