package year2024

import plus
import kotlin.math.abs

fun day20() {

    val input = {}.javaClass.getResource("inputFiles/day20/real")?.readText() ?: "?"
    val l1 = input.lines()

    val grid = mutableMapOf<Pair<Int, Int>, Char>()
    var posS: Pair<Int, Int> = Pair(-1, -1)
    var posE: Pair<Int, Int> = Pair(-1, -1)

    for (y in l1.indices) {
        for (x in l1[0].indices) {
            grid[Pair(x, y)] = l1[y][x]
            if (grid[Pair(x, y)] == 'S') posS = Pair(x, y)
            if (grid[Pair(x, y)] == 'E') posE = Pair(x, y)
        }
    }

    data class Way(val points: List<Pair<Int, Int>> = listOf(posS))

    fun Pair<Int, Int>.getNeighbours(): Set<Pair<Int, Int>> {
        return listOf(Pair(0, 1), Pair(0, -1), Pair(1, 0), Pair(-1, 0)).mapNotNull {
            if (grid[this + it] in setOf('.', 'E')) {
                this + it
            } else {
                null
            }
        }.toSet()
    }

    var referenceWay: Way? = null

    tailrec fun run(way: Way) {
        way.points.last().getNeighbours().filter { it !in way.points }.forEach { neighbour ->
            if (neighbour == posE) {
                referenceWay = Way(way.points + neighbour)
            } else {
                return run(Way(way.points + neighbour))
            }
        }
    }

    run(Way())

    fun Pair<Int, Int>.getCheatedNeighbours(): Set<Pair<Int, Int>> {
        return listOf(Pair(0, 1), Pair(0, -1), Pair(1, 0), Pair(-1, 0)).mapNotNull {
            if (grid[this + it] == '#' && this + it + it in referenceWay!!.points) {
                this + it + it
            } else {
                null
            }
        }.toSet()
    }

    var c = 0

    referenceWay!!.points.forEach { point ->
        point.getCheatedNeighbours().forEach { neighbour ->
            if (referenceWay!!.points.indexOf(neighbour) > 101 + referenceWay!!.points.indexOf(point)) c++
        }
    }

    println("part1: $c")

    fun Pair<Int, Int>.dist(point: Pair<Int, Int>) = abs(this.first - point.first) + abs(this.second - point.second)

    var d = 0

    referenceWay!!.points.forEach { point1 ->
        referenceWay!!.points.forEach { point2 ->
            if (point1.dist(point2) < 21 && referenceWay!!.points.indexOf(point2) - referenceWay!!.points.indexOf(point1) - point1.dist(
                    point2
                ) >= 100
            ) d++
        }
    }

    println("part2: $d")

}