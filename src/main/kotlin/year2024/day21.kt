package year2024

import minus
import plus


fun day21() {

    val input = ({}.javaClass.getResource("inputFiles/day21/real")?.readText() ?: "?").lines().map { it.toList() }

    val nums = listOf(listOf('7', '8', '9'), listOf('4', '5', '6'), listOf('1', '2', '3'), listOf('X', '0', 'A'))
    val dirs = listOf(listOf('X', '^', 'A'), listOf('<', 'v', '>'))

    val posToNumChar = mutableMapOf<Pair<Int, Int>, Char>()
    val posToArrowChar = mutableMapOf<Pair<Int, Int>, Char>()

    for (y in nums.indices) {
        for (x in nums[y].indices) {
            posToNumChar[Pair(x, y)] = nums[y][x]
        }
    }

    for (y in dirs.indices) {
        for (x in dirs[y].indices) {
            posToArrowChar[Pair(x, y)] = dirs[y][x]
        }
    }

    val dirToChar =
        mapOf(Pair(-1, 0) to '<', Pair(1, 0) to '>', Pair(0, -1) to '^', Pair(0, 1) to 'v', Pair(0, 0) to 'A')

    data class Way(val points: List<Pair<Int, Int>>)

    fun Way?.dirsAsChars() = if (this == null) listOf('A') else this.points.zipWithNext { a, b -> b - a }
        .map { dir -> dirToChar[dir]!! } + listOf('A')

    fun Way.countDirChanges() = this.points.zipWithNext { a, b -> b - a }.zipWithNext { a, b -> a != b }.count { it }

    fun MutableMap<Pair<Int, Int>, Char>.getNeighbours(point: Pair<Int, Int>): Set<Pair<Int, Int>> {
        return listOf(Pair(0, 1), Pair(0, -1), Pair(1, 0), Pair(-1, 0)).mapNotNull {
            if (this[point + it] !in setOf(null, 'X')) {
                point + it
            } else {
                null
            }
        }.toSet()
    }

    val powerMapWays = mutableMapOf<Pair<Char, Char>, Way>()

    fun MutableMap<Pair<Int, Int>, Char>.shortestWays(startChar: Char, endChar: Char): List<Way> {
        if (Pair(startChar, endChar) in powerMapWays) {
            return listOf(powerMapWays[Pair(startChar, endChar)]!!)
        }
        val reversedMap = this.map { (k, v) -> v to k }.toMap()
        val start = reversedMap[startChar]!!
        val end = reversedMap[endChar]!!
        var ways = setOf(Way(listOf(start)))
        val newWays = mutableSetOf<Way>()
        val targetWays = mutableSetOf<Way>()
        while (ways.isNotEmpty()) {
            ways.forEach { way ->
                if (way.points.last() == end) {
                    targetWays.add(way)
                } else {
                    this.getNeighbours(way.points.last()).filter { it !in way.points }.forEach { neighbour ->
                        newWays.add(Way(way.points + neighbour))
                    }
                }
            }
            ways = newWays.toSet()
            newWays.clear()
        }
        return targetWays.filter { way -> way.points.size == targetWays.minBy { it.points.size }.points.size }
    }

    dirs.flatten().filter { it != 'X' }.forEach { start ->
        dirs.flatten().filter { it !in setOf(start, 'X') }.forEach { end ->
            val way = posToArrowChar.shortestWays(start, end).minBy { it.countDirChanges() }
            powerMapWays[Pair(start, end)] = way
        }
    }

    fun MutableMap<Pair<Int, Int>, Char>.crypt(code: List<Char>): List<List<Char>> {
        var char = 'A'
        var ways = mutableSetOf(Way(listOf()))
        val newWays = mutableSetOf<Way>()
        code.forEach { nextChar ->
            ways.forEach { way ->
                this.shortestWays(char, nextChar).forEach {
                    newWays.add(Way(way.points + it.points))
                }
            }
            ways = newWays.toMutableSet()
            newWays.clear()
            char = nextChar
        }
        return ways.map { it.dirsAsChars() }
    }

    fun encrypt(code: List<Char>): List<Char> {
        var pos = Pair(2, 0)
        val result = mutableListOf<Char>()
        code.forEach { char ->
            when (char) {
                '<' -> pos += Pair(-1, 0)
                '>' -> pos += Pair(1, 0)
                '^' -> pos += Pair(0, -1)
                'v' -> pos += Pair(0, 1)
                else -> result += posToArrowChar[pos]!!
            }
        }
        return result
    }

    val first = input.map { posToNumChar.crypt(it) }
    val second = first.map { it.map { jt -> posToArrowChar.crypt(jt) }.flatten() }
    val third = second.map { it.map { jt -> posToArrowChar.crypt(jt) }.flatten() }
    val fourth = third.map { it.map { jt -> posToArrowChar.crypt(jt) }.flatten() }
    val fifth  = fourth.map { it.map { jt -> posToArrowChar.crypt(jt) }.flatten() }
    val numericParts = input.map { it.joinToString("").filter { jt -> jt.isDigit() }.toInt() }
    val lengthOfShortestSequences = third.map { it.minOf { jt -> jt.size } }
    val result = numericParts.zip(lengthOfShortestSequences).sumOf { it.first * it.second }
    println("part1: $result")

    val inputPart2 = fifth.map { encrypt( encrypt(encrypt(encrypt(it.minBy { jt -> jt.size })))) }
    val powerMapCodeLength = mutableMapOf<Pair<List<Char>, Int>, Long>()
    val maxDeep = 25

    println(powerMapWays.filter { it.value.points.size == 3 })
    powerMapWays[Pair('A', 'v')] = Way(listOf(Pair(2, 0), Pair(1, 0), Pair(1, 1)))
    // powerMapWays[Pair('v', 'A')] = Way(listOf(Pair(1, 1), Pair(2, 1), Pair(2, 0)))
    powerMapWays[Pair('>', '^')] = Way(listOf(Pair(2, 1), Pair(1, 1), Pair(1, 0)))

    // 515566662627324
    // 551908341118708
    // 328290281789484
    // 279638326609472

    fun rek(list: List<Char>, deep: Int): Long {
        if (list.isEmpty()) return 0L
        if (list.size == 1) return 0L
        if (deep == maxDeep) return list.size.toLong() - 1L
        if (Pair(list, deep) in powerMapCodeLength) return powerMapCodeLength[Pair(list, deep)]!!
        val res1 = rek(listOf('A') + powerMapWays[Pair(list[0], list[1])].dirsAsChars(), deep + 1)
        val res2 = rek(list.drop(1), deep)
        powerMapCodeLength[Pair(list, deep)] = res1 + res2
        return res1 + res2
    }

    val resultPart2 = numericParts.zip(inputPart2.map { rek(listOf('A') + it, 0) }).sumOf { it.first * it.second }

    println("part2: $resultPart2")

}