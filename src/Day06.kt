fun main() {
    fun part1(input: List<String>): Int {
        var fish = input[0].split(",").map { it.toInt() }

        for (day in 1..80) {
            val newFish = fish.count { it == 0 }
            fish = (fish.map { if (it == 0) 6 else it - 1 } + List(newFish) { 8 })
        }

        return fish.size
    }

    fun part2(input: List<String>): Long {
        val fish = input[0].split(",").map { it.toInt() }
        var fishCounter: Map<Int, Long> = fish.associateBy({ it }, { day -> fish.count { it == day }.toLong() })

        for (day in 1..256) {
            val currentFishCounter = mutableMapOf<Int, Long>()
            for (counter in 8 downTo 0) {
                when (counter) {
                    8    -> currentFishCounter[8] = fishCounter.getOrDefault(0, 0)
                    6    -> currentFishCounter[6] = fishCounter.getOrDefault(7, 0) + fishCounter.getOrDefault(0, 0)
                    else -> currentFishCounter[counter] = fishCounter.getOrDefault(counter + 1, 0)
                }
            }
            fishCounter = currentFishCounter
        }

        return fishCounter.values.sumOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934)
    check(part2(testInput) == 26984457539)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

