package com.rmaciak.adventofcode.common

data class Tuple<T>(
    val first: T,
    val second: T,
    val third: T
) {

    fun <R> map(transform: (T) -> R): Tuple<R> =
        Tuple(transform.invoke(first), transform.invoke(second), transform.invoke(third))
}