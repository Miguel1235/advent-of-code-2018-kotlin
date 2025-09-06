private val parseInput = { input: List<String> -> input.map { it.toInt() } }

private val part1 = { input: List<Int> -> input.sum() }
private fun part2(input: List<Int>): Int {
    val seenFrequencies = mutableSetOf<Int>()
    var index = 0
    var currentFrequency = 0

    while (true) {
        if (!seenFrequencies.add(currentFrequency)) return currentFrequency
        currentFrequency += input[index % input.size]
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
