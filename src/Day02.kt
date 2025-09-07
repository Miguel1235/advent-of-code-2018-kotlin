private fun part1(input: List<String>): Int {
    val ids = input.map { it.toList() }
    var twoTimes = 0
    var threeTimes = 0
    for (id in ids) {
        val grouped = id.groupingBy { it }.eachCount()
        if (grouped.count { it.value == 3 } > 0) threeTimes++
        if (grouped.count { it.value == 2 } > 0) twoTimes++
    }
    return twoTimes * threeTimes
}

private fun part2(input: List<String>): String {
    val ids = input.map { it.toList() }
    for (i in ids.indices) {
        for (j in ids.indices) {
            if (i == j) continue
            var diff = 0
            var idx2Remove = -1
            for (k in ids[i].indices) {
                if (ids[i][k] != ids[j][k]) {
                    diff++
                    idx2Remove = k
                }
            }
            if (diff == 1) return ids[i].filterIndexed { index, _ -> index != idx2Remove }.joinToString("")

        }
    }
    return ""
}

fun main() {
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 0)
    check(part2(testInput) == "fgij")

    val input = readInput("Day02")
    check(part1(input) == 8610)
    check(part2(input) == "iosnxmfkpabcjpdywvrtahluy")
}
 