package year2024

import plus
import times


fun day15() {

    val input = {}.javaClass.getResource("inputFiles/day15/real")?.readText() ?: "?"
    val l1 = input.split(regex = "\\s\\s".toRegex())
    val matrix = l1.first().lines().map { it.toMutableList() }.toMutableList()
    val moves = l1.last().lines().joinToString("").toList()

    val max = matrix.size - 1
    var pos = Pair(0, 0)
    val grid = mutableMapOf<Pair<Int, Int>, Char>()
    for (y in matrix.indices) {
        for (x in matrix[y].indices) {
            grid[Pair(x, y)] = matrix[y][x]
            if (matrix[y][x] == '@') pos = Pair(x, y)
        }
    }

    fun Char.dir() = when (this) {
        '^' -> Pair(0, -1)
        '>' -> Pair(1, 0)
        'v' -> Pair(0, 1)
        else -> {
            Pair(-1, 0)
        }
    }

    fun can(dir: Pair<Int, Int>) =
        (0..max).map { grid[pos + it * dir] }.dropWhile { jt -> jt !in setOf('#', '.') }.first() == '.'

    fun move(dir: Pair<Int, Int>) {
        var actual = '.'
        var next: Char? = null
        var lokPos = pos
        pos += dir
        while (next != '.') {
            next = grid[lokPos]
            grid[lokPos] = actual
            lokPos += dir
            actual = next!!
        }
    }

    moves.forEach {
        if (can(it.dir())) {
            move(it.dir())
        }
    }

    var result = 0
    grid.forEach { (t, u) -> if (u == 'O') result += t.first + 100 * t.second }

    println("part1: $result")

    val matrix2 = mutableListOf<MutableList<Char>>()
    for (y in matrix.indices) {
        val line = mutableListOf<Char>()
        for (x in matrix[y].indices) {
            if (matrix[y][x] == '#') {
                line += '#'
                line += '#'
            } else if (matrix[y][x] == 'O') {
                line += '['
                line += ']'
            } else if (matrix[y][x] == '@') {
                line += '@'
                line += '.'
            } else {
                line += '.'
                line += '.'
            }
        }
        matrix2.add(line)
    }

    var pos2 = Pair(0, 0)
    val grid2 = mutableMapOf<Pair<Int, Int>, Char>()
    for (y in matrix2.indices) {
        for (x in matrix2[y].indices) {
            grid2[Pair(x, y)] = matrix2[y][x]
            if (matrix2[y][x] == '@') pos2 = Pair(x, y)
        }
    }

    fun Pair<Int, Int>.canMove(dir: Pair<Int, Int>): Boolean {
        if (dir in setOf(Pair(1, 0), Pair(-1, 0))) {
            return when {
                grid2[this + dir] == '.' -> true
                grid2[this + dir] == '#' -> false
                else -> (this + dir).canMove(dir)
            }
        }
        return when {
            grid2[this + dir] == '.' -> true
            grid2[this + dir] == '#' -> false
            grid2[this + dir] == '[' -> {
                (this + dir).canMove(dir) && (this + dir + Pair(1, 0)).canMove(dir)
            }
            else -> { // grid2[this + dir] == ']'
                (this + dir).canMove(dir) && (this + dir + Pair(-1, 0)).canMove(dir)
            }
        }
    }

    fun Pair<Int, Int>.move2(dir: Pair<Int, Int>) {
        if (dir in setOf(Pair(1, 0), Pair(-1, 0))) {
            if (grid2[this + dir] == '.') {
                grid2[this + dir] = grid2[this]!!
                grid2[this] = '.'
            } else if(grid2[this + dir] in setOf('[', ']')) {
                (this + dir).move2(dir)
                grid2[this + dir] = grid2[this]!!
                grid2[this] = '.'
            }
        } else {
            if (grid2[this + dir] == '.') {
                grid2[this + dir] = grid2[this]!!
                grid2[this] = '.'
            } else if (grid2[this + dir] == '[') {
                (this + dir).move2(dir)
                (this + dir + Pair(1, 0)).move2(dir)
                grid2[this + dir] = grid2[this]!!
                grid2[this] = '.'
            } else if (grid2[this + dir] == ']') {
                (this + dir).move2(dir)
                (this + dir + Pair(-1, 0)).move2(dir)
                grid2[this + dir] = grid2[this]!!
                grid2[this] = '.'
            }
        }
    }

    moves.forEach {
        if (pos2.canMove(it.dir())) {
            pos2.move2(it.dir())
            pos2 += it.dir()
        }
    }

    result = 0
    grid2.forEach { (t, u) -> if (u == '[') result += t.first + 100 * t.second }

    println("part2: $result")

}
