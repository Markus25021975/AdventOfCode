package year2024

import kotlin.math.abs

fun day22() {

    val input = ({}.javaClass.getResource("inputFiles/day22/test")?.readText() ?: "?").lines().map { it.toInt() }

    var secretNumber = 123L

    val modulus = 16777216L

    fun mix(num: Long) {
        secretNumber = secretNumber xor num
    }

    fun prune() {
        secretNumber = secretNumber.mod(modulus)
    }

    fun step1() {
        val help = secretNumber * 64L
        mix(help)
        prune()
    }

    fun step2() {
        val help = secretNumber / 32L
        mix(help)
        prune()
    }

    fun step3() {
        val help = secretNumber * 2048L
        mix(help)
        prune()
    }

    fun run(max: Int): List<Long> {
        var times = 0
        val secretNumbers = mutableListOf<Long>()
        while (times++ < max) {
            secretNumbers.add(abs(secretNumber) % 10)
            step1()
            step2()
            step3()
        }
        secretNumbers.add(abs(secretNumber) % 10)
        return secretNumbers.toList()
    }

    val secretNumbers = mutableListOf<List<Long>>()

    var result = 0L
    input.forEach {
        secretNumber = 123L // it.toLong()
        secretNumbers.add(run(10))
        result += secretNumber
    }
    println("part1 $result")

    println(secretNumbers)

    var changesList = mutableListOf<MutableList<Int>>()
    for (i in -9..9) {
        changesList += mutableListOf(i)
    }

    var b = 3
    while (b-- > 0) {
        val newList = mutableListOf<MutableList<Int>>()
        changesList.forEach {
            for (i in -9..9) {
                newList.add((it + i).toMutableList())
            }
        }
        changesList = newList.toMutableList()
        newList.clear()
    }

    println(changesList.size)

}