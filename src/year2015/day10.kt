package year2015


fun day10() {
    val input = {}.javaClass.getResource("inputFiles/day10/real")?.readText() ?: "?"
    var currentSequence = input.map { it.digitToInt() }
    repeat(50) {
        currentSequence = generateNextSequence(currentSequence)
    }
    println(currentSequence.size)
}

private fun generateNextSequence(sequence: List<Int>): List<Int> {
    if (sequence.isEmpty()) return emptyList()
    val nextSequence = mutableListOf<Int>()
    var currentDigit = sequence[0]
    var runLength = 1
    for (i in 1 until sequence.size) {
        if (sequence[i] == currentDigit) {
            runLength++
        } else {
            nextSequence.add(runLength)
            nextSequence.add(currentDigit)
            currentDigit = sequence[i]
            runLength = 1
        }
    }
    nextSequence.add(runLength)
    nextSequence.add(currentDigit)
    return nextSequence
}