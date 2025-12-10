package year2024

import kotlin.math.abs

fun day22() {

    val input = ({}.javaClass.getResource("inputFiles/day22/real")?.readText() ?: "?").lines().map { it.toLong() }

    val modulus = 16777216L
    fun mix(secretNumber: Long, other: Long) = secretNumber xor other
    fun prune(secretNumber: Long) = secretNumber.mod(modulus)
    fun step1(secretNumber: Long) = prune(mix(secretNumber, 64 * secretNumber))
    fun step2(secretNumber: Long) = prune(mix(secretNumber, secretNumber / 32L))
    fun step3(secretNumber: Long) = prune(mix(secretNumber, 2048L * secretNumber))
    fun run(max: Int, secretNumber: Long): Long {
        var times = 0
        var result = secretNumber
        while (times++ < max) {
            result = step3(step2(step1(result)))
        }
        return result
    }

    println("part1 ${input.sumOf { run(2000, it) }}")

    var allPossibleChanges = mutableListOf<List<Int>>()
    for (i in -9..9) {
        allPossibleChanges += mutableListOf(i)
    }
    var w = 3
    while (w-- > 0) {
        val newList = mutableListOf<List<Int>>()
        allPossibleChanges.forEach {
            for (i in -9..9) {
                newList.add((it + i).toList())
            }
        }
        allPossibleChanges = newList.toMutableList()
        newList.clear()
    }

    val secretNumbersLists = input.map {
        var max = 2000
        val list = mutableListOf((it % 10).toInt())
        var next = it
        while (max-- > 0) {
            next = run(1, next)
            list += (next % 10).toInt()
        }
        list
    }

    fun findSublistIndex(mainList: List<Int>, sublist: List<Int>): Int {
        if (sublist.isEmpty() || sublist.size > mainList.size) return -1

        for (i in 0..mainList.size - sublist.size) {
            if (mainList.subList(i, i + sublist.size) == sublist) return i
        }
        return -1
    }

    val changesLists = secretNumbersLists.map { it.zipWithNext { a, b -> b - a } }

    println(allPossibleChanges.size)

    var part2 = 0
    allPossibleChanges.forEachIndexed { j, possibleChange ->
        println(j)
        var new = 0
        changesLists.forEachIndexed { index, changes ->
            val i = findSublistIndex(changes, possibleChange)
            if (i != -1) {
                new += secretNumbersLists[index][i + 4]
            }
        }
        if (new > part2) {
            part2 = new
        }
    }

    println("part2 $part2")

}