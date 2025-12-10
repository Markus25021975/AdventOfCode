package year2025

import getNeighbourPoints
import readInput


fun day4() {
    val input = readInput("2025", "day4", "real")
    println(input)
    val matrix = input.lines().map { it.toList() }
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



