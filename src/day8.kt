fun day8_1() {
    val input = {}.javaClass.getResource("inputFiles/day8/real")?.readText() ?: "?"

    val matrix = input.lines().map { it.toList() }
    val antis = mutableSetOf<Pair<Int, Int>>()
    val antennas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

    println(matrix.size)

    for (y in matrix.indices) {
        for (x in matrix.indices) {
            if (matrix[y][x] == '#') {
                antis.add(Pair(x, y))
            } else if (matrix[y][x] != '.') {
                if (matrix[y][x] in antennas) {
                    antennas[matrix[y][x]]?.add(Pair(x, y))
                } else {
                    antennas[matrix[y][x]] = mutableListOf(Pair(x, y))
                }
            }
        }
    }

    for (antenna in antennas) {
        for (pos1 in antenna.value) {
            for (pos2 in antenna.value) {
                if (pos2 != pos1) {
                    antis.add(pos1 - pos2 + pos1) // comment out for part2
                    antis.add(pos2 - pos1 + pos2) // comment out for part2
                    // comment in the next 4 lines for part 2
//                    for (s in -200..200) {
//                        antis.add(s * (pos1 - pos2) + pos1)
//                        antis.add(s * (pos2 - pos1) + pos2)
//                    }
                }
            }
        }
    }

    val antis2 = antis.filter { listOf(it.first, it.second).all { jt -> jt in matrix.indices.toList() } }

    println(antis2.size)
}

operator fun Int.times(other: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this * other.first, this * other.second)


