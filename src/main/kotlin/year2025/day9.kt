package year2025

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun day9() {
    val input = {}.javaClass.getResource("inputFiles/day9/real")?.readText() ?: "?"

    val l1 = input.lines().map { it.split(',').map { it.toInt() } }.map { Pair(it[0], it[1]) }

    data class Line(val start: Pair<Int, Int>, val end: Pair<Int, Int>)

    fun Line.intersectsPerpendicular(other: Line): Boolean {
        fun Line.isVertical() = start.first == end.first
        fun Line.isHorizontal() = start.second == end.second

        val thisVertical = this.isVertical()
        val thisHorizontal = this.isHorizontal()
        val otherVertical = other.isVertical()
        val otherHorizontal = other.isHorizontal()
        if (!((thisVertical && otherHorizontal) || (thisHorizontal && otherVertical))) return false

        val (thisStartX, thisStartY) = this.start
        val (thisEndX, thisEndY) = this.end
        val (otherStartX, otherStartY) = other.start
        val (otherEndX, otherEndY) = other.end

        val thisMinX = minOf(thisStartX, thisEndX)
        val thisMaxX = maxOf(thisStartX, thisEndX)
        val thisMinY = minOf(thisStartY, thisEndY)
        val thisMaxY = maxOf(thisStartY, thisEndY)

        val otherMinX = minOf(otherStartX, otherEndX)
        val otherMaxX = maxOf(otherStartX, otherEndX)
        val otherMinY = minOf(otherStartY, otherEndY)
        val otherMaxY = maxOf(otherStartY, otherEndY)

        return if (thisVertical && otherHorizontal) {
            thisStartX in otherMinX..otherMaxX &&
                    otherStartY in thisMinY..thisMaxY
        } else {
            otherStartX in thisMinX..thisMaxX &&
                    thisStartY in otherMinY..otherMaxY
        }
    }


    data class Area(val p1: Pair<Int, Int>, val p2: Pair<Int, Int>) {
        val xMin = min(p1.first, p2.first)
        val xMax = max(p1.first, p2.first)
        val yMin = min(p1.second, p2.second)
        val yMax = max(p1.second, p2.second)
        val width = (abs(p1.first - p2.first) + 1).toLong()
        val height = (abs(p1.second - p2.second) + 1).toLong()

        fun getSize() = width * height
        fun get4InnerBoundaryLines(): Set<Line> {
            return setOf(
                Line(Pair(xMin + 1, yMin + 1), Pair(xMax - 1, yMin + 1)),
                Line(Pair(xMin + 1, yMax - 1), Pair(xMax - 1, yMax - 1)),
                Line(Pair(xMin + 1, yMin + 1), Pair(xMin + 1, yMax - 1)),
                Line(Pair(xMax - 1, yMin + 1), Pair(xMax - 1, yMax - 1))
            )
        }
    }

    val areas = l1.flatMap { p1 -> l1.map { p2 -> Area(p1, p2) } }.sortedByDescending { it.getSize() }

    println("day 9_1: ${areas.first().getSize()}")

    val boundary = (l1 + l1.first()).zipWithNext { p1, p2 -> Line(p1, p2) }

    println(
        "day 9_2: ${
            areas.first { area ->
                area.get4InnerBoundaryLines()
                    .none { it.let { lineA -> boundary.any { lineB -> lineA.intersectsPerpendicular(lineB) } } }
            }.getSize()
        }"
    )

}



