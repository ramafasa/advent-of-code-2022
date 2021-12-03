import kotlin.math.abs
import kotlin.math.ceil

fun main() {
    fun part1(input: List<String>): Int {

        val sums = input.sumOnesInColumns()
        val gamma = sums.map { if (it > input.size / 2) "1" else "0" }.toList().joinToString("").toInt(2)
        val epsilon = sums.map { if (it > input.size / 2) "0" else "1" }.toList().joinToString("").toInt(2)

        return epsilon * gamma
    }

    fun part2(input: List<String>): Int {

        var currentScrubber = input
        var currentOxygen = input
        var position = 0

        while (true) {

            if (currentScrubber.size == 1 && currentOxygen.size == 1)
                break

            if (currentScrubber.size > 1)
                currentScrubber = currentScrubber.filterThatHasMoreDigitsAtPosition(0, position)

            if (currentOxygen.size > 1)
                currentOxygen = currentOxygen.filterThatHasMoreDigitsAtPosition(1, position)

            position++
        }

        val scrubberRating = currentScrubber[0].toInt(2)
        val oxygenRating = currentOxygen[0].toInt(2)

        return oxygenRating * scrubberRating

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

private fun List<String>.filterThatHasMoreDigitsAtPosition(digit: Int, position: Int): List<String> {
    val toKeep: Int = if (this.sumOnesInColumns()[position] >= ceil(this.size / 2.0)) digit else abs(digit - 1)
    return this.filter { it[position].digitToInt() == toKeep }
}

private fun List<String>.sumOnesInColumns(): MutableList<Int> {
    return this.fold(mutableListOf(*Array(this[0].length) { 0 })) { acc, item ->
        item.forEachIndexed { index, i -> acc[index] = acc[index].plus(Integer.parseInt(i.toString())) }
        acc
    }
}