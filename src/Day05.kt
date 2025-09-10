fun hasOppositePolarity(f: Char, s: Char) = (f.isLowerCase() && s.isUpperCase()) || (f.isUpperCase() && s.isLowerCase())
private fun compressString(polymer: String): String {
    if (polymer.isEmpty()) return ""
    val stack = StringBuilder()
    for (char in polymer) {
        if (stack.isNotEmpty() &&
            char.equals(stack.last(), ignoreCase = true) &&
            hasOppositePolarity(stack.last(), char)
        ) {
            stack.setLength(stack.length - 1)
        } else {
            stack.append(char)
        }
    }
    return stack.toString()
}

private val part1 = { input: String -> compressString(input).count() }
private val part2 = { input: String ->
    input.lowercase().toSet().sorted().minOf {
        compressString(input.replace(Regex("[${it.lowercaseChar()}${it.uppercaseChar()}]"), "")).count()
    }
}

fun main() {
    val testInput = readInput("Day05_test").first()
    check(part1(testInput) == 10)
    check(part2(testInput) == 4)

    val input = readInput("Day05").first()
    check(part1(input) == 10804)
    check(part2(input) == 6650)
}
 