package com.rmaciak.adventofcode.day7

import readInput

fun main() {

    fun parseDirectory(input: List<String>): Directory {
        val root = Directory()
        var currentDir = root

        input
            .drop(2)
            .forEach {
                when {
                    it.startsWith("dir")   -> {
                        val newDirectory = Directory(name = it.substringAfter("dir "), parent = currentDir)
                        currentDir.add(newDirectory)
                    }
                    !it.startsWith("$")    -> {
                        val newFile = File(it.substringBefore(" ").toLong(), it.substringAfter(" "))
                        currentDir.add(newFile)
                    }
                    it == "$ cd .."        -> {
                        currentDir = currentDir.parent!!
                    }
                    it.startsWith("$ cd ") -> {
                        currentDir = (currentDir.children.find { fo -> fo.name() == it.substringAfter("$ cd ") } as Directory)
                    }
                    it == "$ ls"           -> {}
                }
            }

        return root
    }

    fun part1(input: List<String>): Long =
        parseDirectory(input).withAtMostSizeOf(100_000).sumOf { it.size() }


    fun part2(input: List<String>): Long {
        val root = parseDirectory(input)
        val toFree = 30_000_000 - (70_000_000 - root.size())

        return root.withAtLeastSizeOf(toFree).minByOrNull { it.size() }!!.size()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day7/Day07_test")
    val input = readInput("day7/Day07")
    check(part1(testInput) == 95437L)
    check(part2(testInput) == 24933642L)

    println(part1(input))
    println(part2(input))
}

interface FileObject {
    fun size(): Long
    fun name(): String
}

data class Directory(
    var children: MutableList<FileObject> = mutableListOf(),
    val name: String = "/",
    val parent: Directory? = null
) : FileObject {

    override fun size(): Long =
        children.sumOf { it.size() }

    override fun name(): String = name

    fun withAtMostSizeOf(maxSize: Long): List<Directory> {
        val children = children.flatMap {
            if (it is Directory) {
                it.withAtMostSizeOf(maxSize)
            } else {
                emptyList()
            }
        }

        return if (this.size() <= maxSize) {
            return listOf(this) + children
        } else {
            children
        }
    }

    fun withAtLeastSizeOf(maxSize: Long): List<Directory> {
        val children = children.flatMap {
            if (it is Directory) {
                it.withAtLeastSizeOf(maxSize)
            } else {
                emptyList()
            }
        }

        return if (this.size() > maxSize) {
            return listOf(this) + children
        } else {
            children
        }
    }

    fun add(fileObject: FileObject) = children.add(fileObject)
}

data class File(
    val size: Long,
    val name: String
) : FileObject {
    override fun size(): Long = size
    override fun name(): String = name
}