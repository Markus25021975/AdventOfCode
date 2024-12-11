package year2024

import getRealInput
import getTestInput
import plus

object Day10 {

    fun part1() {

        val input = getRealInput(this).lines().map { it.toList().map { jt -> jt.digitToInt() } }

        var old = mutableListOf<List<Pair<Int, Int>>>()

        for (y in input.indices) {
            for (x in input[y].indices) {
                if (input[y][x] == 0) old.add(listOf(Pair(x, y)))
            }
        }

        // var old = input.flatMapIndexed { y, line -> line.mapIndexedNotNull { x, _ -> if (input[y][x] == 0) listOf(Pair(x, y)) else null }}.toMutableList()

        fun getNeighbours(p: Pair<Int, Int>) =
            setOf(Pair(-1, 0), Pair(1, 0), Pair(0, 1), Pair(0, -1)).mapNotNull { dir ->
                if (listOf((p + dir).first, (p + dir).second).all { it in input.indices }) {
                    p + dir
                } else {
                    null
                }
            }.toSet()

        fun Pair<Int, Int>.isOneHigher(other: Pair<Int, Int>) =
            input[this.second][this.first] == 1 + input[other.second][other.first]

        fun nextHeads(head: Pair<Int, Int>) =
            getNeighbours(head).filter { neighbour -> neighbour.isOneHigher(head) }.toSet()

        val new = mutableListOf<List<Pair<Int, Int>>>()

        while (true) {
            old.forEach { heads ->
                new.add(heads.map { head -> nextHeads(head) }.flatten())
            }
            old = new.toMutableList()
            if (old.first().any { input[it.second][it.first] == 9 }) break
            new.clear()
        }

        println(old.map { it.distinct() }.sumOf { it.size })
        println(old.sumOf { it.size })

    }
}