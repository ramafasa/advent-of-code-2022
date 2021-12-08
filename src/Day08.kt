fun main() {
    fun part1(input: List<String>): Int =
        input
            .map { it.split("|")[1].trim() }
            .flatMap { it.split(" ") }
            .filter { it.length in listOf(2, 3, 4, 7) }
            .count()

    fun part2(input: List<String>): Int =
        input
            .map { calculateNumberForLine(it) }
            .sum()


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

fun calculateNumberForLine(line: String): Int {
    val mapping = resolveMapping(line)

    val number2 = listOf(mapping["a"], mapping["c"], mapping["d"], mapping["e"], mapping["g"])
    val number3 = listOf(mapping["a"], mapping["c"], mapping["d"], mapping["f"], mapping["g"])
    val number5 = listOf(mapping["a"], mapping["b"], mapping["d"], mapping["f"], mapping["g"])
    val number0 = listOf(mapping["a"], mapping["b"], mapping["c"], mapping["e"], mapping["f"], mapping["g"])
    val number6 = listOf(mapping["a"], mapping["b"], mapping["d"], mapping["e"], mapping["f"], mapping["g"])
    val number9 = listOf(mapping["a"], mapping["b"], mapping["c"], mapping["d"], mapping["f"], mapping["g"])

    return line.split(" | ")[1].trim()
        .split(" ")
        .map {
            when {
                it.length == 7                                        -> 8
                it.length == 2                                        -> 1
                it.length == 3                                        -> 7
                it.length == 4                                        -> 4
                it.length == 5 && it.toLetters().containsAll(number2) -> 2
                it.length == 5 && it.toLetters().containsAll(number3) -> 3
                it.length == 5 && it.toLetters().containsAll(number5) -> 5
                it.length == 6 && it.toLetters().containsAll(number0) -> 0
                it.length == 6 && it.toLetters().containsAll(number6) -> 6
                it.length == 6 && it.toLetters().containsAll(number9)        -> 9
                else                                                  -> 0
            }
        }
        .joinToString("")
        .toInt()
}

fun resolveMapping(line: String): Map<String, String> {
    val elements = line.split(" | ")[0].trim().split(" ").toList()

    val a: String = (elements.singleWithLength(3) - elements.singleWithLength(2)).first()
    val d: String = (elements.singleWithLength(4) - elements.singleWithLength(2)).first { elements.withLength(5).all { withLength5 -> withLength5.contains(it) } }
    val b: String = (elements.singleWithLength(4) - elements.singleWithLength(2) - d).first()
    val f: String = elements.singleWithLength(2).first { elements.withLength(6).all { withLength6 -> withLength6.contains(it) } }
    val c: String = (elements.singleWithLength(2) - f).first()
    val e: String = (elements.singleWithLength(7) - a - d - b - f - c).first { elements.withLength(5).filter { withhLength5 -> withhLength5.contains(it) }.count() == 1 }
    val g: String = (elements.singleWithLength(7) - a - d - b - f - c - e).first()

    return mapOf("a" to a, "b" to b, "c" to c, "d" to d, "e" to e, "f" to f, "g" to g)

}

fun List<String>.singleWithLength(length: Int) =
    this.first { it.length == length }.split("").filter { it.isNotEmpty() }

fun List<String>.withLength(length: Int) =
    this.filter { it.length == length }

fun String.toLetters() =
    this.split("").filter { el -> el.isNotEmpty() }