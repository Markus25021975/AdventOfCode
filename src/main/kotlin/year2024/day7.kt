package year2024

import java.math.BigInteger

fun day7_1() {
    val input = {}.javaClass.getResource("inputFiles/day7/real")?.readText() ?: "?"

    val l = input.lines().map { it.split((": | ").toRegex()) }

    val results = l.map { it.first().toBigInteger() }
    val operands = l.map { it.drop(1).map { jt -> jt.toBigInteger() } }

    val o = results.zip(operands)

    val p = o.filter { it.first in next1(it.second.first(), it.second.drop(1)) }.map { it.first }.sumOf { it }

    println(p)

}

fun next1(r: BigInteger, l: List<BigInteger>) : List<BigInteger> {
    if (l.isEmpty()) return listOf(r)
    return next1(r + l.first(), l.drop(1)) + next1(r * l.first(), l.drop(1))
}

fun day7_2() {
    val input = {}.javaClass.getResource("inputFiles/day7/real")?.readText() ?: "?"

    val l = input.lines().map { it.split((": | ").toRegex()) }


    val results = l.map { it.first().toBigInteger() }
    val operands = l.map { it.drop(1).map { jt -> jt.toBigInteger() } }

    val o = results.zip(operands)

    val p = o.filter { it.first in next2(it.second.first(), it.second.drop(1)) }.map { it.first }.sumOf { it }

    println(p)

}

fun next2(r: BigInteger, l: List<BigInteger>) : List<BigInteger> {
    if (l.isEmpty()) return listOf(r)
    return next2(r + l.first(), l.drop(1)) +
            next2(r * l.first(), l.drop(1)) + next2( (r.toString() + l.first().toString()).toBigInteger(), l.drop(1))
}


