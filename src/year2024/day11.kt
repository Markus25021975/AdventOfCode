package year2024

fun day11(){
    val input = {}.javaClass.getResource("inputFiles/day11/real")?.readText() ?: "?"
    val data = input.split(" ").map { it.toLong() }.toMutableList()

    calc(data, 25)
    calc(data, 75)
}

fun calc(input: MutableList<Long>, max: Int) {

    val map = mutableMapOf<Pair<Long, Int>, Long>()

    fun f(blink: Int, n: Long): Long {
        if (Pair(n, blink) in map) return map[Pair(n, blink)]!!
        if (blink == max) return 1L
        if (n == 0L) {
            val result = f(blink + 1, 1)
            map[Pair(n, blink)] = result
            return result
        }
        val s = n.toString()
        val l = s.length
        if (l.mod(2) == 0) {
            val s1 = s.substring(0, l / 2).toLong()
            val s2 = s.substring(l / 2, l).toLong()
            val result = f(blink + 1, s1) + f(blink + 1, s2)
            map[Pair(n, blink)] = result
            return result
        }
        val result = f(blink + 1, n * 2024)
        map[Pair(n, blink)] = result
        return result
    }

    println(input.sumOf { f(0, it) })

}

