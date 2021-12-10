fun main() {
    fun part1(input: List<String>): Int {
        val board: List<List<Int>> = input.toPointBoard()
        return board.flatMapIndexed { i, line ->
            line
                .filterIndexed { j, height ->
                    getNeighbours(board, i, j).all { board[it.first][it.second] > height }
                }
                .map { it }
        }.sumOf { it + 1 }
    }

    fun part2(input: List<String>): Int {
        val board: List<List<Int>> = input.toPointBoard()

        val lowPoints = board.flatMapIndexed { i, line ->
            line
                .mapIndexed { j, height -> i to j }
                .filterIndexed { j, height ->
                    getNeighbours(board, i, j).all { board[it.first][it.second] > board[i][j] }
                }

        }

        return lowPoints.map { resolveBasin(board, it.first to it.second, 1).first }
            .sortedDescending()
            .take(3)
            .reduce { acc, i -> acc * i }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

fun getNeighbours(board: List<List<Int>>, i: Int, j: Int): List<Pair<Int, Int>> =
    when (Pair(i, j)) {
        0 to 0                              -> listOf(i to j + 1, i + 1 to j)
        0 to board[i].size - 1              -> listOf(i to j - 1, i + 1 to j)
        board.size - 1 to 0                 -> listOf(i - 1 to j, i to j + 1)
        board.size - 1 to board[i].size - 1 -> listOf(i to j - 1, i - 1 to j)
        else                                -> when {
            i == 0                 -> listOf(i to j - 1, i to j + 1, 1 to j)
            i == board.size - 1    -> listOf(i to j - 1, i to j + 1, i - 1 to j)
            j == 0                 -> listOf(i to j + 1, i - 1 to j, i + 1 to j)
            j == board[i].size - 1 -> listOf(i to j - 1, i - 1 to j, i + 1 to j)
            else                   -> listOf(i to j + 1, i to j - 1, i - 1 to j, i + 1 to j)
        }
    }


fun resolveBasin(board: List<List<Int>>, lowPoint: Pair<Int, Int>, size: Int, visited: List<Pair<Int, Int>> = mutableListOf()): Pair<Int, List<Pair<Int, Int>>> {

    val neighbours = getNeighbours(board, lowPoint.first, lowPoint.second)
    var visited = visited
    var size = size

    neighbours.forEach {
        if (!visited.contains(it.first to it.second) && board[it.first][it.second] != 9) {
            val resolveBasin = resolveBasin(board, it, size + 1, visited + lowPoint)
            visited = resolveBasin.second
            size = resolveBasin.first
        }
    }

    return size to visited + lowPoint
}

fun List<String>.toPointBoard() =
    this
        .map { line ->
            line.split("")
                .filter { it.isNotEmpty() }
                .map { it.toInt() }
                .toList()
        }