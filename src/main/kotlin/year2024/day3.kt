package year2024


fun day3_1() {
    val input = {}.javaClass.getResource("inputFiles/day3/real")?.readText() ?: "?"

    println(input)

    val matcher = "mul\\([0-9]+,[0-9]+\\)".toRegex()

    val l1 = matcher.findAll(input).map{it.value}.toList()



    val l2 = l1.map { it.substringAfter("(").substringBefore(",") }.map { it.toInt() }
    val l3 = l1.map { it.substringAfter(",").substringBefore(")") }.map { it.toInt() }
    val l4 = l2.zip(l3).map{it.first * it.second}.toList().sum()

    println (l1)
    println (l2)
    println (l3)
    println (l4)

}

fun day3_2() {
    val input = {}.javaClass.getResource("inputFiles/day3/real")?.readText() ?: "?"

    println(input.lines())

    val matcher = "mul\\([0-9]+,[0-9]+\\)|(don't)|(do)".toRegex()

    val l1 = matcher.findAll(input).map{it.value}.toList()

    var c = 0

    var b = true
    for ( line in l1 ) {
        when (line) {
            "do" -> {
                b = true
            }
            "don't" -> {
                b = false
            }
            else  -> {
                if (b) {
                    val la = line.substringAfter("(").substringBefore(",").toInt()
                    val le = line.substringAfter(",").substringBefore(")").toInt()
                    c += la * le
                }
            }
        }
    }
    println (c)

}