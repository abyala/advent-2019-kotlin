package com.abyala.advent.day8

import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class Day8Test {
    private val dataFile = "src/test/resources/day8.txt"
    private fun readFile() = File(dataFile).readText()

    @Test
    fun `Part 1 - puzzle`() {
        assertEquals(2562, Day8(readFile()).part1())
    }

    @Test
    fun `Part 2 - samples`() {
        assertEquals("01\n10", Day8("0222112222120000", 2, 2).part2())
    }

    @Test
    fun `Part 2 - puzzle`() {
        println(Day8(readFile()).part2().pretty())
    }

    private fun String.pretty() = map {
        when (it) {
            '1' -> 'X'
            '0' -> ' '
            else -> it
        }
    }.joinToString ("")
}