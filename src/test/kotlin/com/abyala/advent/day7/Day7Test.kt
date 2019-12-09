package com.abyala.advent.day7

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.io.File

class Day7Test {
    private val dataFile = "src/test/resources/day7.txt"
    private fun readFile() = File(dataFile).readText()

    @Test
    fun `Part 1 - samples`() {
        assertEquals(43210, Day7("3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0").part1())
        assertEquals(54321, Day7("3,23,3,24,1002,24,10,24,1002,23,-1,23," +
                "101,5,23,23,1,24,23,23,4,23,99,0,0").part1())
        assertEquals(65210, Day7("3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33," +
                "1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0").part1())
    }

    @Test
    fun `Part 1 - puzzle`() {
        assertEquals(18812, Day7(readFile()).part1())
    }

    @Test
    fun `Part 2 - samples`() {
        assertEquals(139629729, Day7("3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26," +
                "27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5").part2())
        assertEquals(18216, Day7("3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54," +
                "-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4," +
                "53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10").part2())
    }

    @Test
    fun `Part 2 - puzzle`() {
        assertEquals(25534964, Day7(readFile()).part2())
    }
}