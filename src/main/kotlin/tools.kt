import java.util.*

// scalar multiplication
operator fun Int.times(other: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this * other.first, this * other.second)

// vector addition
operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> =
    Pair(first + other.first, second + other.second)

// v1 - v2 := v1 + (-1 * v2)
operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>): Pair<Int, Int> =
    Pair(first - other.first, second - other.second)

// complex multiplication
operator fun Pair<Int, Int>.times(other: Pair<Int, Int>): Pair<Int, Int> =
    Pair(first * other.first - second * other.second, first * other.second + second * other.first)

fun getTestInput(day: Any): String {
    return {}.javaClass.getResource("${day.javaClass.packageName}/inputFiles/${day.javaClass.simpleName.lowercase(Locale.getDefault())}/test")
        ?.readText() ?: "?"
}

fun getRealInput(day: Any): String {
    return {}.javaClass.getResource("${day.javaClass.packageName}/inputFiles/${day.javaClass.simpleName.lowercase(Locale.getDefault())}/real")
        ?.readText() ?: "?"
}

fun Map<Pair<Int, Int>, Char>.getNeighbourPoints(point: Pair<Int, Int>) = listOf(
    point + Pair(0, 1),
    point + Pair(0, -1),
    point + Pair(1, 0),
    point + Pair(-1, 0),
    point + Pair(1, 1),
    point + Pair(1, -1),
    point + Pair(-1, 1),
    point + Pair(-1, -1)
).filter { it in keys }

