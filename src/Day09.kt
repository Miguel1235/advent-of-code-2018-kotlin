data class MarbleGame(val players: Int, val lastMarble: Int) {
    private class MarbleNode(val value: Int) {
        var next: MarbleNode = this
        var prev: MarbleNode = this

        fun insertAfter(newNode: MarbleNode): MarbleNode = newNode.also {
            it.next = this.next
            it.prev = this
            this.next.prev = it
            this.next = it
        }

        fun remove(): MarbleNode = this.next.also {
            this.prev.next = this.next
            this.next.prev = this.prev
        }

        fun move(steps: Int): MarbleNode {
            var current = this
            if (steps >= 0) {
                repeat(steps) { current = current.next }
            } else {
                repeat(-steps) { current = current.prev }
            }
            return current
        }
    }

    private companion object {
        const val SPECIAL = 23
        const val BACK = 7
    }

    fun play(): Long {
        val scores = LongArray(players)
        var currentMarble = MarbleNode(0)
        for (marble in 1..lastMarble) {
            val player = (marble - 1) % players
            if (marble % SPECIAL == 0) {
                val marbleToRemove = currentMarble.move(-BACK)
                scores[player] += marble.toLong() + marbleToRemove.value
                currentMarble = marbleToRemove.remove()
            } else {
                currentMarble = currentMarble.move(1).insertAfter(MarbleNode(marble))
            }
        }
        return scores.maxOrNull() ?: 0L
    }
}

private fun part1(input: String, isPart2: Boolean = false): Long {
    val regex = Regex("""(\d+)\s+players; last marble is worth\s+(\d+)""")
    val (playersS, lastS) = requireNotNull(regex.find(input)) { "Invalid input: $input" }.destructured
    val players = playersS.toInt()
    val last = lastS.toInt() * if (isPart2) 100 else 1
    return MarbleGame(players, last).play()
}

fun main() {
    val testInput = readInput("Day09_test").first()
    check(part1(testInput) == 32L)

    val input = readInput("Day09").first()
    check(part1(input) == 428690L)
    check(part1(input, true) == 3628143500L)
}
 