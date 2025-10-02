private data class FuelCell(val r: Int, val c: Int, var serialNumber: Int) {
    fun totalPower(): Int {
        val rackId = r + 10
        val powerLevel = (rackId * c) + serialNumber
        val hundredsDigit = (powerLevel * rackId) % 1000 / 100
        return hundredsDigit - 5
    }
}
private fun part1(serialNumber: Int): Pair<Int, Int> {
    val gridResults = List(300) { r ->
        List(300) { c ->
            FuelCell(r + 1, c + 1, serialNumber).totalPower()
        }
    }

    var maxSum = Int.MIN_VALUE
    var maxCords = Pair(0, 0)

    for (r in 0 until 298) {
        for (c in 0 until 298) {
            var sum = 0
            for (dr in 0 until 3) {
                for (dc in 0 until 3) {
                    sum += gridResults[r + dr][c + dc]
                }
            }
            if (sum > maxSum) {
                maxSum = sum
                maxCords = Pair(r + 1, c + 1)
            }
        }
    }

    println("Max power: $maxSum at coordinates: $maxCords")

    return maxCords
}

private fun part2(input: List<String>): Int {
    return 0
}

private val parseInput = { input: List<String> -> input.first().toInt() }

fun main() {
    val testInput = parseInput(readInput("Day11_test"))
    check(part1(testInput) == Pair(33,45))

    val input = parseInput(readInput("Day11"))
    check(part1(input) == Pair(20,32))
//    check(part2(input) == 0)
}
 