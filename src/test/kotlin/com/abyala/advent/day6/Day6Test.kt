package com.abyala.advent.day6

import com.abyala.advent.readFileAsListOfStrings
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.io.File

class Day6Test {
    private val dataFile = "src/test/resources/day6.txt"
    private fun readFile() = File(dataFile).readText()
    private val sampleList = listOf(
        "COM)B", "B)C", "C)D", "D)E", "E)F", "B)G", "G)H", "D)I", "E)J", "J)K", "K)L"
    )

    @Test
    fun `Part 1 - sample`() {
        val day6 = Day6(sampleList)
        assertEquals(11, day6.directOrbits)
        assertEquals(42, day6.totalOrbits)
    }

    @Test
    fun `Part 1 - puzzle`() {
        assertEquals(315757, Day6(readFileAsListOfStrings(dataFile)).totalOrbits)
    }

    @Test
    fun `Part 2 - sample`() {
        assertEquals(4, Day6(sampleList).distanceBetween("K", "I"))
    }

    @Test
    fun `Part 2 - puzzle`() {
        assertEquals(481, Day6(readFileAsListOfStrings(dataFile)).distanceBetween("YOU", "SAN")-2)
    }
}