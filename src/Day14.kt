private fun part1(input: List<String>): Int {
    val recipes = mutableListOf(3,7)
    var elf1P = 0
    var elf2P = 1

    repeat(20) {
        recipes.forEachIndexed { i, p ->
            val r = when(i) {
                elf1P % recipes.size -> "($p)"
                elf2P % recipes.size -> "[$p]"
                else -> p
            }
            print(" $r ")
        }
        println()


        val elf1V = recipes[elf1P % recipes.size]
        val elf2V = recipes[elf2P % recipes.size]

        val newRecipes = (elf1V + elf2V).toString().toList().map { it.toString().toInt() }
        recipes.addAll(newRecipes)
        elf1P += (elf1V + 1)
        elf2P += (elf2V + 1)
    }
    return 0
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 0)
//    check(part2(testInput) == 0)
     
//    val input = readInput("Day14")
//    check(part1(input) == 0)
//    check(part2(input) == 0)
}
 