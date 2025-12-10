package year2024

import minus
import plus
import times


fun day12() {

    val s = {}.javaClass.getResource("inputFiles/day12/real")?.readText() ?: "?"
    val matrix = s.lines().map { it.toList() }
    matrix.forEach { println(it) }
    val grid = mutableMapOf<Pair<Int, Int>, Char>()
    for (y in matrix.indices) {
        for (x in matrix[y].indices) {
            grid[Pair(x, y)] = matrix[y][x]
        }
    }

    fun isBorderPoint(point: Pair<Int, Int>) =
        point.second in setOf(0, matrix.size - 1) || point.first in setOf(0, matrix[0].size - 1)

    fun Map<Pair<Int, Int>, Char>.getNeighbours(point: Pair<Int, Int>): Set<Pair<Int, Int>> =
        listOf(Pair(0, 1), Pair(0, -1), Pair(1, 0), Pair(-1, 0)).mapNotNull {
            if (this[point + it] == this[point]) {
                point + it
            } else {
                null
            }
        }.toSet()

    fun Map<Pair<Int, Int>, Char>.expandFromToConnectedComponent(point: Pair<Int, Int>): Set<Pair<Int, Int>> {
        val connectiveComponent = mutableSetOf<Pair<Int, Int>>()
        val cache = mutableSetOf(point)
        while (cache.isNotEmpty()) {
            val nextPoint = cache.first()
            cache.remove(nextPoint)
            connectiveComponent.add(nextPoint)
            cache += this.getNeighbours(nextPoint).filter { it !in connectiveComponent }
        }
        return connectiveComponent
    }

    fun Map<Pair<Int, Int>, Char>.getConnectedComponents(): Set<Set<Pair<Int, Int>>> {
        val notVisited = this.keys.toMutableSet()
        val connectiveComponents: MutableSet<Set<Pair<Int, Int>>> = mutableSetOf()
        while (notVisited.isNotEmpty()) {
            val connectiveComponent = this.expandFromToConnectedComponent(notVisited.first())
            notVisited.removeAll(connectiveComponent)
            connectiveComponents.add(connectiveComponent)
        }
        return connectiveComponents
    }

    println("part 1: ${grid.getConnectedComponents().sumOf { it.sumOf { point -> 4 - grid.getNeighbours(point).size } * it.size }}")

    fun Set<Pair<Int, Int>>.borderStartPoint(): Pair<Int, Int> {
        val min = this.filter { pair -> pair.second == this.minOf { it.second } }
        val min2 = min.first { pair -> pair.first == min.minOf { it.first } }
        return min2
    }

    fun Set<Pair<Int, Int>>.getSidesCount(): Int {

        if (this.size in setOf(1, 2)) return 4

        val directions =
            listOf(Pair(-1, 0), Pair(-1, -1), Pair(0, -1), Pair(1, -1), Pair(1, 0), Pair(1, 1), Pair(0, 1), Pair(-1, 1))
        fun Pair<Int, Int>.turn45Clockwise(times: Int) = directions[(directions.indexOf(this) + times).mod(directions.size)]
        fun Pair<Int, Int>.turn45counterClockwise(times: Int) =
            directions[(directions.indexOf(this) - times).mod(directions.size)]
        fun Pair<Int, Int>.isUpDownLeftRight() = this in setOf(Pair(1, 0), Pair(0, 1), Pair(-1, 0), Pair(0, -1))

        val startPoint = this.borderStartPoint()
        var actualPoint = startPoint
        val startDir = Pair(-1, 0)
        var dir = startDir
        var nextPoint: Pair<Int, Int>
        var sidesCount = 1

        while (true) {
            if (actualPoint + dir.turn45Clockwise(2) in this) {
                nextPoint = actualPoint + dir.turn45Clockwise(2)
                sidesCount++
                dir = dir.turn45Clockwise(2)
            } else if (actualPoint + dir.turn45Clockwise(1) in this) {
                nextPoint = actualPoint + dir.turn45Clockwise(1)
                if(dir.isUpDownLeftRight()) sidesCount++
                dir = dir.turn45Clockwise(1)
            } else if (actualPoint + dir in this) {
                nextPoint = actualPoint + dir
                if (!dir.isUpDownLeftRight()) sidesCount += 2
            } else if (actualPoint + dir.turn45counterClockwise(1) in this) {
                nextPoint = actualPoint + dir.turn45counterClockwise(1)
                if (dir.isUpDownLeftRight()){
                    sidesCount += 2
                }else{
                    sidesCount++
                }
                dir = dir.turn45counterClockwise(1)
            } else if (actualPoint + dir.turn45counterClockwise(2) in this) {
                nextPoint = actualPoint + dir.turn45counterClockwise(2)
                sidesCount += if (dir.isUpDownLeftRight()) { 1 } else { 3 }
                dir = dir.turn45counterClockwise(2)
            } else if (actualPoint + dir.turn45counterClockwise(3) in this) {
                nextPoint = actualPoint + dir.turn45counterClockwise(3)
                sidesCount += if (dir.isUpDownLeftRight()) { 3 } else { 2 }
                dir = dir.turn45counterClockwise(3)
            } else {
                nextPoint = actualPoint + dir.turn45counterClockwise(4)
                sidesCount += if (dir.isUpDownLeftRight()) { 2 } else { 4 }
                dir = dir.turn45counterClockwise(4)
            }
            actualPoint = nextPoint
            if (actualPoint == startPoint) {
                if (dir == startDir) sidesCount--
                break
            }
        }
        return sidesCount
    }

    fun Map<Pair<Int, Int>, Char>.getPrice(component: Set<Pair<Int, Int>>): Int {

        val workMap = mutableMapOf<Pair<Int, Int>, Char>()
        this.forEach {
            if (it.key in component) {
                workMap[it.key] = '#'
            } else {
                workMap[it.key] = '.'
            }
        }

        val components = workMap.getConnectedComponents().filter { it != component }.filter { it.none { point -> isBorderPoint(point) } }
        return (component.getSidesCount() + components.sumOf { it.getSidesCount() }) * component.size
    }

    println("part 2 ${grid.getConnectedComponents().sumOf { grid.getPrice(it) }}")

}


