package com.abyala.advent.day5

import com.abyala.advent.IntcodeComputer
import com.abyala.advent.IntcodeComputer.Mode.IMMEDIATE
import com.abyala.advent.IntcodeComputer.Mode.POSITION
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import java.io.File

class Day5Test {
    private val dataFile = "src/test/resources/day5.txt"
    private fun readFile() = File(dataFile).readText()

    @Test
    fun `Test instruction parsing and iteration`() {
        val instruction = IntcodeComputer.Instruction.parse("1002")
        assertEquals(2, instruction.opCode)

        val iter = instruction.modeIterator()
        listOf(POSITION, IMMEDIATE, POSITION, POSITION, POSITION).forEachIndexed { index, mode ->
            assertEquals("Incorrect mode at index $index", mode, iter.next())
        }
    }

    @Test
    fun `Part 1 - puzzle`() {
        val IntcodeComputer = IntcodeComputer(readFile())
        val output = IntcodeComputer.execute(1)
        assertTrue(output.passedAllTests())
        assertEquals(13087969, output.diagnosticCode)
    }

    @Test
    fun `Part 1 - test new instructions`() {
        val IntcodeComputer = IntcodeComputer("3,0,4,0,99")
        val output = IntcodeComputer.execute(123)
        assertEquals("123, 0, 4, 0, 99", IntcodeComputer.registers.joinToString())
        assertTrue(output.passedAllTests())
        assertEquals(123, output.diagnosticCode)
    }

    @Test
    fun `Part 2 - samples`() {
        val data = "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
            "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104," +
            "999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99"
        verifyOutput(data, 7, 999)
        verifyOutput(data, 8, 1000)
        verifyOutput(data, 9, 1001)
    }

    private fun verifyOutput(data: String, input: Int, expected: Int) {
        val IntcodeComputer = IntcodeComputer(data)
        val output = IntcodeComputer.execute(input)
        assertTrue(output.passedAllTests())
        assertEquals(expected, output.diagnosticCode)
    }

    @Test
    fun `Part 2 - puzzle`() {
        verifyOutput(readFile(), 5, 14110739)
    }
}