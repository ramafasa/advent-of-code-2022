fun main() {
    fun part1(input: List<String>): Int {
        val lines = input.toLines()
            .withOnlyHorizontalAndVertical()

        return (0..999).asSequence()
            .flatMap { x -> (0..999).asSequence().map { y -> Point(x, y) } }
            .map { point -> lines.asSequence().filter { line -> line.covers(point) }.count() }
            .filter { it >= 2 }
            .count()
    }

    fun part2(input: List<String>): Int {
        val lines = input.toLines()

        return (0..999).asSequence()
            .flatMap { x -> (0..999).asSequence().map { y -> Point(x, y) } }
            .map { point -> lines.asSequence().filter { line -> line.covers(point) }.count() }
            .filter { it >= 2 }
            .count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

fun List<String>.toLines() =
    this.map {
        Line(Point(it.substringBefore(",").toInt(),
            it.substringAfter(",").substringBefore(" -> ").toInt()),
            Point(it.substringBeforeLast(",").substringAfterLast(" -> ").toInt(),
                it.substringAfterLast(",").toInt())
        )
    }
        .toList()

fun List<Line>.withOnlyHorizontalAndVertical() =
    this.filter { it.from.x == it.to.x || it.from.y == it.to.y }
        .toList()


data class Line(
    val from: Point,
    val to: Point
) {

    companion object {
        val diagonalCache = mutableMapOf<Line, List<Point>>()
    }

    fun covers(point: Point) =
        if (from.x == to.x && point.x == from.x) {
            point.y in range(from.y, to.y)
        } else if (from.y == to.y && point.y == from.y) {
            point.x in range(from.x, to.x)
        } else {
            point in diagonalPoints(this)
        }

    private fun diagonalPoints(line: Line): List<Point> =
        diagonalCache.computeIfAbsent(line) {
            range(line.from.x, line.to.x).asSequence()
                .zip(range(line.from.y, line.to.y).asSequence()) { x, y -> Point(x, y) }
                .toList()
        }

    private fun range(x: Int, y: Int) =
        if (y > x) {
            x..y
        } else {
            x downTo y
        }
}

data class Point(
    val x: Int,
    val y: Int
)