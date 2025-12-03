package year2025

fun day3() {
    val input = {}.javaClass.getResource("inputFiles/day3/real")?.readText() ?: "?"
    val l = input.lines().map {
        it.mapIndexed { idx, char -> Pair(char.digitToInt(), idx) }
    }
    println("day3_1 ${l.sumOf { getString(0, it.lastIndex - 1, it).toLong() }}")
    println("day3_1 ${l.sumOf { getString(0, it.lastIndex - 11, it).toLong() }}")
}

fun getString(minIdx: Int, maxIdx: Int, l: List<Pair<Int, Int>>): String {
    val max = l.filter { it.second in minIdx..maxIdx }.maxOf { it.first }
    val firstIdx = l.first { it.first == max && it.second in minIdx..maxIdx }.second
    if (maxIdx == l.last().second) return max.toString()
    return max.toString() + getString(firstIdx + 1, maxIdx + 1, l)
}
