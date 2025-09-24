import kotlin.collections.component1
import kotlin.collections.component2

private fun part1(input: List<Int>): Int {
    var r = getData(input)
    var total = r.second
    while(r.first.isNotEmpty()) {
        r = getData(r.first)
        total += r.second
    }
    println(total)
    return 0
}

private fun getData(tape: List<Int>): Pair<List<Int>, Int> {
    val (children, metadataQ) = tape.take(2)

    if(children != 0) {
        val metadata = tape.takeLast(metadataQ)
        println("tape: $tape metadata $metadata")
        if(tape.size < metadata.size + 2) return Pair(tape, metadata.sum())
        val stringR = tape.subList(2, tape.size - metadataQ)
        return Pair(stringR, metadata.sum())
    } else {
        val metadata = tape.subList(2, 2 + metadataQ)
        println("metadata $metadata")
        val r = tape.filterIndexed { index, _ -> index > metadata.size + 1 }
        return Pair(r, metadata.sum())
    }
}

private fun part2(input: List<String>): Int {
    return 0
}

private val parseInput = { input: List<String> -> input.first().split(" ").map { it.toInt() } }

fun main() {
    val testInput = parseInput(readInput("Day08_test"))
    check(part1(testInput) == 0)
//    check(part2(testInput) == 0)

    val input = parseInput(readInput("Day08"))
    check(part1(input) == 0) // + 31241
//    check(part2(input) == 0)
}
 