package year2025

import readInput

fun day7() {
    val input = readInput("2025", "day7", "real")

    val matrix = input.lines().toMutableList().map { it.toMutableList() }
    val height = matrix.size
    val width = matrix[0].size
    var splits = 0

    val m2trix= MutableList(height) { MutableList(width) { 0.toBigInteger() } }

    for (y in 1 until height) {
        for (x in 0 until width) {
            when {
                matrix[y - 1][x] == 'S' -> {
                    matrix[y][x] = '|'
                    m2trix[y][x] += 1.toBigInteger()
                }

                matrix[y - 1][x] == '|' && matrix[y][x] == '^' -> {
                    matrix[y][x + 1] = '|'
                    m2trix[y][x + 1] += m2trix[y - 1][x]
                    matrix[y][x - 1] = '|'
                    m2trix[y][x - 1] += m2trix[y - 1][x]
                    splits++
                }

                matrix[y - 1][x] == '|' && matrix[y][x] in setOf('.', '|') -> {
                    matrix[y][x] = '|'
                    m2trix[y][x] += m2trix[y - 1][x]
                }
            }
        }
    }

    println("day7_1:  $splits")
    println("day7_2: ${m2trix.last().sumOf { it }}")

}









