
private fun part1(input: List<String>): Int {
    val grid = input.map { it.toList() }
    val cartSigns = mutableListOf<Char>('<', '>', '^', 'v')

    data class Path(val r: Int, val c: Int, val pic: Char) {
        override fun toString(): String {
            return pic.toString()
        }
    }


    data class Cart(var r: Int, var c: Int, var direction: Directions) {
        fun moveCart(track: List<Path>) {
            val currentTrack = track.first { it.r == r && it.c == c }
            when (currentTrack.pic) {
                '/' -> direction = when (direction) {
                    Directions.UP -> Directions.RIGHT
                    Directions.RIGHT -> Directions.UP
                    Directions.DOWN -> Directions.LEFT
                    Directions.LEFT -> Directions.DOWN
                }

                '\\' -> direction = when (direction) {
                    Directions.UP -> Directions.LEFT
                    Directions.LEFT -> Directions.UP
                    Directions.DOWN -> Directions.RIGHT
                    Directions.RIGHT -> Directions.DOWN
                }
            }
            when (direction) {
                Directions.UP -> r--
                Directions.DOWN -> r++
                Directions.LEFT -> c--
                Directions.RIGHT -> c++
            }
        }
    }


    val carts = buildList {
        for(r in grid.indices) {
            for(c in grid[r].indices) {
                val item = grid[r][c]
                if(cartSigns.contains(item)) {
                    add(Cart(r,c, when(item) {
                        '>' -> Directions.RIGHT
                        '<' -> Directions.LEFT
                        '^' -> Directions.UP
                        'v' -> Directions.DOWN
                        else -> throw Exception("Invalid cart sign: $item")
                    }))
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
    tracks.prettyPrint()
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
 