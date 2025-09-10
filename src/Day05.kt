import kotlin.text.first
import kotlin.text.last

fun hasOppositePolarity(f: Char, s: Char) = (f.isLowerCase() && s.isUpperCase()) || (f.isUpperCase() && s.isLowerCase())
private fun compressString(polymer: String): String {
    val chunks = polymer.windowed(2, 1).toMutableList()
    if(polymer.isEmpty()) return ""
    for(i in chunks.indices) {
        val  chunk = chunks[i]
        val f = chunk.first()
        val s = chunk.last()
        if(hasOppositePolarity(f,s) && f.equals(s, ignoreCase = true)) {
            return compressString(polymer.removeRange(i, i+2))
        }
    }
    return polymer
}

private val part1 = { input: String -> compressString(input).count() }

private fun part2(input: String): Int {
    val letters = input.lowercase().toSet().sorted()

    return letters.minOf {
        val trim = input.replace(Regex("[${it.lowercaseChar()}${it.uppercaseChar()}]"), "")
        compressString(trim).count()
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
 