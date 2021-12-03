fun main() {
    fun part1(input: List<String>): Int = input
        .asSequence()
        .map { s -> s.toInt() }
        .zipWithNext { a, b -> b - a }
        .count { it > 0 }


    fun part2(input: List<String>): Int = input
        .asSequence()
        .map { s -> s.toInt() }
        .windowed(3, transform = { it.sum() })
        .zipWithNext { a, b -> b - a }
        .count { it > 0 }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
