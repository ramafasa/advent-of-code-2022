fun main() {

    fun part1(input: List<String>): Int =
        input
            .takeLastWhile { it.isNotEmpty() }
            .take(1)
            .map { it.toFold() }
            .fold(input.toInitialDots()) { acc, fold ->
                acc.map { pair ->
                    when (fold.axis) {
                        Axis.Y -> pair.foldY(fold.value)
                        Axis.X -> pair.foldX(fold.value)
                    }
                }.toSet()
            }.size


    fun part2(input: List<String>): Int =
        input
            .takeLastWhile { it.isNotEmpty() }
            .map { it.toFold() }
            .fold(input.toInitialDots()) { acc, fold ->
                acc.map { pair ->
                    when (fold.axis) {
                        Axis.Y -> pair.foldY(fold.value)
                        Axis.X -> pair.foldX(fold.value)
                    }
                }.toSet()
            }.also { printCode(it) }.size


// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
//    check(part1(testInput) == 17)
//    check(part2(testInput) == 33)

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}

private fun printCode(dots: Set<Pair<Int, Int>>) {
    for (i in 0..100) {
        for (j in 0..100) {
            if (dots.contains(Pair(j, i))) {
                print("*")
            } else {
                print(" ")
            }
        }
        println()
    }
}

private fun Pair<Int, Int>.foldY(foldValue: Int) =
    if (this.second > foldValue) {
        Pair(this.first, foldValue - (this.second - foldValue))
    } else {
        this
    }

private fun Pair<Int, Int>.foldX(foldValue: Int) =
    if (this.first > foldValue) {
        Pair(foldValue - (this.first - foldValue), this.second)
    } else {
        this
    }


private fun String.toFold(): Fold {
    val axis = if (this.startsWith("fold along y=")) {
        Axis.Y
    } else {
        Axis.X
    }

    return Fold(axis, this.substringAfterLast("=").toInt())
}

private fun List<String>.toInitialDots() =
    this
        .takeWhile { it.isNotEmpty() }
        .map { line -> line.split(",").let { Pair(it[0].toInt(), it[1].toInt()) } }
        .toSet()

class Fold(
    val axis: Axis,
    val value: Int
)

enum class Axis {
    X, Y
}


