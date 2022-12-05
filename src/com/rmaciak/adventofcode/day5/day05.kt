package com.rmaciak.adventofcode.day5

import readInput
import java.util.LinkedList

fun main() {

    fun createStacks(input: List<String>): MutableList<LinkedList<Char>> {
        val stacks: MutableList<LinkedList<Char>> = input
                                                        .dropWhile { !it.startsWith(" 1") }
                                                        .first()
                                                        .split(" ")
                                                        .filter { it.isNotBlank() }
                                                        .maxOfOrNull { it.toInt() }
                                                        ?.let {
                                                            MutableList(it) {
                                                                LinkedList()
                                                            }
                                                        } ?: mutableListOf()

        input
            .asSequence()
            .takeWhile { !it.startsWith(" 1") }
            .forEach {
                it.windowed(4, 4, true)
                    .forEachIndexed { index, elem: String ->
                        if (elem.isNotBlank()) {
                            stacks[index].add(elem[1])
                        }
                    }
            }

        return stacks
    }


    fun part1(input: List<String>): String {
        val stacks: MutableList<LinkedList<Char>> = createStacks(input)
        input
            .asSequence()
            .dropWhile { it.isNotEmpty() }
            .drop(1)
            .forEach {
                val count = it.substringAfter("move ").substringBefore(" from").toInt()
                val from = it.substringAfter("from ").substringBefore(" to").toInt()
                val to = it.substringAfter(" to ").toInt()

                for (i in 1..count) {
                    stacks[to - 1].addFirst(stacks[from - 1].removeFirst())
                }
            }

        return stacks.map { it.first }.joinToString("")
    }

    fun part2(input: List<String>): String {
        val stacks: MutableList<LinkedList<Char>> = createStacks(input)
        input
            .asSequence()
            .dropWhile { it.isNotEmpty() }
            .drop(1)
            .forEach {
                val count = it.substringAfter("move ").substringBefore(" from").toInt()
                val from = it.substringAfter("from ").substringBefore(" to").toInt()
                val to = it.substringAfter(" to ").toInt()

                val toAdd = mutableListOf<Char>()
                for (i in 1..count) {
                    toAdd.add(stacks[from - 1].removeFirst())
                }

                stacks[to - 1].addAll(0, toAdd)
            }

        return stacks.map { it.first }.joinToString("")

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day5/Day05_test")
    val input = readInput("day5/Day05")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    println(part1(input))
    println(part2(input))
}

