private val parseInput = { input: List<String> -> input.map { it.toInt() } }

private val part1 = { input: List<Int> -> input.sum() }

private fun part2(input: List<Int>): Int {
    val frequencies = mutableListOf(0)
    var index = 0

    while (true) {
        val change = input[index % input.size]
        val resultingFrequency = frequencies.last() + change
        if (frequencies.contains(resultingFrequency)) return resultingFrequency
        frequencies.add(resultingFrequency)
        index++
    }
}


fun main() {
    val testInput = parseInput(readInput("Day01_test"))
    check(part1(testInput) == 3)
    check(part2(testInput) == 2)

    val input = parseInput(readInput("Day01"))
    check(part1(input) == 599)
    check(part2(input) == 81204)
}
