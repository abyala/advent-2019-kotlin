package com.abyala.advent.day1

import com.abyala.advent.lineSequenceOfInts
import org.junit.Test
import kotlin.test.assertEquals

class Day1Test {
    private val dir = "src/test/resources"
    private val dataFile = "$dir/day1.txt"

    @Test
    fun `Problem 1 - samples`() {
        assertEquals(2, 12.fuelRequired())
        assertEquals(2, 14.fuelRequired())
        assertEquals(654, 1969.fuelRequired())
        assertEquals(33583, 100756.fuelRequired())
    }

    @Test
    fun `Problem 1 - puzzle`() {
        assertEquals(3454026, Day1().part1(lineSequenceOfInts(dataFile)))
    }

    @Test
    fun `Problem 2 - samples`() {
        val day1 = Day1()
        assertEquals(2, day1.totalFuel(14))
        assertEquals(966, day1.totalFuel(1969))
        assertEquals(50346, day1.totalFuel(100756))
    }

    @Test
    fun `Problem 2 - puzzle`() {
        assertEquals(5178170, Day1().part2(lineSequenceOfInts(dataFile)))
    }
}