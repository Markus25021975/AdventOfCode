package year2025

import com.google.ortools.Loader
import com.google.ortools.linearsolver.MPSolver
import readInput
import kotlin.text.toInt

fun day10() {

    val input = readInput("2025", "day10", "real")

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

    charGoals.mapIndexed { index, goal -> getminDeepCharGoals(goal, buttonsList[index]) }.sum()
        .also { println("day10_1: $it") }


    fun toBinaryMatrix(lists: List<List<Int>>, size: Int): List<List<Int>> {
        val matrix = Array(size) { MutableList(lists.size) { 0 } }
        lists.forEachIndexed { col, indices ->
            indices.forEach { row ->
                matrix[row][col] = 1
            }
        }
        return matrix.map { it.toList() }
    }

    fun solveWithORTools(matrix: List<List<Int>>, voltages: List<Int>, maxPress: Int = 1000): List<Int>? {
        Loader.loadNativeLibraries()
        val numRows = matrix.size
        val numCols = if (matrix.isNotEmpty()) matrix[0].size else 0
        val solver = MPSolver.createSolver("SCIP") ?: return null

        // x: Anzahl der Tastendrücke pro Button (entspricht Spaltenanzahl)
        val x = Array(numCols) { solver.makeIntVar(0.0, maxPress.toDouble(), "x$it") }

        // Jede Zeile (Licht) muss die Zielspannung erreichen
        for (row in 0 until numRows) {
            val ct = solver.makeConstraint(voltages[row].toDouble(), voltages[row].toDouble())
            for (col in 0 until numCols) {
                ct.setCoefficient(x[col], matrix[row][col].toDouble())
            }
        }

        val objective = solver.objective()
        for (i in 0 until numCols) {
            objective.setCoefficient(x[i], 1.0)
        }
        objective.setMinimization()

        val resultStatus = solver.solve()
        return if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
            x.map { it.solutionValue().toInt() }
        } else {
            null
        }
    }


    println(
        "day10_2: ${
            voltageGoals.mapIndexed { idx, voltage ->
                solveWithORTools(
                    toBinaryMatrix(buttonsList[idx], voltage.size),
                    voltages = voltage,
                    maxPress = 1000
                )
            }.sumOf { it?.sum() ?: 0 }
        }"
    )


}

