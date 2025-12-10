package year2025

import readInput

fun day8() {
    val input = readInput("2025", "day8", "real")

    println(input)

    fun dist(p1: Triple<Long, Long, Long>, p2: Triple<Long, Long, Long>): Long =
        (p1.first - p2.first) * (p1.first - p2.first) + (p1.second - p2.second) * (p1.second - p2.second) + (p1.third - p2.third) * (p1.third - p2.third)

    val points = input.lines().map { it.split(",").map { it.toLong() }.let { Triple(it[0], it[1], it[2]) } }

    val sortedPairList = points.flatMapIndexed { index, p1 ->
        points.drop(index + 1).map { p2 -> Pair(p1, p2) }
    }.sortedBy { dist(it.first, it.second) }

    val circuits: MutableList<MutableSet<Triple<Long, Long, Long>>> =
        mutableListOf()

    for (i in 0 until 1000000) {
        val pair = sortedPairList[i]

        if (circuits.none { it.contains(pair.first) || it.contains(pair.second) }) {
            circuits.add(mutableSetOf(pair.first, pair.second))
        } else {
            val newSet = circuits.filter { it.contains(pair.first) || it.contains(pair.second) }
            circuits.removeAll(newSet)

            newSet.forEach {
                it.add(pair.first)
                it.add(pair.second)
            }

            circuits.add(newSet.flatten().toMutableSet())
        }

        if (i == 1000) println(
            "day81_1  ${
                circuits.sortedByDescending { it.size }.take(3).map { it.size }.reduce { acc, i -> acc * i }
            }"
        )

        if (circuits.first().size == 1000) {
            println("day81_2 ${pair.first.first * pair.second.first}")
            break
        }
    }

}