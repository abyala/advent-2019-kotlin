package com.abyala.advent.day2

class Day2 {
    fun part1(input: List<Int>): Int = runProgram(input.withNounAndVerb(12, 2))[0]

    fun part2(input: List<Int>, output: Int): Int {
        for (noun in 0..99) {
            for (verb in 0..99) {
                try {
                    val found = runProgram(input.withNounAndVerb(noun, verb))[0]
                    if (output == found) {
                        return 100 * noun + verb
                    }
                } catch (e: Exception) {
                    // Try again
                }
            }
        }

        throw Exception("No solution found!")
    }

    private fun List<Int>.withNounAndVerb(noun: Int, verb: Int) = toMutableList().apply {
        this[1] = noun
        this[2] = verb
    }

    fun runProgram(input: List<Int>): List<Int> = runProgram(input.toMutableList())

    @JvmName("runProgramMutable")
    fun runProgram(input: MutableList<Int>): List<Int> {
        for (pos in 0..input.size step 4) {
            val command = input[pos]

            when (command) {
                99 -> return input.toList()
                1 -> input[input[pos + 3]] = input[input[pos + 1]] + input[input[pos + 2]]
                2 -> input[input[pos + 3]] = input[input[pos + 1]] * input[input[pos + 2]]
                else -> throw Exception("Unknown OpCode $command at index $pos")
            }
        }
        throw Exception("Should have aborted by now...")
    }

}