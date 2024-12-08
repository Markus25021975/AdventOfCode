package year2024

fun day1_1() {
    val input = {}.javaClass.getResource("inputFiles/day1/real")?.readText() ?: "?"

    println(input.lines())

    val l0 = input.lines()
    val l1 = l0.map { it.substringBefore(" ").toInt() }.sorted()
    val l2 = l0.map { it.substringAfter("   ").toInt() }.sorted()
    println (l1)
    println (l2)
    val l3 = l1.zip(l2).sumOf { Math.abs(it.first - it.second) }
    println (l3)

}


fun day1_2() {
    val input = {}.javaClass.getResource("inputFiles/day1/real")?.readText() ?: "?"

    println(input.lines())

    val l0 = input.lines()
    val l1 = l0.map { it.substringBefore(" ").toInt() }.sorted()
    val l2 = l0.map { it.substringAfter("   ").toInt() }.sorted()
    println (l1)
    println (l2)

    val l3 = l1.sumOf { it * l2.count { jt -> it == jt } }

    println (l3)



}