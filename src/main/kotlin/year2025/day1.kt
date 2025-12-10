package year2025

import kotlin.math.abs
import kotlin.math.sign

fun day1() {

    println("day1")

    val input = {}.javaClass.getResource("inputFiles/day1/real")?.readText() ?: "?"

    val l = input.lines().map {
        if (it.first() == 'R') {
            it.drop(1).toInt()
        } else {
            -it.drop(1).toInt()
        }
    }

    println("l $l")

    val accList = l.runningFold(50) { acc, i -> (acc + i).mod(100) }

    println("accList $accList")

    println("day1_1: ${accList.count { it == 0 }}")

    val large = l.map { number -> List(abs(number)) { number.sign } }.flatten()

    println("large $large")

    val accList2 = large.runningFold(50) { acc, i -> (acc + i).mod(100) }

    println("day1_2: ${accList2.count { it == 0 }}")

}

