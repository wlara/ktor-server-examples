package io.github.wlara.ktor.server.examples.basic.core.extensions

import io.github.wlara.ktor.server.examples.basic.core.exceptions.HttpUnprocessableEntityException

private val nameRegex = Regex("^\\p{L}+(?:[ '-]\\p{L}+)*$")

private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

fun String.checkLength(
    field: String,
    maxLength: Int
) {
    if (length > maxLength) {
        throw HttpUnprocessableEntityException("$field cannot exceed $maxLength characters")
    }
}

fun String.checkName(
    field: String = "name",
    maxLength: Int = 256
) {
    checkLength(field, maxLength)
    if (!nameRegex.matches(this)) {
        throw HttpUnprocessableEntityException("$field contains invalid characters")
    }
}

fun String.checkEmail(
    field: String = "email",
    maxLength: Int = 256
) {
    checkLength(field, maxLength)
    if (!matches(emailRegex)) {
        throw HttpUnprocessableEntityException("$field is not a valid email address")
    }
}
