package com.abyala.advent.day12

import com.abyala.advent.readFileAsListOfStrings
import junit.framework.TestCase.assertEquals
import org.junit.Test

class Day12Tests {
    private val dataFile = "src/test/resources/day12.txt"
    private val smallSample = listOf(
        "<x=-1, y=0, z=2>",
        "<x=2, y=-10, z=-7>",
        "<x=4, y=-8, z=8>",
        "<x=3, y=5, z=-1>"
    )
    private val largeSample = listOf(
        "<x=-8, y=-10, z=0>",
        "<x=5, y=5, z=10>",
        "<x=2, y=-7, z=3>",
        "<x=9, y=-8, z=-3>"
    )

    @Test
    fun `Moon parser`() {
        assertEquals(Moon(5, 2, -6, 0, 0, 0), Moon.from("<x=5, y=2, z=-6>"))
    }

    @Test
    fun `Part 1 - small sample`() {
        assertEquals(179, Day12(smallSample).part1(10))
    }

    @Test
    fun `Part 1 - large sample`() {
        assertEquals(1940, Day12(largeSample).part1(100))
    }

    @Test
    fun `Part 1 - puzzle`() {
        assertEquals(7687, Day12(readFileAsListOfStrings(dataFile)).part1())
    }

    @Test
    fun `Part 2 - samples`() {
        assertEquals(2772L, Day12(smallSample).findCopy())
        assertEquals(4686774924L, Day12(largeSample).findCopy())
    }

    @Test
    fun `Part 2 - puzzle`() {
        assertEquals(334945516288044, Day12(readFileAsListOfStrings(dataFile)).findCopy())
    }
}