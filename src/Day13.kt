data class Path(val r: Int, val c: Int, val pic: Char)

data class Cart(var r: Int, var c: Int, var direction: Directions) {
    val intersectionSigns = listOf("left", "straight", "right")
    val infiniteSignSequence = generateSequence(0) { (it + 1) % intersectionSigns.size }.map { intersectionSigns[it] }
    val iterator = infiniteSignSequence.iterator()


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

            '+' -> {
                when (iterator.next()) {
                    "left" -> direction = when (direction) {
                        Directions.UP -> Directions.LEFT
                        Directions.RIGHT -> Directions.UP
                        Directions.DOWN -> Directions.RIGHT
                        Directions.LEFT -> Directions.DOWN
                    }

                    "straight" -> direction = direction
                    "right" -> direction = when (direction) {
                        Directions.UP -> Directions.RIGHT
                        Directions.RIGHT -> Directions.DOWN
                        Directions.DOWN -> Directions.LEFT
                        Directions.LEFT -> Directions.UP
                    }
                }
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

fun printGrid(paths: List<Path>, carts: List<Cart> = listOf()) {
    val maxRow = paths.maxOf { it.r }
    val maxCol = paths.maxOf { it.c }
    val grid = Array(maxRow + 1) { CharArray(maxCol + 1) { ' ' } }

    for (p in paths) grid[p.r][p.c] = p.pic

    for (cart in carts) grid[cart.r][cart.c] = when (cart.direction) {
        Directions.UP -> '^'
        Directions.DOWN -> 'v'
        Directions.LEFT -> '<'
        Directions.RIGHT -> '>'
    }

    for (row in grid) println(row.concatToString())
}

private fun part1(carts: List<Cart>, tracks: List<Path>): Pair<Int, Int> {
    while (true) {
        carts.forEach { it.moveCart(tracks) }
        val positionsGrouped = carts.groupingBy { it.r to it.c }.eachCount()

        val r = positionsGrouped.filter { it.value > 1 }
        if (r.isNotEmpty()) {
            val (r, c) = r.keys.first()
            return Pair(c, r)
        }
    }
}

private fun part2(carts: List<Cart>, tracks: List<Path>): Pair<Int, Int> {
    var newCarts = carts.toList()
    while (newCarts.size > 1) {
        newCarts.forEach { it.moveCart(tracks) }
        val positionsGrouped = newCarts.groupingBy { it.r to it.c }.eachCount()
        val r = positionsGrouped.filter { it.value > 1 }
        for (crash in r) {
            val (r, c) = crash.key
            newCarts = newCarts.filter { it.r != r || it.c != c }.toMutableList()
        }
    }
    val (r, c) = newCarts.first()
    return Pair(c, r)
}

private fun getTracks(grid: List<List<Char>>): List<Path> {
    return buildList {
        for (r in grid.indices) {
            for (c in grid[r].indices) {
                add(
                    when (val item = grid[r][c]) {
                        'v', '^' -> Path(r, c, '|')
                        '<', '>' -> Path(r, c, '-')
                        '+' -> Path(r, c, '+')
                        else -> Path(r, c, item)
                    }
                )
            }
        }
    }.filter { it.pic != ' ' }
}

private fun getCarts(
    grid: List<List<Char>>,
): List<Cart> {
    val cartSigns = mutableListOf('<', '>', '^', 'v')
    return buildList {
        for (r in grid.indices) {
            for (c in grid[r].indices) {
                val item = grid[r][c]
                if (cartSigns.contains(item)) {
                    add(
                        Cart(
                            r, c, when (item) {
                                '>' -> Directions.RIGHT
                                '<' -> Directions.LEFT
                                '^' -> Directions.UP
                                'v' -> Directions.DOWN
                                else -> throw Exception("Invalid cart sign: $item")
                            }
                        )
                    )
                }
            }
        }
    }
}


fun main() {
    val testInput = readInputNoTrim("Day13_test").map { it.toList() }
    check(part1(getCarts(testInput), getTracks(testInput)) == Pair(2,0))
    check(part2(getCarts(testInput), getTracks(testInput)) == Pair(6,4))

    val input = readInputNoTrim("Day13").map { it.toList() }
    check(part1(getCarts(input), getTracks(input)) == Pair(91, 69))
    part2(getCarts(input), getTracks(input)).println()
}
 