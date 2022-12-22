package com.rmaciak.adventofcode.day8

import readInput

fun main() {

    fun mapToElementsRow(input: List<String>, indexRow: Int, indexColumn: Int): Int =
        input[indexRow]
            .split("")
            .filter { elem -> elem.isNotBlank() }[indexColumn]
            .toInt()

    fun mapToElementsCol(input: List<String>, indexRow: Int, indexColumn: Int): Int =
        input[indexRow][indexColumn].digitToInt()


    fun part1(input: List<String>): Int {
        val edgeTrees = input.size * 2 + ((input[0].length - 2) * 2)

        val interiorTrees = input
            .asSequence()
            .drop(1)
            .takeWhile { it != input.last() }
            .mapIndexed { indexRow, it ->
                it.split("")
                    .asSequence()
                    .filter { it.isNotBlank() }
                    .map(String::toInt)
                    .mapIndexed { indexColumn, int ->
                        if (indexColumn == 0 || indexColumn == input[0].length - 1) {
                            false
                        } else {
                            (0 until indexColumn).map { mapToElementsRow(input, indexRow + 1, it) }.all { elem -> elem < int } ||
                            (indexColumn + 1 until input[0].length).map { mapToElementsRow(input, indexRow + 1, it) }.all { elem -> elem < int } ||
                            (0..indexRow).map { mapToElementsCol(input, it, indexColumn) }.all { elem -> elem < int } ||
                            (indexRow + 2 until input.size).map { mapToElementsCol(input, it, indexColumn) }.all { elem -> elem < int }
                        }
                    }
                    .count { it }
            }.sum()

        return edgeTrees + interiorTrees
    }

    fun part2(input: List<String>): Int =
        input
            .asSequence()
            .drop(1)
            .takeWhile { it != input.last() }
            .mapIndexed { indexRow, it ->
                it.split("")
                    .asSequence()
                    .filter { it.isNotBlank() }
                    .map(String::toInt)
                    .mapIndexed { indexColumn, int ->
                        if (indexColumn == 0 || indexColumn == input[0].length - 1) {
                            0
                        } else {
                            (0 until indexColumn).map { mapToElementsRow(input, indexRow + 1, it) }.reversed().takeUntilLowerThan(int).count() *
                            (indexColumn + 1 until input[0].length).map { mapToElementsRow(input, indexRow + 1, it) }.takeUntilLowerThan(int).count() *
                            (0..indexRow).map { mapToElementsCol(input, it, indexColumn) }.reversed().takeUntilLowerThan(int).count() *
                            (indexRow + 2 until input.size).map { mapToElementsCol(input, it, indexColumn) }.takeUntilLowerThan(int).count()
                        }
                    }
                    .maxOf { it }
            }.maxOf { it }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day8/Day08_test")
    val input = readInput("day8/Day08")
//    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

//    println(part1(input))
    println(part2(input))
}

inline fun Iterable<Int>.takeUntilLowerThan(lowerThan: Int): List<Int> {
    val list = ArrayList<Int>()
    for (item in this) {
        if (item >= lowerThan) {
            list.add(item)
            break
        }

        list.add(item)
    }
    return list
}