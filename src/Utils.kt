import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

/**
 * Represents the directions on a grid
 */
enum class Directions(val rowDelta: Int, val colDelta: Int) {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1)
}

fun <T> List<T>.combinations(k: Int): List<List<T>> {
    val results = mutableListOf<List<T>>()

    fun backtrack(start: Int, current: MutableList<T>) {
        if (current.size == k) {
            results.add(current.toList())
            return
        }
        for (i in start..this.lastIndex) {
            current.add(this[i])
            backtrack(i + 1, current)
            current.removeLast()
        }
    }
    backtrack(0, mutableListOf())
    return results
}

fun <T> List<T>.permutations(): List<List<T>> {
    if (size <= 1) return listOf(this)

    val result = mutableListOf<List<T>>()
    for (i in indices) {
        val element = this[i]
        val remaining = this.toMutableList().apply { removeAt(i) }
        val perms = remaining.permutations()
        result.addAll(perms.map { listOf(element) + it })
    }
    return result
}

fun <T> List<T>.prettyPrint() {
    this.forEach { println(it) }
    println()
}

private fun <T> prettyGridPint(grid: List<List<T>>) {
    for (r in grid.indices) {
        for (c in grid[0].indices) {
            print(grid[r][c])
        }
        println()
    }
}

fun Char.offsetLetter(offset: Int): Char {
    val lower = this.lowercaseChar()
    if(lower == '-') return ' '

    val baseP = lower - 'a'
    val newP = (baseP + offset) % 26

    val wrappedPosition = if (newP < 0) newP + 26 else newP
    return 'a' + wrappedPosition
}

fun <T> List<List<T>>.transpose(): MutableList<MutableList<T>> {
    if (this.isEmpty()) return mutableListOf()

    val rows = this.size
    val cols = this[0].size

    return MutableList(cols) { col ->
        MutableList(rows) { row ->
            this[row][col]
        }
    }
}