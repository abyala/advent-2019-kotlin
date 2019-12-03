package com.abyala.advent.day2

import com.abyala.advent.readSingleLineOfInts
import org.junit.Test
import kotlin.test.assertEquals

class Day2Test {
    private val dataFile = "src/test/resources/day2.txt"

    @Test
    fun `Problem 1 - samples`() {
        assertEquals(listOf(2, 0, 0, 0, 99), Day2().runProgram(listOf(1, 0, 0, 0, 99)))
        assertEquals(listOf(2, 3, 0, 6, 99), Day2().runProgram(listOf(2, 3, 0, 3, 99)))
        assertEquals(listOf(2, 4, 4, 5, 99, 9801), Day2().runProgram(listOf(2, 4, 4, 5, 99, 0)))
        assertEquals(listOf(30, 1, 1, 4, 2, 5, 6, 0, 99), Day2().runProgram(listOf(1, 1, 1, 4, 99, 5, 6, 0, 99)))
    }

    @Test
    fun `Problem 1 - puzzle`() {
        assertEquals(5866663, Day2().part1(readSingleLineOfInts(dataFile)))
    }

    @Test
    fun `Problem 2 - puzzle`() {
        assertEquals(4259, Day2().part2(readSingleLineOfInts(dataFile), 19690720))
    }
}