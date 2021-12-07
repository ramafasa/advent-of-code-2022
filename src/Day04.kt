import java.util.stream.IntStream
import kotlin.streams.toList

fun main() {
    fun part1(input: List<String>): Int {
        val draws = input.toDraws()
        val boards = input.toBoard()

        for (draw in draws) {
            for (board in boards) {
                if (board.mark(draw)) {

                    val sumOfUnmarked = board.sumUnmarked()
                    return sumOfUnmarked * draw
                }
            }
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        val draws = input.toDraws()
        val boards = input.toBoard()

        for (draw in draws) {
            for (board in boards) {

                if (board.finished) {
                    continue
                }

                if (board.mark(draw) && boards.filter { !it.finished }.count() == 0) {

                    val sumOfUnmarked = board.sumUnmarked()
                    return sumOfUnmarked * draw
                }
            }
        }
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

private fun List<String>.toDraws() =
    this.first().split(",").map { it.toInt() }

private fun List<String>.toBoard(): List<Board> {
    val list = this.drop(2).toList()

    val boards = mutableListOf<Board>()
    var board = mutableListOf<String>()

    for (line in list) {
        if (line.isNotEmpty())
            board.add(line.trim())
        else {
            boards.add(Board.create(board))
            board = mutableListOf()
        }
    }

    boards.add(Board.create(board))

    return boards

}

data class Board(
    val rows: List<RowOrColumn>,
    var finished: Boolean = false
    //val columns: List<RowOrColumn>
) {

    fun sumUnmarked(): Int =
        rows.sumOf { it.sumUnmarked() }

    fun mark(value: Int): Boolean {
        val isRowFullyMarked = rows.map { it.mark(value) }.any { it }

        val isColumnFullyMarked: Boolean = IntStream.range(0, rows.size).toList()
            .map { column -> rows.all { it.isMarkedInColumn(column) } }
            .any { it }

        if (isColumnFullyMarked || isRowFullyMarked) {
            this.finished = true
        }

        return isColumnFullyMarked || isRowFullyMarked

    }

    companion object {
        fun create(rows: List<String>) =
            Board(
                rows = rows.map { row -> RowOrColumn(row.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }) }.toList()
            )

    }
}

data class RowOrColumn(
    val values: MutableMap<Int, Boolean>
) {

    constructor(list: List<Int>) : this(list.associateBy({ it }, { false }).toMutableMap())

    fun sumUnmarked() =
        values.asSequence().filter { !it.value }.sumOf { it.key }

    fun mark(value: Int): Boolean {
        if(values[value] != null)
            values[value] = true

        return values.all { it.value }
    }

    fun isMarkedInColumn(column: Int): Boolean {
        return values.values.filterIndexed { index, _ -> index == column }.first()
    }

}