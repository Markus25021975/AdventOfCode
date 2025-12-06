package year2025

fun day6() {

    val input = {}.javaClass.getResource("inputFiles/day6/test")?.readText() ?: "?"

    println(input)

    val l1 = input.lines()

    println("l1 $l1")

    val ops = l1.last().split(" ").filter { it.isNotBlank()}

    val l2 = l1.map { it.split(" ") }.map{it.filter { jt -> jt.toIntOrNull() != null }}.dropLast(1)

    println("l2 $l2")
    println(ops)

    val columnIndices = l2.first().indices

    val l3 = columnIndices.map { columnIndex -> l2.map { row -> row[columnIndex] }}.map { it.map { it.toLong() }}

    println("l3 $l3")

    var result = 0L

    ops.forEachIndexed { idx, op ->

        if(op == "+"){
            result += l3[idx].sumOf { it }
        }
        if(op == "*"){
            result += l3[idx].map{ it }.reduce { acc, el -> acc * el }
        }
    }

    println("day6_1 $result")



    val l4 = l1.dropLast(1)

    println("l4 $l4")

    val columnIndices2 = l4.first().indices

    println("columnIndices2 $columnIndices2")

    val l5 = columnIndices2.map { columnIndex -> l4.map { row -> row[columnIndex] }}

    println("l5 $l5")

    val l6 = l5.map { it.filter { jt -> jt != ' ' } }

    println("l6 $l6")

    val l7 = l6.map { it.joinToString("")}

    println("l7 $l7")

    val l8 = mutableListOf<List<Int>>()

    var currentGroup = mutableListOf<Int>()
    l7.forEachIndexed { idx, num ->
        if (num.isNotBlank()) {
            currentGroup.add(num.toInt() )
        }else{
            l8.add(currentGroup)
            currentGroup = mutableListOf<Int>()
        }
        if(idx == l7.lastIndex){
            l8.add(currentGroup)
        }
    }

    println("l8 $l8")

    var res = 0.toBigInteger()

    ops.forEachIndexed { idx, op ->

        if(op == "+"){
            res += l8[idx].sumOf { it.toBigInteger() }
        }
        if(op == "*"){
            res += l8[idx].map{ it.toBigInteger() }.reduce { acc, el -> acc * el }
        }
    }

    println("day6_2 $res")

}

// 56319733957 too low