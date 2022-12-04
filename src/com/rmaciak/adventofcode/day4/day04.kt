package com.rmaciak.adventofcode.day4

import readInput

fun main() {

    fun part1(input: List<String>): Int =
        input.asSequence()
            .map { it.split(",").map { it.split("-") }.map { it[0].toInt()..it[1].toInt() } }
            .filter { list -> list[0].all { list[1].contains(it) } || list[1].all { list[0].contains(it) } }
            .count()

    fun part2(input: List<String>): Int =
        input.asSequence()
            .map { it.split(",").map { it.split("-") }.map { it[0].toInt()..it[1].toInt() } }
            .filter { list -> list[0].any { list[1].contains(it) } || list[1].any { list[0].contains(it) } }
            .count()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day4/Day04_test")
    val input = readInput("day4/Day04")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    println(part1(input))
    println(part2(input))
}

