package year2024

fun day4_1() {
    val input = {}.javaClass.getResource("inputFiles/day4/real" +
            "")?.readText() ?: "?"

    val l0 = input.lines()
    val l1 = input.lines().map { it.toList() }

    val matcher1 = "(XMAS)".toRegex()
    val matcher2 = "(SAMX)".toRegex()

    // 0. Index = y - Coordinate
    // 1. Index = x - Coordinate

    val x = l0
    val y = l1.transpose().map { it.joinToString(separator = "") }
    val d1 = l1.diags().map { it.joinToString(separator = "") }
    val d2 = l1.map { it.reversed() }.diags().map { it.joinToString(separator = "") }

    var c = 0
    x.forEach {
        println (it)
        c += matcher1.findAll(it).count()
        println (c)
        c += matcher2.findAll(it).count()
        println (c)
    }
    println()
    y.forEach {
        println (it)
        c += matcher1.findAll(it).count()
        println (c)
        c += matcher2.findAll(it).count()
        println (c)
    }

    println()
    d1.forEach {
        println (it)
        c += matcher1.findAll(it).count()
        println (c)
        c += matcher2.findAll(it).count()
        println (c)
    }
    d2.forEach {
        println (it)
        c += matcher1.findAll(it).count()
        println (c)
        c += matcher2.findAll(it).count()
        println (c)
    }


    println(x)
    println(y)
    println(d1)
    println(d2)

}


fun day4_2() {
    val input = {}.javaClass.getResource("inputFiles/day4/real")?.readText() ?: "?"

    println(input.lines())

    val l0 = input.lines()
    val l = input.lines().map { it.toList() }

    println(l0)
    println(l)

    var c = 0

    for (y in 1..l.size - 2) {
        for ( x in 1..l[y].size - 2) {
            if( l[y][x] == 'A') {
                println("  matched $y $x")

                when {
                    l[y - 1][x - 1] == 'M' && l[y + 1][x + 1] == 'S' && l[y - 1][x + 1] == 'M' && l[y + 1][x - 1] == 'S' -> {
                        println("hit 1")
                        c++
                    }

                    l[y - 1][x - 1] == 'S' && l[y + 1][x + 1] == 'M' && l[y - 1][x + 1] == 'M' && l[y + 1][x - 1] == 'S' -> {
                        println("hit 2")
                        c++
                    }

                    l[y - 1][x - 1] == 'M' && l[y + 1][x + 1] == 'S' && l[y - 1][x + 1] == 'S' && l[y + 1][x - 1] == 'M' -> {
                        println("hit 3")
                        c++
                    }

                    l[y - 1][x - 1] == 'S' && l[y + 1][x + 1] == 'M' && l[y - 1][x + 1] == 'S' && l[y + 1][x - 1] == 'M' -> {
                        println("hit 4")
                        c++
                    }

                }
            }
        }
    }

    println (c)

}

fun <T> List<List<T>>.transpose(): List<List<T>> {
    val transposed = mutableListOf<List<T>>()
    for (i in first().indices) {
        val col: MutableList<T> = ArrayList()
        forEach { row ->
            col.add(row[i])
        }
        transposed.add(col)
    }
    return transposed
}



// ChatGPT
fun <T> List<List<T>>.diags(): List<List<T>> {
    val result = mutableListOf<List<T>>()
    val n = this.size
    if (n == 0) return result

    // Diagonalen von oben links nach unten rechts
    for (d in 0 until n) {
        val diag = mutableListOf<T>()
        var row = d
        var col = 0
        while (row >= 0) {
            diag.add(this[row][col])
            row--
            col++
        }
        result.add(diag)
    }

    // Diagonalen von oben rechts nach unten links
    for (d in 1 until n) {
        val diag = mutableListOf<T>()
        var row = n - 1
        var col = d
        while (col < n) {
            diag.add(this[row][col])
            row--
            col++
        }
        result.add(diag)
    }

    return result
}



