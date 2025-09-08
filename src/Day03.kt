private fun part1(input: List<String>, isPart2: Boolean = false): Int {
    val grid = MutableList(1_000) { MutableList(1_000) { "." } }
    val overlapIds = mutableSetOf<Int>()
    val ids = mutableListOf<Int>()
    val regex = Regex("""#(\d+) @ (\d+),(\d+): (\d+)x(\d+)""")

    for (area in input) {
        val (id, startC, startR, w, h) = regex.find(area)!!.groupValues.drop(1).map { it.toInt() }
        ids.add(id)
        for (c in startC until startC + w) {
            for (r in startR until startR + h) {
                grid[r][c] = if(grid[r][c] == ".") id.toString() else {
                    overlapIds.add(id)
                    overlapIds.addAll(grid[r][c].split(":").map { it.toInt() })
                    grid[r][c] + ":$id"
                }
            }
        }
    }
    return if(isPart2) (ids-overlapIds)[0] else grid.flatten().count { it.contains(":") }
}

fun main() {
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4)
    check(part1(testInput, true) == 3)

    val input = readInput("Day03")
    check(part1(input) == 111485)
    check(part1(input, true) == 113)
}
 