package year2024

import minus
import plus
import times

fun day6_1(matrix: List<List<Char>>): Pair<MutableSet<Pair<Int, Int>>, Boolean> {

    val barriers = mutableSetOf<Pair<Int, Int>>()
    var pos = Pair(0, 0)
    var newPos: Pair<Int, Int>
    var dir = Pair(0, -1)
    val visited = mutableSetOf<Pair<Int, Int>>()

    for (y in matrix.indices) {
        for (x in matrix[y].indices) {
            if (matrix[y][x] == '^') {
                pos = Pair(x, y)
            } else if (matrix[y][x] == '#') {
                barriers.add(Pair(x, y))
            }
        }
    }

    var running = true
    var big = 10000

    while (running) {
        newPos = pos + dir
        if (listOf(newPos.first, newPos.second).any { it in listOf(-1, matrix.size) }) {
            running = false
        } else if (newPos in barriers) {
            newPos -= dir
            dir *= Pair(0, 1)
            newPos += dir
            if (newPos in barriers) {
                newPos -= dir
                dir *= Pair(0, 1)
                newPos += dir
            }
        }
        if (running) {
            if (newPos !in visited) visited.add(newPos)
            pos = newPos
        }
        if (big-- < 0) break
    }

    if (big < 0) return Pair(visited, true)
    return Pair(visited, false)
}



fun day6_2() {
    val input = {}.javaClass.getResource("inputFiles/day6/real")?.readText() ?: "?"
    val matrix = input.lines().map { it.toList() }
    val potentialOs = day6_1(matrix).first
    println(potentialOs.size + 1)
    var poss = 0
    for (potentialO in potentialOs) {
        val newMatrix = matrix.map { it.toMutableList() }
        newMatrix[potentialO.second][potentialO.first] = '#'
        if (day6_1(newMatrix).second) poss++
    }
    println(poss)
}