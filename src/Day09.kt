data class MarbleGame(val players: Int, val lastMarble: Int) {
    private class MarbleNode(val value: Int) {
        var next: MarbleNode = this
        var prev: MarbleNode = this

        fun insertAfter(newNode: MarbleNode) {
            newNode.next = this.next
            newNode.prev = this
            this.next.prev = newNode
            this.next = newNode
        }

        fun remove(): MarbleNode {
            this.prev.next = this.next
            this.next.prev = this.prev
            return this.next
        }

        fun moveClockwise(steps: Int): MarbleNode {
            var current = this
            repeat(steps) { current = current.next }
            return current
        }

        fun moveCounterClockwise(steps: Int): MarbleNode {
            var current = this
            repeat(steps) { current = current.prev }
            return current
        }
    }

    fun play(): Long {
        val scores = LongArray(players)
        var currentMarble = MarbleNode(0)
        var currentPlayer = 0
        for (marble in 1..lastMarble) {
            if (marble % 23 == 0) {
                scores[currentPlayer] += marble.toLong()
                val marbleToRemove = currentMarble.moveCounterClockwise(7)
                scores[currentPlayer] += marbleToRemove.value.toLong()
                currentMarble = marbleToRemove.remove()
            } else {
                val insertPosition = currentMarble.moveClockwise(1)
                val newMarble = MarbleNode(marble)
                insertPosition.insertAfter(newMarble)
                currentMarble = newMarble
            }
            currentPlayer = (currentPlayer + 1) % players
        }
        return scores.maxOrNull() ?: 0L
    }
}

private fun part1(input: String, isPart2: Boolean = false): Long {
    val lineRegex = Regex("""(\d+) players; last marble is worth (\d+)""")
    val (players, lastMarble) = lineRegex.find(input)!!.groupValues.drop(1).map { it.toInt() }
    return MarbleGame(players, if (!isPart2) lastMarble else lastMarble * 100).play()
}

fun main() {
    val testInput = readInput("Day09_test").first()
    check(part1(testInput) == 32L)

    val input = readInput("Day09").first()
    check(part1(input) == 428690L)
    check(part1(input, true) == 3628143500L)

}
 