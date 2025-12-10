package year2025

import readInput

fun String.isInvalid(): Boolean {
    if (length % 2 == 1) return false
    val midIndex = length / 2
    val firstHalf = substring(0, midIndex)
    val secondHalf = substring(midIndex)
    return firstHalf == secondHalf
}

fun String.isInvalid2() = (this + this).drop(1).dropLast(1).contains(this)

fun day2() {

    println("day2")

    val input = readInput("2025", "day2", "real")

    println(input)

    val l1 = input.split(',').map { it.split('-')}.map { it[0].toLong()..it[1].toLong() }

    println(l1)

    val l2 = l1.map { it.filter { num -> num.toString().isInvalid() } }.flatten()

    println("day2_1: ${l2.sum()}")

    val l3 = l1.map { it.filter { num -> num.toString().isInvalid2() } }.flatten()

    println("day2_2: ${l3.sum()}")

}

