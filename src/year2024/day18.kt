package year2024

import plus

fun day18() {

    val input = {}.javaClass.getResource("inputFiles/day18/real")?.readText() ?: "?"
    val l1 = input.split(regex = "\\n".toRegex()).map { it.split(',').map(String::toInt) }
    val l2 = l1.map { Pair(it.first(), it.last()) }

    val max = 70
    var bytes = 1024
    val grid = mutableMapOf<Pair<Int, Int>, Char>()
    val startPos = Pair(0, 0)
    val endPos = Pair(max, max)
    val visited = mutableSetOf<Pair<Int, Int>>()
    var ways = mutableSetOf((mutableListOf(startPos)))
    val newWays = mutableSetOf<MutableList<Pair<Int, Int>>>()

    for (y in 0..max) {
        for (x in 0..max) {
            grid[Pair(x, y)] = if (l2.take(bytes).contains(Pair(x, y))) '#' else '.'
        }
    }

    fun getNeighbours(point: Pair<Int, Int>) =
        listOf(Pair(0, 1), Pair(0, -1), Pair(1, 0), Pair(-1, 0)).mapNotNull {
            if (grid[point + it] == '.') {
                point + it
            } else {
                null
            }
        }.toSet()

    fun run(): Int {
        var big = 1000
        while (ways.none { it.contains(endPos) } && big-- > 0) {
            ways.forEach { way ->
                val newHeads = getNeighbours(way.last()).filter {
                    it !in visited
                }
                visited.addAll(newHeads)
                newHeads.forEach { newHead ->
                    newWays.add((way + newHead).toMutableList())
                }
            }
            ways = newWays.toMutableSet()
            newWays.clear()
        }
        return big
    }

    println("run() ${run()}")
    println("part1: ${ways.filter { it.contains(endPos) }.map { jt -> jt.size - 1 }.min()}")

    while (true) {
        ways = mutableSetOf((mutableListOf(startPos)))
        newWays.clear()
        visited.clear()
        grid[l2[bytes]] = '#'
        if (run() <= 0) break
        bytes++
    }

    println("bytes $bytes")
    println("part2: ${l2[bytes]}")

}