package io.github.wlara.ktor.server.examples.basic.features.products

import io.github.wlara.ktor.server.examples.basic.test.assertNotContains
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ProductRoutesTest {

    @Test
    fun `GET products without query returns all products`() = testApplication {
        configureApp()

        val response = client.get("/api/v1/products")
        val body = response.bodyAsText()

        assertEquals(HttpStatusCode.OK, response.status)
        assertContains(body, "Mechanical Keyboard")
        assertContains(body, "Wireless Mouse")
        assertContains(body, "Ultra-Wide Monitor")
        assertContains(body, "USB-C Hub")
    }


    @Test
    fun `GET products with query returns filtered data`() = testApplication {
        configureApp()

        val response = client.get("/api/v1/products?query=wireless")
        val body = response.bodyAsText()

        assertEquals(HttpStatusCode.OK, response.status)
        assertContains(body, "Wireless Mouse")
        assertNotContains(body, "Mechanical Keyboard")
        assertNotContains(body, "Ultra-Wide Monitor")
        assertNotContains(body, "USB-C Hub")
    }

    @Test
    fun `GET products with unknown query returns empty list`() = testApplication {
        configureApp()

        val response = client.get("/api/v1/products?query=nonexistent")
        val body = response.bodyAsText()

        assertEquals(HttpStatusCode.OK, response.status)
        assertContains(body, "[]")
    }

    @Test
    fun `GET product by id returns matching product only`() = testApplication {
        configureApp()

        val response = client.get("/api/v1/products/217ac10b-58cc-4372-a567-0e02b2c3d411")
        val body = response.bodyAsText()

        assertEquals(HttpStatusCode.OK, response.status)
        assertContains(body, "Mechanical Keyboard")
        assertNotContains(body, "Wireless Mouse")
        assertNotContains(body, "Ultra-Wide Monitor")
        assertNotContains(body, "USB-C Hub")
    }

    @Test
    fun `GET unknown product id returns 404`() = testApplication {
        configureApp()

        val unknownId = "00000000-0000-0000-0000-000000000000"
        val response = client.get("/api/v1/products/$unknownId")
        val body = response.bodyAsText()

        assertEquals(HttpStatusCode.NotFound, response.status)
        assertContains(body, "Product with id $unknownId not found")
        assertNotContains(body, "Mechanical Keyboard")
        assertNotContains(body, "Wireless Mouse")
        assertNotContains(body, "Ultra-Wide Monitor")
        assertNotContains(body, "USB-C Hub")
    }

    private fun ApplicationTestBuilder.configureApp() {
        environment {
            config = ApplicationConfig("application.conf")
        }
    }
}
