package com.abyala.advent.day3

import com.abyala.advent.readFileAsListOfStrings
import org.junit.Test
import kotlin.test.assertEquals

class Day3Test {
    private val dataFile = "src/test/resources/day3.txt"

    private fun readInputStrings(): Pair<String, String> = readFileAsListOfStrings(dataFile).run {
        Pair(this[0], this[1])
    }

    private fun testPart1(expected: Int, str1: String, str2: String) {
        assertEquals(expected, Day3().part1(str1, str2))
    }

    private fun testPart2(expected: Int, str1: String, str2: String) {
        assertEquals(expected, Day3().part2(str1, str2))
    }

    @Test
    fun `Problem 1 - samples`() {
        testPart1(6, "R8,U5,L5,D3", "U7,R6,D4,L4")
        testPart1(159, "R75,D30,R83,U83,L12,D49,R71,U7,L72", "U62,R66,U55,R34,D71,R55,D58,R83")
        testPart1(135, "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")
    }

    @Test
    fun `Problem 1 - puzzle`() {
        readInputStrings().run {
            testPart1(232, first, second)
        }
    }

    @Test
    fun `Problem 2 - samples`() {
        testPart2(30, "R8,U5,L5,D3", "U7,R6,D4,L4")
        testPart2(610, "R75,D30,R83,U83,L12,D49,R71,U7,L72", "U62,R66,U55,R34,D71,R55,D58,R83")
        testPart2(410, "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")
    }

    @Test
    fun `Problem 2 - puzzle`() {
        readInputStrings().run {
            testPart2(6084, first, second)
        }
    }
}