private fun part1(tasks: MutableMap<Char, MutableList<Char>>): Int {
    sort(tasks)
    return 0
}

private fun parseInput(input: List<String>): MutableMap<Char, MutableList<Char>> {
    val tasks = mutableMapOf<Char, MutableList<Char>>()
    for (dep in input) {
        val (a, b) = dep.filter { it.isUpperCase() }.drop(1).toList()
        tasks.merge(b, mutableListOf(a)) { old, new -> old.apply { addAll(new) } }
    }
    return tasks
}


private fun sort(graph: MutableMap<Char, MutableList<Char>>) {
    val result = mutableListOf<Char>()

    fun visit(node: Char) {
        if (node in result) return
        val deps = graph[node] ?: emptyList()
        for (dep in deps.sorted()) {
            visit(dep)
        }
        result += node
    }
    for (node in graph.keys.sorted()) {
        visit(node)
    }
    println(result.joinToString(""))
}

fun main() {
    val testInput = parseInput(readInput("Day07_test"))
    check(part1(testInput) == 0) // CABDFE

    val input = parseInput(readInput("Day07"))
    check(part1(input) == 0) // GKWBYARVMZDUCETFPSIXQJLHNO  GKWBYAZDRVMUCETFSXPQIJLHNO
}
 