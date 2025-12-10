package year2025

fun day10() {
    val input = {}.javaClass.getResource("inputFiles/day10/test")?.readText() ?: "?"

    val l1 = input.lines()

    val regexLights = "\\[(.*?)]".toRegex()
    val regexButtons = "\\((.*?)\\)".toRegex()
    val regexVoltages = "\\{(.*?)\\}".toRegex()

    val charGoals = l1.map { regexLights.find(it)?.groupValues?.get(1) ?: "" }.map { it.toList() }
    val buttonsList = l1.map {
        regexButtons.findAll(it)
            .map { it.groupValues[1] }
            .map { content ->
                content.split(",")
                    .map { it.trim().toInt() }
            }
            .toList()
    }
    val voltageGoals = l1.map {
        regexVoltages.find(it)?.groupValues?.get(1)?.split(",")?.map { it.toInt() } ?: emptyList()
    }

    charGoals.forEach { println(it) }
    buttonsList.forEach { println(it) }
    voltageGoals.forEach { println(it) }

    data class Button(
        val aktConfig: List<Char>,
        val instruction: List<Int>,
        val deep: Int,
        val nextButtons: List<Button>,
    )

    fun List<Int>.applyTo(config: List<Char>): List<Char> {
        return config.mapIndexed { index, c ->
            if (index in this) if (c == '#') '.' else '#' else c
        }
    }

    fun List<Int>.applyToVoltage(config: List<Int>): List<Int> {
        return config.mapIndexed { index, i ->
            if (index in this) i + 1 else i
        }
    }

    // Tests
    println("Test")
    println((listOf(0, 2).applyTo(listOf('.', '.', '.', '.'))))
    println((listOf(0, 2).applyTo(listOf(0, 2).applyTo(listOf('.', '.', '.', '.')))))

    println(listOf(0, 2).applyToVoltage(listOf(0, 0, 0, 0)))
    println(listOf(0, 2).applyToVoltage(listOf(0, 2).applyToVoltage(listOf(0, 0, 0, 0))))


    fun getminDeepCharGoals(goal: List<Char>, buttons: List<List<Int>>): Int {
        val initialConfig = List(goal.size) { '.' }
        val initialButtons = buttons.map {
            Button(
                aktConfig = it.applyTo(initialConfig),
                instruction = it,
                deep = 1,
                nextButtons = emptyList(),
            )
        }

        var currentButtons = initialButtons
        var nextButtons = mutableListOf<Button>()
        var minDeep = Int.MAX_VALUE

        while (currentButtons.isNotEmpty()) {
            for (button in currentButtons) {
                if (button.aktConfig == goal) {
                    minDeep = minOf(minDeep, button.deep)
                } else if (button.deep < minDeep) {
                    val newButtons = buttons.map {
                        Button(
                            aktConfig = it.applyTo(button.aktConfig),
                            instruction = it,
                            deep = button.deep + 1,
                            nextButtons = emptyList(),
                        )
                    }
                    nextButtons.addAll(newButtons)
                }
            }
            currentButtons = nextButtons
            nextButtons = mutableListOf()
        }

        return minDeep
    }



    charGoals.forEachIndexed { index, goal ->
        println(
            "Goal $index: $goal = ${
                getminDeepCharGoals(
                    goal,
                    buttonsList[index]
                )
            }"
        )
    }
    charGoals.mapIndexed { index, goal -> getminDeepCharGoals(goal, buttonsList[index]) }.sum()
        .also { println("Sum: $it") }


    val charGoalTest = ".####..#..".map { it }
    val buttonsTest = listOf(
        listOf(6, 7, 9),
        listOf(1, 4, 6, 7, 8),
        listOf(1, 2, 3, 5, 6, 7),
        listOf(4, 5, 7),
        listOf(1, 9),
        listOf(6, 7),
        listOf(0, 1, 2, 4, 5, 7, 9),
        listOf(1, 3, 4, 5, 6, 7, 9),
        listOf(1, 2, 5, 6, 8, 9),
        listOf(0, 2, 4, 5, 6, 8, 9),
        listOf(0, 2, 4, 5, 6, 7, 8, 9),
        listOf(0, 2, 3, 4, 6, 7, 8),
        listOf(0, 1, 2, 3, 4, 7, 8, 9)
    )
    val voltagesTest = listOf(64, 68, 82, 53, 82, 69, 85, 121, 51, 77)

    fun toBinaryMatrix(lists: List<List<Int>>, size: Int): List<List<Int>> =
        lists.map { indices ->
            List(size) { i -> if (i in indices) 1 else 0 }
        }

    val matrix = toBinaryMatrix(buttonsTest, 10)
    matrix.forEach { println(it) }


    // matrix: List<List<Int>>, voltagesTest: List<Int>
    fun solveBruteForce(matrix: List<List<Int>>, voltages: List<Int>, maxPress: Int = 10): List<Int>? {
        val n = matrix.size
        val m = voltages.size

        fun isSolution(x: List<Int>): Boolean {
            val result = List(m) { j ->
                matrix.indices.sumOf { i -> matrix[i][j] * x[i] }
            }
            return result == voltages
        }

        fun search(x: List<Int>, idx: Int): List<Int>? {
            if (idx == n) return if (isSolution(x)) x else null
            for (v in 0..maxPress) {
                val res = search(x + v, idx + 1)
                if (res != null) return res
            }
            return null
        }

        return search(emptyList(), 0)
    }

// Beispielaufruf:
    val solution = solveBruteForce(matrix, voltagesTest)
    println("Lösung: $solution")





}

