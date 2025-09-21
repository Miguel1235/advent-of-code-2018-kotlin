private fun part1(input: List<Int>): Int {
    println(input)
    val (children, metadataQ) = input.take(2)
    println("children $children with metadata $metadataQ")

    val metadata = input.takeLast(metadataQ)
    println("metadata $metadata")

    val stringR = input.subList(2, input.size-metadataQ)
    println("stringR $stringR")
    println(stringR.chunked(stringR.size/children))
    return 0
}

private fun part2(input: List<String>): Int {
    return 0
}

private val parseInput  = { input: List<String> -> input.first().split(" ").map { it.toInt() } }

fun main() {
    val testInput = parseInput(readInput("Day08_test"))
    check(part1(testInput) == 0)
//    check(part2(testInput) == 0)
     
    val input = parseInput(readInput("Day08"))
//    check(part1(input) == 0)
//    check(part2(input) == 0)
}
 