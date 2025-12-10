package year2024

import minus
import plus


fun day16() {

    val input = {}.javaClass.getResource("inputFiles/day16/real")?.readText() ?: "?"
    val l1 = input.lines()
    val matrix = l1.map { it.toList() }

    var posS = Pair(0, 0)
    var posE = Pair(0, 0)

    val grid = mutableMapOf<Pair<Int, Int>, Char>()
    val priceGrid = mutableMapOf<Pair<Int, Int>, Int>()
    for (y in matrix.indices) {
        for (x in matrix[y].indices) {
            grid[Pair(x, y)] = matrix[y][x]
            priceGrid[Pair(x, y)] = -1
            if (matrix[y][x] == 'S') posS = Pair(x, y)
            if (matrix[y][x] == 'E') posE = Pair(x, y)
        }
    }

    fun getNeighbours(point: Pair<Int, Int>) =
        listOf(Pair(0, 1), Pair(0, -1), Pair(1, 0), Pair(-1, 0)).mapNotNull {
            if (grid[point + it] != '#') {
                point + it
            } else {
                null
            }
        }.toSet()

    data class Way(val points: MutableList<Pair<Int, Int>>, var price: Int = 0)

    var ways = mutableSetOf(Way(mutableListOf(posS)))
    val targetWays: MutableSet<Way> = mutableSetOf()

    var b = 1111

    while (--b > 0) {
        val cache: MutableSet<Way> = mutableSetOf()
        ways.forEach { way ->
            val head = way.points.last()
            val dir = if (way.points.size > 1) {
                head - way.points.dropLast(1).last()
            } else {
                Pair(1, 0)
            }
            if (way.points.contains(posE)) {
                targetWays.add(way)
            } else {
                val newHeads = getNeighbours(head)
                newHeads.forEach { newHead ->
                    val price = if (newHead - head == dir) {
                        1
                    } else {
                        1001
                    }
                    if (priceGrid[newHead] == -1 || price + way.price <= priceGrid[newHead]!!) {
                        priceGrid[newHead] = way.price + price
                        cache.add(Way((way.points + newHead).toMutableList(), way.price + price))
                    }
                }
            }
        }
        ways = cache.toMutableSet()
    }

    println("part1:  ${targetWays.map { it.price }.minOf { it }}")

    println("part2: ${targetWays.filter { it.price == 88416 }.map { it.points.toSet() }.flatten().toSet().size}")

}