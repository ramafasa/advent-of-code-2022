fun main() {
    fun part1(input: List<String>): Int {
        val forward = input
            .asSequence()
            .filter { it.startsWith("forward") }
            .sumOf { it.substringAfter(" ").toInt() }

        val depth = input
            .asSequence()
            .filter { !it.startsWith("forward") }
            .map { if (it.startsWith("down")) it.substringAfter(" ").toInt() else it.substringAfter(" ").toInt().unaryMinus() }
            .sum()

        return forward * depth
    }

    fun part2(input: List<String>): Int {
        val forward = input
            .asSequence()
            .filter { it.startsWith("forward") }
            .sumOf { it.substringAfter(" ").toInt() }

        val depth = input.fold(Pair(0, 0)) { acc, item ->
            when {
                item.startsWith("down") -> Pair(acc.first + item.substringAfter(" ").toInt(), acc.second)
                item.startsWith("up")   -> Pair(acc.first - item.substringAfter(" ").toInt(), acc.second)
                else                    -> Pair(acc.first, acc.second + acc.first * item.substringAfter(" ").toInt())
            }
        }.second

        return forward * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

