private data class FuelCell(val r: Int, val c: Int, var serialNumber: Int) {
    fun totalPower(): Int {
        val rackId = r + 10
        val powerLevel = (rackId * c) + serialNumber
        val hundredsDigit = (powerLevel * rackId) % 1000 / 100
        return hundredsDigit - 5
    }
}

private fun part1(grid: List<List<FuelCell>>): Pair<Int, Int> {
    var maxSum = Int.MIN_VALUE
    var maxCords = Pair(0, 0)
    val gridResults = grid.map { row -> row.map { it.totalPower() } }

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
    return maxCords
}

private fun part2(grid: List<List<FuelCell>>): Triple<Int, Int, Int> {
    val gridResults = grid.map { row -> row.map { it.totalPower() } }

    val sat = Array(301) { IntArray(301) }
    for (r in 1..300) {
        for (c in 1..300) {
            sat[r][c] = gridResults[r-1][c-1] +
                    sat[r-1][c] +
                    sat[r][c-1] -
                    sat[r-1][c-1]
        }
    }

    var maxSum = Int.MIN_VALUE
    var maxCords = Triple(0, 0, 0)

    for (size in 1..300) {
        var foundPositive = false
        for (r in size..300) {
            for (c in size..300) {
                val sum = sat[r][c] - sat[r-size][c] - sat[r][c-size] + sat[r-size][c-size]
                if (sum > 0) foundPositive = true
                if (sum > maxSum) {
                    maxSum = sum
                    maxCords = Triple(r - size + 1,c - size + 1 ,size)
                }
            }
        }
        if (!foundPositive && size > 10) break
    }
    return maxCords
}

private val parseInput = { input: List<String> ->
    val serialNumber = input.first().toInt()
    List(300) { r ->
        List(300) { c ->
            FuelCell(r + 1, c + 1, serialNumber)
        }
    }
}

fun main() {
    val testInput = parseInput(readInput("Day11_test"))
    check(part1(testInput) == Pair(33, 45))
    check(part2(testInput) == Triple(90, 269, 16))

    val input = parseInput(readInput("Day11"))
    check(part1(input) == Pair(20, 32))
    check(part2(input) == Triple(235, 287, 13))
}
 