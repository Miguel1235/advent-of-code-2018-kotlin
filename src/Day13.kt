private fun part1(input: List<String>): Int {
    val grid = input.map { it.toList() }
    val cartSigns = mutableListOf<Char>('<', '>', '^', 'v')

    data class Path(val r: Int, val c: Int, val pic: Char)

    val carts = buildList {
        for(r in grid.indices) {
            for(c in grid[r].indices) {
                val item = grid[r][c]
                if(cartSigns.contains(item)) {
                    add(Path(r,c, item))
                }
            }
        }
    }

    val tracks = buildList {
        for(r in grid.indices) {
            for(c in grid[r].indices) {
                add(when (val item = grid[r][c]) {
                    'v', '^' -> Path(r,c, grid[r-1][c])
                    '<', '>' -> Path(r,c, grid[r][c-1])
                    else -> Path(r,c, item)
                })
            }
        }
    }.filter { it.pic != ' ' }

    println(tracks)
    println(carts)

    return 0
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 0)
    check(part2(testInput) == 0)
     
//    val input = readInput("Day13")
//    check(part1(input) == 0)
//    check(part2(input) == 0)
}
 