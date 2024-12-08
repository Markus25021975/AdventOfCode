package year2024

import minus
import plus
import times

fun day8() {
    val input = {}.javaClass.getResource("inputFiles/day8/test")?.readText() ?: "?"

    val matrix = input.lines().map { it.toList() }
    val antisPart1 = mutableSetOf<Pair<Int, Int>>()
    // val antisPart1 = matrix.mapIndexed { indexY, chars -> chars.mapIndexedNotNull { indexX, c -> if (c == '#') { Pair(indexX, indexY) } else { null } } }.flatten().toMutableSet()
    val antennas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

    for (y in matrix.indices) {
        for (x in matrix.indices) {
            if (matrix[y][x] == '#') {
                antisPart1.add(Pair(x, y))
            } else if (matrix[y][x] != '.') {
                if (matrix[y][x] in antennas) {
                    antennas[matrix[y][x]]?.add(Pair(x, y))
                } else {
                    antennas[matrix[y][x]] = mutableListOf(Pair(x, y))
                }
            }
        }
    }

    val antisPart2 = antisPart1.toMutableSet()

    for (antenna in antennas) {
        for (pos1 in antenna.value) {
            for (pos2 in antenna.value) {
                if (pos2 != pos1) {
                    antisPart1.add(2 * pos1 - pos2)
                    antisPart1.add(2 * pos2 - pos1)
                    for (s in -200..200) {
                        antisPart2.add(s * (pos1 - pos2) + pos1)
                        antisPart2.add(s * (pos2 - pos1) + pos2)
                    }
                }
            }
        }
    }

    println(antisPart1.filter { listOf(it.first, it.second).all { jt -> jt in matrix.indices.toList() } }.size)
    println(antisPart2.filter { listOf(it.first, it.second).all { jt -> jt in matrix.indices.toList() } }.size)

}




