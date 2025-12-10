package year2024

fun day19() {

    val input = {}.javaClass.getResource("inputFiles/day19/real")?.readText() ?: "?"
    val l1 = input.split(regex = "\\n".toRegex())

    val patterns = l1.first().split(", ")
    val designs = l1.drop(2)
    val cache = mutableMapOf<String, Long>()

    fun f(design: String): Long {
        if (cache.containsKey(design)) return cache[design]!!
        if (design == "") return 1L
        val matches = patterns.filter { design.startsWith(it) }
        if (matches.isEmpty()) return 0L
        var sum = 0L
        matches.forEach { match ->
            val subDesign = design.drop(match.length)
            val result = f(subDesign)
            cache[subDesign] = result
            sum += result
        }
        return sum
    }

    println("part2 ${designs.sumOf { (f(it)) }}")
    println("part1 ${designs.map { f(it) > 0 }.filter { it }.size}")

}