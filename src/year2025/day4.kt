package year2025

import plus

fun day4() {
    val input = {}.javaClass.getResource("inputFiles/day4/real")?.readText() ?: "?"

    val matrix = input.lines().map { it.toList() }
    matrix.forEach { println(it) }
    val grid = matrix.withIndex().flatMap { (y, row) ->
        row.withIndex().map { (x, char) -> (x to y) to char }
    }.toMap().toMutableMap()

    val num = grid.filter { point ->
        point.value == '@' && grid.getNeighbourPoints(point.key)
            .count { neighbour -> grid[neighbour] == '@' } < 4
    }.count()

    println("day4_1: $num")

    var running = 0
    while (running < 2000) {
        grid.forEach { point ->
            if (point.value == '@' && grid.getNeighbourPoints(point.key)
                    .count { neighbour -> grid[neighbour] in setOf('@', '#') } < 4
            ) grid[point.key] = '#'
        }
        grid.forEach {
            point -> if (point.value == '#') grid[point.key] = '.'
        }
        running++
    }

    println("day4_2: ${matrix.flatten().count{it == '@'} - grid.filter { it.value == '@' }.size}")

}

fun Map<Pair<Int, Int>, Char>.getNeighbourPoints(point: Pair<Int, Int>): List<Pair<Int, Int>> {
    if (point !in keys) return emptyList()
    return listOf(
        point + Pair(0, 1),
        point + Pair(0, -1),
        point + Pair(1, 0),
        point + Pair(-1, 0),
        point + Pair(1, 1),
        point + Pair(1, -1),
        point + Pair(-1, 1),
        point + Pair(-1, -1)
    ).filter { it in keys }

}