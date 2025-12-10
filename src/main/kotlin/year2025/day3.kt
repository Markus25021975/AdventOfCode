package year2025

import readInput


fun getString(minIdx: Int, maxIdx: Int, l: List<Int>): String {
    val max = l.filterIndexed { idx, _ -> idx in minIdx..maxIdx }.maxOf { it }
    val firstIdx = l.withIndex().indexOfFirst { it.value == max && it.index in minIdx..maxIdx }
    if (maxIdx == l.withIndex().last().index) return max.toString()
    return max.toString() + getString(firstIdx + 1, maxIdx + 1, l)
}

fun day3() {
    val input = readInput("2025", "day3", "real")
    val l = input.lines().map { it.map { char -> char.digitToInt() } }
    println("day3_1 ${l.sumOf { getString(0, it.lastIndex - 1, it).toLong() }}")
    println("day3_1 ${l.sumOf { getString(0, it.lastIndex - 11, it).toLong() }}")
}


