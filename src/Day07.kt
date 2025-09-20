private fun part1(tasks: MutableMap<Char, MutableList<Char>>): String {
    return topoSortFlat(tasks)
}

private fun parseInput(input: List<String>): MutableMap<Char, MutableList<Char>> {
    return input.fold(mutableMapOf<Char, MutableList<Char>>()) { acc, dep ->
        val (a, b) = dep.asSequence()
            .filter { it.isUpperCase() }
            .drop(1)
            .take(2)
            .toList()
        acc.getOrPut(a) { mutableListOf() }.add(b)
        acc
    }
}

fun topoSortFlat(tree: Map<Char, List<Char>>): String {
    val allNodes = tree.keys + tree.values.flatten()
    val indegree = allNodes.associateWith { 0 }.toMutableMap()
    for (deps in tree.values) {
        for (d in deps) {
            indegree[d] = indegree.getValue(d) + 1
        }
    }

    val result = StringBuilder()
    val available = java.util.PriorityQueue<Char>()
    indegree.filterValues { it == 0 }.keys.forEach { available.add(it) }

    while (available.isNotEmpty()) {
        val node = available.poll()
        result.append(node)

        for (dep in tree[node].orEmpty()) {
            indegree[dep] = indegree.getValue(dep) - 1
            if (indegree[dep] == 0) available.add(dep)
        }
    }

    return result.toString()
}

fun main() {
    val testInput = parseInput(readInput("Day07_test"))
    check(part1(testInput) == "CABDFE")

    val input = parseInput(readInput("Day07"))
    check(part1(input) == "GKRVWBESYAMZDPTIUCFXQJLHNO")
}
 