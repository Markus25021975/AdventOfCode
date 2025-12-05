package year2025

fun day5() {

    val input = {}.javaClass.getResource("inputFiles/day5/real")?.readText() ?: "?"

    val ranges =
        input.split("\n\n").first().lines().map { it.split('-').map { it.toLong() } }.map { it.first()..it.last() }
    val ids = input.split("\n\n").last().lines().map { it.toLong() }

    println("day5_1 ${ids.count { id -> ranges.any { range -> id in range } }}")

    println("day5_2 ${unOverlapRanges(ranges).sumOf { (it.last - it.first + 1) }}")

}

fun unOverlapRanges(ranges: List<LongRange>): List<LongRange> {

    val sorted = ranges.sortedBy { it.first }

    var start = sorted.first().first
    var end = sorted.first().last

    val result = mutableListOf<LongRange>()

    sorted.drop(1).forEach { nextRange ->
        if (nextRange.first <= end + 1) {
            end = maxOf(end, nextRange.last)
        } else {
            result.add(start..end)
            start = nextRange.first
            end = nextRange.last
        }
    }
    result.add(start..end)
    return result

}
