package year2024

import java.math.BigDecimal
import kotlin.math.floor

fun day13() {
    val input = {}.javaClass.getResource("inputFiles/day13/real")?.readText() ?: "?"


    val l1 = input.split("\n\n")

    l1.forEach { println(it);println(); }

    val l2 = l1.map { it.split(" ") }.map { it.map { jt -> jt.filter { kt -> kt.isDigit() } } }
        .map { lt -> lt.filter { mt -> mt.isNotEmpty() } }

    println(l2)

    val results = mutableListOf<Long>()

    l2.forEach {
        val a1 = it.first().toLong()
        val a2 = it.drop(1).first().toLong()
        val b1 = it.drop(2).first().toLong()
        val b2 = it.drop(3).first().toLong()
        val r1 = it.drop(4).first().toLong() + 10000000000000L
        val r2 = it.drop(5).first().toLong() + 10000000000000L

        // a1 * x + b1 * y = r1
        // a2 * x + b2 * y = r2

        val det = a1 * b2 - a2 * b1

        if (det.toInt() != 0) {
            val x = (r1 * b2 - r2 * b1).toDouble() / det
            val y = (a1 * r2 - a2 * r1).toDouble() / det

            println("x $x y $y")

            if (floor(x).toLong().toDouble() == x && floor(y).toLong().toDouble() == y &&  x > 0L && y > 0L) {
                val result = 3L * x + y
                results.add(result.toLong())
            }
        }
    }

    println(results)

    var result = 0L
    results.forEach { result += it}

    println(result)
}