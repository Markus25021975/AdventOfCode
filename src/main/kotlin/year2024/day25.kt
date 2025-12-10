package year2024


fun day25() {

    val input = ({}.javaClass.getResource("inputFiles/day25/real")?.readText() ?: "?")

    val l = input.split("\\n\\n".toRegex())

    val (keyGridList, lockGridList) = l.map { keyOrLockString ->
        keyOrLockString.lines().map { it.toList() }
            .withIndex().flatMap { (y, row) ->
                row.withIndex().map { (x, char) -> (x to y) to char }
            }.toMap()
    }.partition { grid -> grid.keys.filter { it.second == 0 }.all { grid[it] == '#' } }

    keyGridList.forEach { println(it) }
    println("")
    lockGridList.forEach { println(it) }

    val yMax = 7
    val xMax = 5

    var sum = 0

    keyGridList.forEach { keyGrid ->
        lockGridList.forEach { lockGrid ->
            if ((0..xMax).all { x ->
                    (0..yMax).count { y ->
                        keyGrid[Pair(x, y)] == '#'
                    } + (0..yMax).count { y -> lockGrid[Pair(x, y)] == '#' } <= 7
                }) sum++
        }
    }

    println(sum)


}