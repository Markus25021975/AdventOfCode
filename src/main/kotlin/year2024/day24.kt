package year2024

import java.util.*

enum class Op {
    AND,
    OR,
    XOR
}

fun day24() {

    val input =
        ({}.javaClass.getResource("inputFiles/day24/real")?.readText() ?: "?")

    val (l1, l2) = input.split(regex = "\\s\\s".toRegex()).map { it.lines() }
    fun String.toOp() = Op.valueOf(this.uppercase(Locale.getDefault()))
    fun eval(in1: Int?, in2: Int?, op: Op): Int? {
        if (in1 == null || in2 == null) return null
        return when (op) {
            Op.AND -> in1 and in2
            Op.XOR -> in1 xor in2
            Op.OR -> in1 or in2
        }
    }

    var xWires: MutableList<Int?> =
        l1.filter { it.startsWith("x") }.map { it.split(": ").last().toInt() }.toMutableList()
    val yWires: MutableList<Int?> =
        l1.filter { it.startsWith("y") }.map { it.split(": ").last().toInt() }.toMutableList()
    val zWires: MutableList<Int?> = List(xWires.size + 1) { null }.toMutableList()
    var rWires = l2.map { Pair<String, Int?>(it.split("-> ").last(), null) }.toMutableSet()

    data class Gate(val in1: String, val in2: String, val op: Op, val out: String)

    var gates: List<Gate>? = l2.map { it.split(" ") }.map {
        Gate(in1 = it[0], in2 = it[2], op = it[1].toOp(), out = it[4])
    }

    fun List<Gate>.sort(): List<Gate>? {
        val sortedGates = mutableListOf<Gate>()
        var oldSize = 0
        while (true) {
            this.filter { it !in sortedGates }.forEach { gate ->
                if (gate.in1.first() in setOf('x', 'y') && gate.in2.first() in setOf('x', 'y')) {
                    sortedGates.add(gate)
                } else if (sortedGates.any { it.out == gate.in1 } && sortedGates.any { it.out == gate.in2 }) {
                    sortedGates.add(gate)
                }
            }

            if (gates!!.size == sortedGates.size) break
            if (oldSize == sortedGates.size) { // cyclic
                return null
            }
            oldSize = sortedGates.size
        }
        return sortedGates.toList()
    }

    gates = gates!!.sort()

    fun String.inputValue(): Int? {
        if (this.startsWith("x")) return xWires[this.drop(1).toInt()]
        if (this.startsWith("y")) return yWires[this.drop(1).toInt()]
        return rWires.first { it.first == this }.second
    }

    fun String.setOutputValue(value: Int?) {
        if (this.startsWith("z")) {
            zWires[this.drop(1).toInt()] = value
        } else {
            rWires.remove(rWires.first { it.first == this })
            rWires.add(Pair(this, value))
        }
    }

    fun run() {
        gates!!.forEach { gate ->
            gate.out.setOutputValue(eval(gate.in1.inputValue(), gate.in2.inputValue(), gate.op))
        }

    }

    run()

    println("part1 ${zWires.joinToString("").reversed().toLong(2)}")
    println("      57344080719736")

    val originalGates = gates!!.toList()

    fun reset() {
        xWires.replaceAll { 0 }
        yWires.replaceAll { 0 }
        zWires.replaceAll { null }
        rWires = rWires.map { Pair(it.first, null) }.toMutableSet()
    }

    reset()

    println(xWires)
    println(yWires)
    println(zWires)
    println(rWires)


    fun List<Gate>.swapOutput(idx1: Int, idx2: Int): List<Gate> {
        val result = this.toMutableList()
        val tmp = this[idx1].out
        result[idx1] = Gate(result[idx1].in1, result[idx1].in2, result[idx1].op, result[idx2].out)
        result[idx2] = Gate(result[idx2].in1, result[idx2].in2, result[idx2].op, tmp)
        return result.toList()
    }

    for (k in 0..<xWires.size) {
        xWires =
            (1L shl k).toString(2).padStart(xWires.size, '0').toList().map {
                it.digitToInt()
            }.reversed().toMutableList()
        val inX = "0" + xWires.reversed().joinToString("")
        run()
        val outZ = zWires.joinToString("").reversed()
        println(inX)
        println(outZ)
        println()
        reset()
    }

    /*
    val found = mutableListOf<Pair<Int, Int>>()

    for (i in 0..45) {
        for (j in i + 1..45) {
            gates = gates!!.swapOutput(i, j)
            gates = gates.sort()
            if (gates == null) {
                gates = originalGates.toList()
                println("i, j $i $j")
                continue
            }
            var countDiff = 0
            for (k in 0..<xWires.size) {
                xWires =
                    (1L shl k).toString(2).padStart(xWires.size, '0').toList().map {
                        it.digitToInt()
                    }.reversed().toMutableList()
                val inX = "0" + xWires.reversed().joinToString("")
                run()
                val outZ = zWires.joinToString("").reversed()
                if (inX != outZ) {
                    countDiff++

                }
                reset()
            }

           if (countDiff < 4) found.add(Pair(i, j))
            gates = originalGates.toList()
        }
    }

    println(found)

     */


}