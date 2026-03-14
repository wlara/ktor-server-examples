package io.github.wlara.ktor.server.examples.basic.core.extensions

fun Throwable.deepestCause(): Throwable {
    // Guard against accidental cycles in cause chains.
    val visited = mutableSetOf<Throwable>()
    var current = this
    while (visited.add(current) && current.cause != null) {
        current = current.cause!!
    }
    return current
}
