private fun part1(input: List<String>): Int {
    val marginList = List(10) { '.' }
    val initialStateList = marginList + input.first().split(":").drop(1).first().trim().toMutableList() + marginList

    val notesList = input.drop(2)
    val notes = buildMap {
        for(note in notesList) {
            val (current, next) = note.split("=>").map { it.trim() }
            put(current, next)
        }
    }

    println(notes)

    val noteSize = notes.keys.first().length + 1
    var initialState = initialStateList.joinToString("")
    println(initialState)
//    val chunks = initialState.chunked(noteSize)


    for(note in notes) {
//        println(note)
        if(initialState.contains(note.key)) {
            println("found match on note $note!")
            initialState = initialState.replaceFirst(note.key, note.value)
        }
        println(initialState)
    }
    println(initialState)

//    println(chunks)

//    println(initialState)
//    val r = buildList {
//        for(chunk in chunks) {
////            println(chunk)
//            if(chunk in notes.keys) {
////                println("found match!")
//                add(notes.getValue(chunk))
//            } else {
//                add(chunk)
//            }
//        }
//    }
//    println(r.joinToString(""))
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
 