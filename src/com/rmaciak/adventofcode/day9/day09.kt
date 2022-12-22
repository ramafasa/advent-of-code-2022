package com.rmaciak.adventofcode.day9

import com.rmaciak.adventofcode.day9.Direction.*
import readInput
import java.util.LinkedList
import java.util.stream.Collectors
import kotlin.math.abs

fun main() {


    fun part1(input: List<String>): Int {

        var rope = Rope(
            Position(0, 0),
            Position(0, 0)
        )

        rope = input.fold(rope) { acc, it ->
            val count = it.split(" ")[1].toInt()
            val direction = Direction.valueOf(it.split(" ")[0])

            (1..count).fold(acc) { accu, _ ->
                accu.moveHead(direction)
            }
        }

        return rope.visitedByTail.size
    }

    fun part2(input: List<String>): Int {

        var rope = MultipleKnotsRope(
            (1 .. 10).map { Position(0, 0) }.toCollection(LinkedList())
        )

        rope = input.fold(rope) { acc, it ->
            val count = it.split(" ")[1].toInt()
            val direction = Direction.valueOf(it.split(" ")[0])

            (1..count).fold(acc) { accu, _ ->
                accu.moveHead(direction)
            }
        }

        return  rope.visitedByTail.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day9/Day09_test")
    val input = readInput("day9/Day09")
//    check(part1(testInput) == 13)
    check(part2(testInput) == 1)

//    println(part1(input))
//    println(part2(input))
}

class MultipleKnotsRope(
    private val knots: LinkedList<Position>,
    val visitedByTail: Set<Position> = setOf(Position(0, 0))
) {


    fun moveHead(direction: Direction): MultipleKnotsRope {
        val newHeadPosition = knots.first().move(direction)

        val iter = knots.listIterator()
        val newKnots = LinkedList(mutableListOf(newHeadPosition))

        while (iter.hasNext()) {
            if (!iter.hasPrevious()) iter.next()
            else {
                val myself = iter.next()
                val myHead = newKnots.last()
                if (myHead.isAdjacentOrCovering(myself)) {
                    newKnots.add(myself)
                } else {
                    newKnots.add(myself.follow(myHead, direction))
                }
            }
        }

        return MultipleKnotsRope(
            newKnots,
            visitedByTail + knots.last()
        )
    }
}

class Rope(
    private val head: Position,
    private val tail: Position,
    val visitedByTail: Set<Position> = setOf(Position(0, 0))
) {


    fun moveHead(direction: Direction): Rope {

        val newHeadPosition = head.move(direction)
        val newTailPosition =
            if (newHeadPosition.isAdjacentOrCovering(tail)) {
                tail
            } else {
                tail.follow(newHeadPosition, direction)
            }

        return Rope(
            newHeadPosition,
            newTailPosition,
            visitedByTail + newTailPosition
        )
    }
}


data class Position(
    val x: Int,
    val y: Int
) {

    fun move(direction: Direction): Position =
        when (direction) {
            U -> Position(x, y - 1)
            D -> Position(x, y + 1)
            L -> Position(x - 1, y)
            R -> Position(x + 1, y)
        }

    fun isAdjacentOrCovering(position: Position) =
        abs(x - position.x) <= 1 && abs(y - position.y) <= 1

    fun follow(position: Position, lastMove: Direction): Position =
        when(lastMove) {
            U -> Position(position.x, position.y + 1)
            D -> Position(position.x, position.y -1)
            R -> Position(position.x - 1, position.y)
            L -> Position(position.x + 1, position.y)
        }




}

enum class Direction {
    U,
    D,
    R,
    L
}