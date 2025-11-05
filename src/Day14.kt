private fun part1(prevRecipes: Int): String {
    val recipes = mutableListOf(3,7)
    var elf1P = 0
    var elf2P = 1

    val recipes2Take = 10

    while(recipes.size < prevRecipes + recipes2Take) {
        val elf1V = recipes[elf1P % recipes.size]
        val elf2V = recipes[elf2P % recipes.size]

        val newRecipes = (elf1V + elf2V).toString().toList().map { it.toString().toInt() }
        recipes.addAll(newRecipes)

        elf1P = (elf1P + elf1V + 1) % recipes.size
        elf2P = (elf2P + elf2V + 1) % recipes.size
    }
    return recipes.takeLast(recipes2Take).joinToString("")
}

private fun part2(prevRecipes: String): String {
    val recipes = mutableListOf(3,7)
    var elf1P = 0
    var elf2P = 1

    while(!recipes.joinToString("").contains(prevRecipes)) {
        val elf1V = recipes[elf1P % recipes.size]
        val elf2V = recipes[elf2P % recipes.size]

        val newRecipes = (elf1V + elf2V).toString().toList().map { it.toString().toInt() }
        recipes.addAll(newRecipes)

        elf1P = (elf1P + elf1V + 1) % recipes.size
        elf2P = (elf2P + elf2V + 1) % recipes.size
    }
    println(recipes.count() - prevRecipes.count())
    return ""
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val testInput = readInput("Day14").first().toInt()
    check(part1(testInput) == "8176111038")
}
 