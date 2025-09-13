data class Cord(val name: String, val r: Int, val c: Int)
private fun part1(input: List<String>, isPart2: Boolean = false, lessThan: Int = 32): Int {
    val cords = input.mapIndexed { index, s ->
        val (c, r) = s.split(", ").map { it.toInt() }
        Cord("cord-$index", r,c)
    }

    val areaSize = mutableMapOf<Cord, Int>()

    val maxR = cords.maxOf { it.r } + 3_00
    val maxC = cords.maxOf { it.c } + 3_00

    var minCounts = 0

    for(r in -maxR until maxR) {
        for(c in -maxC until maxC) {

            val dist = buildMap {
                for(cord in cords) {
                    val distance = kotlin.math.abs(cord.r - r) + kotlin.math.abs(cord.c - c)
                    put(cord, distance)
                }
            }

            if(isPart2 && dist.values.sum() < lessThan) minCounts++

            val sortedByDistance = dist.toList().sortedBy { it.second }
            val minDistance = sortedByDistance.first().second
            val minEntries = sortedByDistance.takeWhile { it.second == minDistance }

            if(minEntries.size == 1) areaSize.merge(minEntries.first().first, 1,Int::plus)
        }
    }

    return if(isPart2) minCounts else areaSize.filter { it.value < 10_000 }.maxOf { it.value }
}

fun main() {
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 17)
    check(part1(testInput, true) == 16)
     
    val input = readInput("Day06")
    check(part1(input) == 4186)
    check(part1(input, true, 10_000) == 45509)
}
 