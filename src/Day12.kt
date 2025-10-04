private fun part1(input: List<String>): Int {
    val initialState = input.first().split(":").drop(1).first().trim()

    val notesList = input.drop(2)
    val notes = buildMap {
        for(note in notesList) {
            val (current, next) = note.split("=>").map { it.trim() }
            put(current, next)
        }
    }

    val noteSize = notes.keys.first().length + 1

    println("start -> $initialState")
    println(initialState.chunked(noteSize))
    return 0
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 0)
    check(part2(testInput) == 0)
     
//    val input = readInput("Day12")
//    check(part1(input) == 0)
//    check(part2(input) == 0)
}
 