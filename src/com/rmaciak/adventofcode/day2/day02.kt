package com.rmaciak.adventofcode.day2

import readInput

fun main() {

    //A, X - Rock
    //B, Y - Paper
    //C, Z - Scissors

    fun List<String>.toResultPart1(): Int =
        when (this[0] to this[1]) {
            Pair("A", "X") -> 4
            Pair("A", "Y") -> 8
            Pair("A", "Z") -> 3
            Pair("B", "X") -> 1
            Pair("B", "Y") -> 5
            Pair("B", "Z") -> 9
            Pair("C", "X") -> 7
            Pair("C", "Y") -> 2
            Pair("C", "Z") -> 6
            else           -> 100000
        }

    // X -> lose
    // Y -> draw
    // Z -> win

    fun List<String>.toResultPart2(): Int =
        when (this[0] to this[1]) {
            Pair("A", "X") -> 3
            Pair("A", "Y") -> 4
            Pair("A", "Z") -> 8
            Pair("B", "X") -> 1
            Pair("B", "Y") -> 5
            Pair("B", "Z") -> 9
            Pair("C", "X") -> 2
            Pair("C", "Y") -> 6
            Pair("C", "Z") -> 7
            else           -> 100000
        }

    fun List<String>.toRoundResultPart1() =
        this.asSequence()
            .map { line ->
                line.split(" ").toResultPart1()
            }

    fun List<String>.toRoundResultPart2() =
        this.asSequence()
            .map { line ->
                line.split(" ").toResultPart2()
            }

    fun part1(input: List<String>): Int =
        input.toRoundResultPart1().sum()


    fun part2(input: List<String>): Int =
        input.toRoundResultPart2().sum()


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day2/Day02_test")
    val input = readInput("day2/Day02")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    println(part1(input))
    println(part2(input))
}
