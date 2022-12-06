package com.rmaciak.adventofcode.day6

import readInput

fun main() {

    fun calculateStartOfPacketMarker(input: String, markerSize: Int): Int =
        input
            .split("")
            .asSequence()
            .filter { it.isNotBlank() }
            .windowed(markerSize, 1)
            .first { it.distinct().toList().size == markerSize }
            .joinToString("")
            .let { input.indexOf(it) + markerSize }

    fun part1(input: List<String>): Int = calculateStartOfPacketMarker(input[0], 4)
    fun part2(input: List<String>): Int = calculateStartOfPacketMarker(input[0], 14)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day6/Day06_test")
    val input = readInput("day6/Day06")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    println(part1(input))
    println(part2(input))
}

