package year2024

import kotlin.math.abs


fun day2_1() {
    val input = {}.javaClass.getResource("inputFiles/day2/real")?.readText() ?: "?"

    println(input.lines())

    val l0 = input.lines().map { it.split(" ") }.filter { jt -> jt.distinct().size == jt.size }

    val l1 = l0.map { it.map { jt -> jt.toInt() } }.filter { kt -> kt in listOf(kt.sorted(), kt.sortedDescending()) }

    val l2 = l1.map { it.zipWithNext() }.map { jt -> jt.map { abs(it.first - it.second) } }

    val l3 = l2.filter { it.all { jt -> jt in listOf(1, 2, 3) } }.size

    println(l0)
    println(l1)
    println(l2)
    println(l3)

}

fun safe(l: List<Int>): Boolean {
    if (l.size == l.distinct().size) {
        if (l in listOf(l.sorted(), l.sortedDescending())) {
            val ll = l.zipWithNext().map { abs(it.first - it.second) }.all { jt -> jt in listOf(1, 2, 3) }
            return ll
        }
    }
    return false
}

fun day2_2() {
    val input = {}.javaClass.getResource("inputFiles/day2/real")?.readText() ?: "?"

    println(input.lines())

    val l0 = input.lines().map { it.split(" ") }
    val l1 = l0.map { it.map { jt -> jt.toInt() } }

    val l2 = l1.map { it ->
        val l = mutableListOf<List<Int>>()
        it.forEachIndexed { index, _ -> l.add(it.filterIndexed { index2, _ -> index != index2 }) }
        // it.forEach { jt -> l.add(it.filter { jt != it }) }
        l
    }

    l2.forEach { it.forEach { jt -> println(safe(jt)) } }

    val l3 = l2.map { it.map { jt -> safe(jt) } }

    val l4 = l3.filter { it.contains(true) }.size




    println(l0)
    println(l1)
    println(l2)

    println(l3)
    println(l4)

}