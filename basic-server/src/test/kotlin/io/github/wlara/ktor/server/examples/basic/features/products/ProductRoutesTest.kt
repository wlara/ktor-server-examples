package io.github.wlara.ktor.server.examples.basic.features.products

import io.github.wlara.ktor.server.examples.basic.module
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class ProductRoutesTest {

    @Test
    fun `GET products without query returns all products`() = testApplication {
        application { module() }

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
        application { module() }

        val response = client.get("/api/v1/products?query=wireless")
        val body = response.bodyAsText()

        assertEquals(HttpStatusCode.OK, response.status)
        assertContains(body, "Wireless Mouse")
        assertFalse(body.contains("Mechanical Keyboard"))
        assertFalse(body.contains("Ultra-Wide Monitor"))
        assertFalse(body.contains("USB-C Hub"))
    }

    @Test
    fun `GET products with unknown query returns empty list`() = testApplication {
        application { module() }

        val response = client.get("/api/v1/products?query=nonexistent")
        val body = response.bodyAsText()

        assertEquals(HttpStatusCode.OK, response.status)
        assertContains(body, "[]")
    }

    @Test
    fun `GET product by id returns matching product only`() = testApplication {
        application { module() }

        val response = client.get("/api/v1/products/217ac10b-58cc-4372-a567-0e02b2c3d411")
        val body = response.bodyAsText()

        assertEquals(HttpStatusCode.OK, response.status)
        assertContains(body, "Mechanical Keyboard")
        assertFalse(body.contains("Wireless Mouse"))
        assertFalse(body.contains("Ultra-Wide Monitor"))
        assertFalse(body.contains("USB-C Hub"))
    }

    @Test
    fun `GET unknown product id returns 404`() = testApplication {
        application { module() }

        val unknownId = "00000000-0000-0000-0000-000000000000"
        val response = client.get("/api/v1/products/$unknownId")
        val body = response.bodyAsText()

        assertEquals(HttpStatusCode.NotFound, response.status)
        assertContains(body, "Product with id $unknownId not found")
        assertFalse(body.contains("Mechanical Keyboard"))
        assertFalse(body.contains("Wireless Mouse"))
        assertFalse(body.contains("Ultra-Wide Monitor"))
        assertFalse(body.contains("USB-C Hub"))
    }
}
