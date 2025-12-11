package year2025

import readInput
import java.math.BigInteger


fun day11() {

    val input = readInput("2025", "day11", "real")

    data class Device(val label: String, val deep: Int, val nextDevices: Set<Device>, val wayToThis: List<String>)

    val labels = input.lines().map { it.split(":")[0] }
    val nextLabels = input.lines().map { it.split(":")[1].split(" ").filter { it.isNotEmpty() } }

    val labelsMap = labels.zip(nextLabels).toMap()

    val initialDevices = labels.filter { it == "you" }.map { you -> Device(you, 0, emptySet(), emptyList()) }.toSet()

    var currentDevices = initialDevices
    var nextDevices = mutableListOf<Device>()
    var count = 0

    while (currentDevices.isNotEmpty()) {
        for (aktDevice in currentDevices) {
            val nextLabels = labelsMap[aktDevice.label]!!
            if (aktDevice.wayToThis.find { it == aktDevice.label } != null) {
                println("loop detected at $aktDevice")
            } else if (nextLabels.contains("out")) {
                println("out detected ${aktDevice.wayToThis + aktDevice.label + "out"}")
                count++
            } else {
                nextLabels.forEach { label ->
                    nextDevices.add(
                        Device(
                            label,
                            aktDevice.deep + 1,
                            emptySet(),
                            aktDevice.wayToThis + aktDevice.label
                        )
                    )
                }
            }
        }
        currentDevices = nextDevices.toSet()
        nextDevices.clear()
    }

    println("day11_1 $count")


    val outsWithoutBoth = mutableMapOf<String, BigInteger>()
    val outsWithoutFft = mutableMapOf<String, BigInteger>()
    val outsWithoutDac = mutableMapOf<String, BigInteger>()
    val outsWithBoth = mutableMapOf<String, BigInteger>()

    fun rek(way: List<String>): BigInteger {
        val nextLabels = labelsMap[way.last()]!!

        if (nextLabels[0] == "out") {
            return if ("fft" in way && "dac" in way) 1.toBigInteger() else 0.toBigInteger()
        }

        val cache = when {
            "fft" in way && "dac" in way -> outsWithoutBoth
            "fft" in way -> outsWithoutFft
            "dac" in way -> outsWithoutDac
            else -> outsWithBoth
        }

        return nextLabels.sumOf { nextLabel ->
            cache[nextLabel] ?: rek(way + nextLabel).also { cache[nextLabel] = it }
        }
    }

    println("day11_2 ${rek(listOf("svr"))}")

}