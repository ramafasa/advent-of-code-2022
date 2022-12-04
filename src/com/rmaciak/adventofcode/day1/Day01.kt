package com.rmaciak.adventofcode.day1

import readInput

fun main() {

    fun computeElfCalories(input: List<String>): MutableList<Int> {
        val elfCalories = mutableListOf<Int>()
        var caloriesSum = 0
        for (calories: String in input) {
            if(calories.isNotEmpty()) {
                caloriesSum += calories.toInt()
            } else {
                elfCalories.add(caloriesSum)
                caloriesSum = 0
            }
        }
        elfCalories.add(caloriesSum)
        return elfCalories
    }

    fun part1(input: List<String>): Int =
        computeElfCalories(input) .maxOf { it }


    fun part2(input: List<String>): Int =
        computeElfCalories(input).asSequence().sortedDescending().take(3).sum()


    val testInput = readInput("day1/Day01_test")
    val input = readInput("day1/Day01")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    println(part1(input))
    println(part2(input))
}
