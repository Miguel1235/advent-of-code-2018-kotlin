data class Point(var r: Int, var c: Int, var rs: Int, var cs: Int) {
    fun move() {
        r += rs
        c += cs
    }
}

fun visualizePoints(points: List<Point>, iteration: Int) {
    val minR = points.minOf { it.r }
    val maxR = points.maxOf { it.r }
    val minC = points.minOf { it.c }
    val maxC = points.maxOf { it.c }

    val height = maxR - minR + 1
    val width = maxC - minC + 1

    if (height > 20 || width > 80) {
        return
    }

    val grid = Array(height) { CharArray(width) { ' ' } }

    for (point in points) {
        val row = point.r - minR
        val col = point.c - minC
        grid[row][col] = '#'
    }

    println("it: $iteration")
    for (row in grid) {
        println(row.joinToString(""))
    }
}

private fun part1(points: List<Point>): Int {
    repeat(1_000_000) {
        visualizePoints(points, it)
        points.forEach { it.move() }
    }
    return 0
}

private val parseInput = { input: List<String> ->
    val numberRegex = Regex("""-?\d+""")
    input.map {
        val (r, c, rs, cs) = numberRegex.findAll(it).map { it.value.toInt() }.toList()
        Point(c,r, cs, rs)
    }
}

fun main() {
    val testInput = parseInput(readInput("Day10_test"))
    check(part1(testInput) == 0)

    val input = parseInput(readInput("Day10"))
    part1(input)
}
 