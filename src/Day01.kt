fun part1(input: List<String>): Int {
    return input.sumOf { it.toInt() }
}

fun part2(input: List<String>): Int {
    val input = input.map { it.toInt() }
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
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 2)

    val input = readInput("Day01")
    check(part1(input) == 599)
    check(part2(input) == 81204)
}
