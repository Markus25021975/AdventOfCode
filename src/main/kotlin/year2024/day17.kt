package year2024


fun day17() {

    val input = ({}.javaClass.getResource("inputFiles/day17/real")?.readText() ?: "?").split(regex = "\\s\\s".toRegex())

    val l1 = input.first().split(regex = "\\n".toRegex())

    var regA = l1.first().filter { it.isDigit() }.toLong()
    var regB = l1.drop(1).first().filter { it.isDigit() }.toLong()
    var regC = l1.drop(2).first().filter { it.isDigit() }.toLong()
    val prg = input.last().filter { it.isDigit() }.toList().map { it.digitToInt().toLong() }

    var pointer = 0L
    val out = mutableListOf<Long>()

    fun pow(baseVal: Long, exponentVal: Long): Long {
        var exponent = exponentVal
        var result = 1L
        while (exponent != 0L) {
            result *= baseVal
            --exponent
        }
        return result
    }

    fun Long.combo(): Long = when (this) {
        0L -> 0L
        1L -> 1L
        2L -> 2L
        3L -> 3L
        4L -> regA
        5L -> regB
        6L -> regC
        else -> {
            42L
        }
    }

    fun Long.instruct(operand: Long) {
        when (this) {
            0L -> regA /= pow(2, operand.combo())
            1L -> regB = regB xor operand
            2L -> regB = operand.combo().mod(8L)
            3L -> if (regA != 0L) pointer = operand
            4L -> regB = regB xor regC
            5L -> out += operand.combo().mod(8L)
            6L -> regB = regA / pow(2L, operand.combo())
            7L -> regC = regA / pow(2L, operand.combo())
            else -> {}
        }
    }

    fun runPrg(){
        var regAIsZeroTimes = 0
        while (regAIsZeroTimes < 2) {
            val opcode = prg[pointer.toInt()]
            opcode.instruct(prg[pointer.toInt() + 1])
            if (opcode != 3L) pointer += 2
            if(regA == 0L) regAIsZeroTimes++
        }
    }

    runPrg()

    println("part1:  ${out.joinToString(",") { it.toString() }}")

    var cache = mutableSetOf(0L)
    val newCache = mutableSetOf<Long>()

    (1..prg.size).forEach { iteration ->
        cache.forEach { fromLastIt ->
            (0L..7L).forEach {
                val candidate = fromLastIt * 8L + it
                regA = candidate
                runPrg()
                if (out == prg.takeLast(iteration)) {
                    newCache.add(candidate)
                }
                out.clear()
            }
        }
        cache = newCache.toMutableSet()
        newCache.clear()
    }

    println("part2:  ${cache.min()}")

}







