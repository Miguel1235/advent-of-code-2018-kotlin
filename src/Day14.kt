private fun part1(input: List<String>): Int {
    val recipes = mutableListOf(3,7)
    var elf1P = 0
    var elf2P = 1

    val prevRecipes = 890691
    val recipes2Take = 10

    while(recipes.size < prevRecipes + recipes2Take) {
        val elf1V = recipes[elf1P % recipes.size]
        val elf2V = recipes[elf2P % recipes.size]

        val newRecipes = (elf1V + elf2V).toString().toList().map { it.toString().toInt() }
        recipes.addAll(newRecipes)

        elf1P = (elf1P + elf1V + 1) % recipes.size
        elf2P = (elf2P + elf2V + 1) % recipes.size
    }
    println(recipes.takeLast(recipes2Take).joinToString(""))
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
 