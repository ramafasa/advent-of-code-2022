package com.rmaciak.adventofcode.day3

import com.rmaciak.adventofcode.common.Tuple
import readInput

fun main() {

    val scoreBoard: List<Pair<Char, Int>> = ('a'..'z').toList().zip(1..26) { char, score ->
        char to score
    } + ('A'..'Z').toList().zip(27..52) { char, score ->
        char to score
    }

    fun String.toScore() =
        scoreBoard
            .first { it.first == this.toCharArray()[0] }
            .second

    fun part1(input: List<String>): Int =
        input
            .asSequence()
            .map { it.substring(0, it.length / 2) to it.substring(it.length / 2, it.length) }
            .map { it.first.split("").distinct() to it.second.split("").distinct() }
            .fold(emptyList<String>()) { acc, pair ->
                acc + pair.first.filter { pair.second.contains(it) }
            }
            .filter { it != "" }
            .sumOf { it.toScore() }


    fun part2(input: List<String>): Int =
        input
            .asSequence()
            .windowed(3, 3, true)
            .map { Tuple(it[0], it[1], it[2]) }
            .map { it.map { it.split("").distinct().filter { it != "" } } }
            .fold(emptyList<String>()) { acc, tuple ->
                acc + tuple.first.filter { tuple.second.contains(it) && tuple.third.contains(it) }
            }
            .sumOf { it.toScore() }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day3/Day03_test")
    val input = readInput("day3/Day03")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    println(part1(input))
    println(part2(input))
}

