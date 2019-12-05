package com.abyala.advent.day4

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test
import kotlin.test.assertEquals

class Day4Test {
    @Test
    fun `Has duplicate`() {
        assertTrue("122345".hasAdjacentDup())
        assertTrue("112345".hasAdjacentDup())
        assertTrue("123455".hasAdjacentDup())
        assertFalse("123456".hasAdjacentDup())
        assertFalse("121212".hasAdjacentDup())
        assertFalse("123234".hasAdjacentDup())
    }

    @Test
    fun `Problem 1 - samples`() {
        assertTrue("111111".isSimplePassword())
        assertFalse("223450".isSimplePassword())
        assertFalse("123789".isSimplePassword())
    }

    @Test
    fun `Problem 1 - puzzle`() {
        assertEquals(1150, Day4().part1(240298, 784956))
    }

    @Test
    fun `Problem 2 - samples`() {
        assertTrue("112233".isComplexPassword())
        assertFalse("123444".isComplexPassword())
        assertTrue("111122".isComplexPassword())
    }

    @Test
    fun `Problem 2 - puzzle`() {
        assertEquals(748, Day4().part2(240298, 784956))
    }
}