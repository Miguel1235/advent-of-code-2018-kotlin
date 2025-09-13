private fun part1(cords: List<Pair<Int, Int>>, isPart2: Boolean = false, lessThan: Int = 32): Int {
    val areaSize = mutableMapOf<Pair<Int, Int>, Int>()
    val maxR = cords.maxOf { it.first } + 3_00
    val maxC = cords.maxOf { it.second } + 3_00
    var minCounts = 0

    for (r in -maxR until maxR) {
        for (c in -maxC until maxC) {
            val dist = cords.associateWith { kotlin.math.abs(it.first - r) + kotlin.math.abs(it.second - c) }

            if (isPart2 && dist.values.sum() < lessThan) minCounts++
            val sortedByDistance = dist.toList().sortedBy { it.second }
            val minDistance = sortedByDistance.first().second
            val minEntries = sortedByDistance.takeWhile { it.second == minDistance }
            if (minEntries.size == 1) areaSize.merge(minEntries.first().first, 1, Int::plus)
        }
    }
    return if (isPart2) minCounts else areaSize.filter { it.value < 10_000 }.maxOf { it.value }
}

private val parseInput = { input: List<String> ->
    input.map {
        val (c, r) = it.split(", ").map(String::toInt)
        r to c
    }
}

fun main() {
    val testInput = parseInput(readInput("Day06_test"))
    check(part1(testInput) == 17)
    check(part1(testInput, true) == 16)

    val input = parseInput(readInput("Day06"))
    check(part1(input) == 4186)
    check(part1(input, true, 10_000) == 45509)
}
 