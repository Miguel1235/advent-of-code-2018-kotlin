
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

data class Point(var r: Int, var c: Int, var rs: Int, var cs: Int) {
    fun move() {
        r += rs
        c += cs
    }
}



fun createImageFromPoints(points: List<Point>, outputPath: String) {
    if (points.isEmpty()) return

    val minR = points.minOf { it.r }
    val maxR = points.maxOf { it.r }
    val minC = points.minOf { it.c }
    val maxC = points.maxOf { it.c }

    val width = (maxC - minC + 1)
    val height = (maxR - minR + 1)

    val occupied = points.map { it.r to it.c }.toSet()

    val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    val graphics = image.createGraphics()

    graphics.color = Color.WHITE
    graphics.fillRect(0, 0, image.width, image.height)
    graphics.color = Color.BLACK
    for (r in minR..maxR) {
        for (c in minC..maxC) {
            if (occupied.contains(r to c)) {
                val x = (c - minC)
                val y = (r - minR)
                graphics.fillRect(x, y,1,1)
            }
        }
    }

    graphics.dispose()

    ImageIO.write(image, "PNG", File(outputPath))
    println("Image saved to $outputPath (${image.width}x${image.height})")
}

private fun part1(points: List<Point>): Int {
    repeat(4) { iteration ->
        createImageFromPoints(points, "output$iteration.png")
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
//    val testInput = parseInput(readInput("Day10_test"))
//    check(part1(testInput) == 0)

    val input = parseInput(readInput("Day10"))
    part1(input)
}
 