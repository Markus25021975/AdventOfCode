package year2024

import getRealInput
import getTestInput

object Day9 {

    fun part1() {

        val input = getRealInput(this).toList()

        val fromLeft = mutableListOf<String>()

        input.forEachIndexed { index, c ->
            fromLeft += if (index.mod(2) == 0) {
                List(c.digitToInt()) { (index / 2).toString() }
            } else {
                List(c.digitToInt()) { "." }
            }
        }

        val fileSize = fromLeft.filter { it != "." }.size
        val fromRight = fromLeft.toMutableList()
        val lNew = mutableListOf<String>()

        while (lNew.size < fileSize) {
            if (fromLeft.first() != ".") {
                lNew += fromLeft.first()
                fromLeft.removeFirst()
            } else {
                if (fromRight.last() != ".") {
                    lNew += fromRight.last()
                    fromRight.removeLast()
                    fromLeft.removeFirst()
                } else {
                    fromRight.removeLast()
                }
            }
        }

        var result = 0L
        lNew.forEachIndexed { index, c -> result += index.toLong() * c.toLong() }
        println(result)
    }

    fun part2() {

        val input = getRealInput(this).toList()

        data class Chunk(val size: Int, val value: String, val free: Boolean)

        operator fun Chunk.minus(other: Chunk): List<Chunk> {
            return if (this.size > other.size) {
                listOf(Chunk(other.size, other.value, false), Chunk(size - other.size, value, true))
            } else {
                listOf(Chunk(other.size, other.value, false))
            }
        }

        fun freeChunk(chunk: Chunk) = Chunk(chunk.size, ".", true)

        var lOld = mutableListOf<Chunk>()

        input.forEachIndexed { index, c ->
            lOld += if (index.mod(2) == 0) {
                Chunk(c.toString().toInt(), (index / 2).toString(), false)
            } else {
                Chunk(c.toString().toInt(), ".", true)
            }
        }

        var lNew: List<Chunk>
        val tail = mutableListOf<Chunk>()

        while (lOld.isNotEmpty()) {
            val chunk = lOld.removeLast()
            if (chunk.free) {
                tail.add(chunk)
            } else {
                val i = lOld.indexOfFirst { it.free && it.size >= chunk.size }
                if (i != -1) {
                    lNew = lOld.subList(0, i) + (lOld[i] - chunk) + lOld.subList(i + 1, lOld.size)
                    lOld = lNew.toMutableList()
                    tail.add(freeChunk(chunk))
                } else {
                    tail.add(chunk)
                }
            }
        }

        var result = 0L
        tail.reversed().map { List(it.size) { _ -> it.value } }.flatten().forEachIndexed { index, c ->
            result += index.toLong() * if (c != ".") {
                c.toLong()
            } else {
                0L
            }
        }
        println(result)

    }
}