package year2024

fun day5_1() {
    val input = {}.javaClass.getResource("inputFiles/day5/real")?.readText() ?: "?"
    val input1 = {}.javaClass.getResource("inputFiles/day5/real1")?.readText() ?: "?"


    val rules = input.lines().map { it.split('|').map(String::toInt) }

    val numsLists = input1.lines().map { it.split(',').map(String::toInt) }

    println(rules)
    println(numsLists)

    var sum = 0

    val incorrect = mutableListOf<MutableList<Int>>()

    numsLists.forEach { numList ->
        var b = true
        numList.forEachIndexed { index0, num0 ->
            rules.forEach { rule ->
                if ( rule[0] == num0 ) {
                    numList.forEachIndexed { index1, num1 ->
                        if ( rule[1] == num1 && index1 < index0 ) {
                            b = false
                        }
                    }
                }
            }
        }
        if (b) {
            sum += numList[(numList.size - 1) / 2]
        } else {
            incorrect.add(numList.toMutableList())
        }
    }

    println (sum)

    println (incorrect)

    val myComparator = Comparator<Int>{
        o1: Int, o2: Int ->

        for (rule in rules) {
            if( rule[0] == o1  && rule[1] == o2) {
                return@Comparator -1
            }  else if( rule[0] == o1  && rule[1] == o2  ) {
                return@Comparator 1
            }
        }
        return@Comparator 0
    }

    val correct = mutableListOf<MutableList<Int>>()

    incorrect.forEach { correct.add(it.sortedWith(myComparator).toMutableList()) }

    println (correct)

    val t = correct.sumOf { it.filterIndexed { index, _ -> index == (it.size - 1) / 2 }.sum() }

    println (t)

}


fun day5_2() {
    val input = {}.javaClass.getResource("inputFiles/day5/real")?.readText() ?: "?"

    println(input.lines())

}