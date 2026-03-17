package io.github.wlara.ktor.server.examples.basic.test

import kotlin.test.assertFalse

// Similar to the standard library's `assertContains`, but inverted to check absence instead of presence.
fun assertNotContains(charSequence: CharSequence, other: CharSequence, ignoreCase: Boolean = false, message: String? = null) {
    assertFalse(charSequence.contains(other, ignoreCase), message ?: "Expected the char sequence to not contain the substring.\nCharSequence <$charSequence>, substring <$other>, ignoreCase <$ignoreCase>.")
}
