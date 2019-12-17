package com.abyala.advent.day10

import com.abyala.advent.readFileAsListOfStrings
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class Day10Test {
    private val dataFile = "src/test/resources/day10.txt"

    @Test
    fun `Create Point with GCD`() {
        fun testGCD(expectedX: Int, expectedY: Int, x: Int, y: Int) {
            val slope = Slope.of(x, y)
            assertEquals("Incorrect reduced x", expectedX, slope.x)
            assertEquals("Incorrect reduced y", expectedY, slope.y)
        }

        testGCD(2, 7, 2, 7)
        testGCD(1, 1, 3, 3)
        testGCD(1, 2, 2, 4)
        testGCD(2, 1, 4, 2)
        testGCD(-1, 2, -2, 4)
        testGCD(1, -2, 2, -4)
        testGCD(-1, -2, -2, -4)
    }

    @Test
    fun `Num detected asteroids, small data sample`() {
        val map = listOf(".#..#", ".....", "#####", "....#", "...##")
        assertEquals(8, Day10(map).numDetectedAsteroids(3, 4))
        assertEquals(7, Day10(map).numDetectedAsteroids(4, 4))
        assertEquals(7, Day10(map).numDetectedAsteroids(1, 0))
        assertEquals(6, Day10(map).numDetectedAsteroids(0, 2))
        assertEquals(5, Day10(map).numDetectedAsteroids(4, 2))
    }

    @Test
    fun `Part 1 - sample`() {
        val map = listOf(".#..#", ".....", "#####", "....#", "...##")
        assertEquals(8, Day10(map).part1())
    }

    @Test
    fun `Part 1 - puzzle`() {
        assertEquals(334, Day10(readFileAsListOfStrings(dataFile)).part1())
    }

    @Test
    fun `Clock radians sorted`() {
        println("Quadrant 1:  0 to ${Math.PI / 2}")
        println("Quadrant 2:  Up to ${Math.PI}")
        println("Quadrant 3:  Up to ${Math.PI * 3 / 2}")
        println("Quadrant 4:  Greater than ${Math.PI * 3 / 2}")

        fun atan(test: String, x: Int, y: Int) {
            println("$test ${Math.PI / 2 + Math.atan2(y.toDouble(), x.toDouble())}")
        }
        fun quad2(x: Int, y: Int) {
            println("For Q2: ${Math.PI/2 + Math.atan2(y.toDouble(), x.toDouble())}")
        }
        fun quad3(x: Int, y: Int) {
            println("For Q3: ${Math.PI/2 +  Math.atan2(y.toDouble(), x.toDouble())}")
        }
        fun quad4(x: Int, y: Int) {
            println("For Q4: ${Math.PI * 5 / 2 + Math.atan2(y.toDouble(), x.toDouble())}")
        }

        atan("For Q1", 1, -10)
        atan("For Q1", 1, -1)
        atan("For Q1", 10, -1)
        quad2(10, 1)
        quad2(1, 1)
        quad2(1, 10)
        quad3(-1, 10)
        quad3(-1, 1)
        quad3(-10, 1)
        quad4(-10, -1)
        quad4(-1, -1)
        quad4(-1, -10)

        println("Q1 " + (Math.PI - Math.atan2(1.0, -10.0)))
        println("Q2 " + (Math.PI / 2 + Math.atan2(1.0, 10.0)))
        println("Q2 " + (Math.PI / 2 + Math.atan2(10.0, 1.0)))
        println("Q3 " + (Math.PI - Math.atan2(-1.0, 10.0)))
        println("Q4 " + (3 * 2 / Math.PI - Math.atan2(-1.0, -1.0)))
        println("Q4 " + (3 * 2 / Math.PI - Math.atan2(-1.0, -10.0)))
        val testList = listOf(
            Slope(0, -1),
            Slope(1, -10),
            Slope(1, -1),
            Slope(10, -1),

            Slope(1, 0),
            Slope(2, 1),
            Slope(1, 1),
            Slope(1, 10),

            Slope(0, 1),
            Slope(-1, 10),
            Slope(-1, 1),
            Slope(-10, 1),

            Slope(-1, 0),
            Slope(-10, -1),
            Slope(-1, -1),
            Slope(-1, -10)
        )
        assertEquals(testList, testList.sortedBy { it.clockRadians })
    }

    @Test
    fun `Part 2 - points to slopes to radians`() {
        val from = Point(11, 13)
        val to = Point(10,1)
        assertTrue(to.distanceTo(from) > 0)
        assertEquals(Slope(-1, -12), from.slopeTo(to))
    }

    @Test
    fun `Part 2 - large sample`() {
        val map = listOf(
".#..##.###...#######",
"##.############..##.",
".#.######.########.#",
".###.#######.####.#.",
"#####.##.#.##.###.##",
"..#####..#.#########",
"####################",
"#.####....###.#.#.##",
"##.#################",
"#####.##.###..####..",
"..######..##.#######",
"####.##.####...##..#",
".#####..#.######.###",
"##...#.##########...",
"#.##########.#######",
".####.#.###.###.#.##",
"....##.##.###..#####",
".#.#.###########.###",
"#.#.#.#####.####.###",
"###.##.####.##.#..##")
        val vaporizations = Day10(map).vaporizationOrder()
        assertEquals("11,12", vaporizations[0].printable())
        assertEquals("12,1", vaporizations[1].printable())
        assertEquals("12,2", vaporizations[2].printable())
        assertEquals("12,8", vaporizations[9].printable())
        assertEquals("16,0", vaporizations[19].printable())
        assertEquals("16,9", vaporizations[49].printable())
        assertEquals("10,16", vaporizations[99].printable())
        assertEquals("9,6", vaporizations[198].printable())
        assertEquals("8,2", vaporizations[199].printable())
        assertEquals("10,9", vaporizations[200].printable())
    }

    @Test
    fun `Part 2 - puzzle`() {
        assertEquals(1119, Day10(readFileAsListOfStrings(dataFile)).part2(200))
    }
}