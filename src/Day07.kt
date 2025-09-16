private fun part1(tasks: MutableMap<Char, MutableList<Char>>): Int {
    visitDependenciesInOrder(tasks).println()
    return 0
}

private fun parseInput(input: List<String>): MutableMap<Char, MutableList<Char>> {
    val tasks = mutableMapOf<Char, MutableList<Char>>()
    for (dep in input) {
        val (a, b) = dep.filter { it.isUpperCase() }.drop(1).toList()
        tasks.merge(a, mutableListOf(b)) { old, new -> old.apply { addAll(new) } }
    }
    return tasks
}


private fun visitDependenciesInOrder(dependencyTree: MutableMap<Char, MutableList<Char>>): String {
    val visited = mutableSetOf<Char>()
    val result = mutableListOf<Char>()
    val allNodes = (dependencyTree.keys + dependencyTree.values.flatten()).toSet().sorted()

    fun dfs(node: Char) {
        if (node in visited) return
        visited.add(node)
        dependencyTree[node]?.sorted()?.forEach { dependency ->
            dfs(dependency)
        }
        result.add(node)
    }

    allNodes.forEach { node ->
        if (node !in visited) {
            dfs(node)
        }
    }
    return result.joinToString("")
}

fun main() {
    val testInput = parseInput(readInput("Day07_test"))
    check(part1(testInput) == 0) // CABDFE

    val input = parseInput(readInput("Day07"))
    check(part1(input) == 0) // GKWBYARVMZDUCETFPSIXQJLHNO  GKWBYAZDRVMUCETFSXPQIJLHNO WBGKYARVMZTDEPUCSXQIJFLHNO
}
 