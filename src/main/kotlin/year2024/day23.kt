package year2024


fun day23() {

    val input =
        ({}.javaClass.getResource("2025/inputFiles/day23/real")?.readText() ?: "?").lines().map { it.split("-").toSet() }
            .toSet()

    val threeConnected = mutableSetOf<Set<String>>()

    input.forEach {
        val one = (input - setOf(it)).filter { jt -> (it intersect jt).size == 1 }
        one.forEach { jt ->
            (one - setOf(jt)).forEach { kt ->
                val inter = jt intersect kt
                if (inter.size == 1 && (inter intersect it).isEmpty()) {
                    threeConnected.add(it union inter)
                }
            }
        }
    }

    println("part1 ${threeConnected.filter { it.any { string -> string.startsWith("t") } }.size}")

    val nodes = input.flatten().toSet()

    fun String.isNeighbour(other: String) = input.any { it == setOf(this, other) }

    var old = threeConnected.toMutableSet()
    val new = mutableSetOf<Set<String>>()
    // takes some hours
    while (true) {
        old.forEachIndexed { index, connected ->
            println("$index ${old.size}")
            nodes.forEach { node ->
                if (connected.all { it.isNeighbour(node) }) new.add(connected + node)
            }
        }
        if (new.isNotEmpty()) {
            old = new.toMutableSet()
            new.clear()
        } else {
            break
        }
    }

    println("part2 ${old.flatten().sorted().joinToString(",")}")

}