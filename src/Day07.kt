import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val positions = input[0].split(",").map { it.toInt() }

        val minPosition = positions.minOrNull()!!
        val maxPosition = positions.maxOrNull()!!

        return (minPosition..maxPosition).asSequence()
            .map { position ->
                positions.sumOf { abs(position - it) }
            }.minOrNull()!!
    }

    fun part2(input: List<String>): Int {
        val positions = input[0].split(",").map { it.toInt() }

        val minPosition = positions.minOrNull()!!
        val maxPosition = positions.maxOrNull()!!

        return (minPosition..maxPosition).asSequence()
            .map { position ->
                positions.sumOf {
                    val n = abs(position - it)
                    (1..n).sum()
                }
            }.minOrNull()!!
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

