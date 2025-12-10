package year2024

import plus
import times


fun day14() {
    
    val s = {}.javaClass.getResource("inputFiles/day14/real")?.readText() ?: "?"

    val l = s.lines()

    l.forEach { println(it) }

    val l1 = l.map { it.split(" ") }

    println()

    l1.forEach { println(it) }

    println()


    val l2 = l1.map { it.map { jt -> jt.substringAfter('=').split(',') }.flatten().map { kt -> kt.toInt() } }

    l2.forEach { println(it) }

    println()

    val l3 = l2.map { it -> Pair(Pair(it[0], it[1]), Pair(it[2], it[3])) }

    l3.forEach { println(it) }


    val xSize = 101
    val ySize = 103

    var matrix = MutableList(ySize) { MutableList(xSize) { 0 } }

    l3.forEach { matrix[it.first.second][it.first.first]++ }

    matrix.forEach { println(it) }

    val l4 = l3.map { it.first + 100 * it.second }.map { jt -> Pair(jt.first.mod(xSize), jt.second.mod(ySize)) }

    l4.forEach { println(it) }

    matrix = MutableList(ySize) { MutableList(xSize) { 0 } }

    l4.forEach { matrix[it.second][it.first]++ }

    matrix.forEach { println(it) }

    println()

    matrix = matrix.mapIndexed { y, ints ->
        List(ints.size) { x ->
            if (x == xSize / 2 || y == ySize / 2) {
                0
            } else {
                matrix[y][x]
            }
        }.toMutableList()
    }.toMutableList()
    val matrix2 = matrix.mapIndexed { y, ints ->
        List(ints.size) { x ->
            if (x == xSize / 2 || y == ySize / 2) {
                ' '
            } else {
                matrix[y][x]
            }
        }.toMutableList()
    }

    matrix.forEach { println(it) }
    println()
    matrix2.forEach { println(it) }

    var q1 = 0
    var q2 = 0
    var q3 = 0
    var q4 = 0

    matrix2.forEachIndexed { y, ints ->
        ints.forEachIndexed { x, _ ->
            if (y < ySize / 2 && x < xSize / 2) {
                q1 += matrix[y][x]
            } else if (y < ySize / 2 && x > xSize / 2) {
                q2 += matrix[y][x]
            } else if (y > ySize / 2 && x < xSize / 2) {
                q3 += matrix[y][x]
            } else if (y > ySize / 2 && x > xSize / 2) {
                q4 += matrix[y][x]
            }
        }
    }

    println(q1)
    println(q2)
    println(q3)
    println(q4)

    println(q1*q2*q3*q4)

}


