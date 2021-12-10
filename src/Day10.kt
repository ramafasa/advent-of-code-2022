fun main() {

    fun part1(input: List<String>): Int =
        input
            .mapNotNull {
                getFirstIncorrect(it.split("").filter { e -> e.isNotEmpty() }.toList())
            }
            .map {
                when (it) {
                    ")"  -> 3
                    "]"  -> 57
                    "}"  -> 1197
                    ">"  -> 25137
                    else -> 0
                }
            }
            .sum()

    fun part2(input: List<String>): Long {
        val scores = input
            .asSequence()
            .map { it.split("").filter { e -> e.isNotEmpty() }.toList() }
            .filter { getFirstIncorrect(it) == null }
            .map { it.completeString() }
            .map { it.calculateScore() }
            .sorted()
            .toList()

        return scores[scores.size / 2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}

fun List<String>.calculateScore(): Long =
    this
        .map {
            when (it) {
                ")"  -> 1L
                "]"  -> 2L
                "}"  -> 3L
                ">"  -> 4L
                else -> 0L
            }
        }
        .reduce { acc, i -> acc * 5 + i }


fun List<String>.completeString(): List<String> {
    val openers = mutableListOf<String>()

    for (character in this) {
        if (character in listOf("(", "{", "[", "<")) {
            openers.add(character)
        } else {
            openers.removeLast()
        }
    }

    return openers.map { it.opposite() }.reversed()
}

fun getFirstIncorrect(characters: List<String>): String? {

    val openers = mutableListOf<String>()

    for (character in characters) {
        if (character in listOf("(", "{", "[", "<")) {
            openers.add(character)
        } else {
            if (openers.removeLast() != character.opposite()) {
                return character
            }
        }
    }
    return null
}

fun String.opposite() =
    when (this) {
        ")"  -> "("
        "]"  -> "["
        ">"  -> "<"
        "}"  -> "{"
        "("  -> ")"
        "["  -> "]"
        "{"  -> "}"
        "<"  -> ">"
        else -> ""
    }

