package com.abyala.advent.day5

import com.abyala.advent.day5.Day5.Mode.IMMEDIATE
import com.abyala.advent.day5.Day5.Mode.POSITION
import java.lang.IllegalArgumentException

class Day5(val registers: IntArray) {
    constructor(registers:String): this(registers.split(",").map (String::toInt).toIntArray())

    private var pc = 0
    private fun nextIndex() = pc++
    private fun nextValue(mode: Mode = POSITION): Int = valueAt(nextIndex(), mode)
    private fun valueAt(pos: Int, mode: Mode): Int = when (mode) {
        POSITION -> registers[registers[pos]]
        IMMEDIATE -> registers[pos]
    }

    data class Output(val diagnosticCode: Int, val testOutputs: List<Int>) {
        fun passedAllTests() = testOutputs.all { it == 0 }
    }

    fun execute(input: Int): Output {
        val outputs = mutableListOf<Int>()

        while(true) {
            val instruction = Instruction.parse(nextValue(IMMEDIATE).toString())
            val modeIterator = instruction.modeIterator()
            fun next() = nextValue(modeIterator.next())
            when (instruction.opCode) {
                1 -> {
                    val sum = next() + next()
                    val nextValue = nextValue(IMMEDIATE)
                    registers[nextValue] = sum
                }
                2 -> {
                    val product = next() * next()
                    registers[nextValue(IMMEDIATE)] = product
                }
                3 -> {
                    val nextValue = nextValue(IMMEDIATE)
                    registers[nextValue] = input
                }
                4 -> {
                    outputs += next()
                }
                5 -> {
                    val check = next()
                    val npc = next()
                    if (check != 0) {
                        pc = npc
                    }
                }
                6 -> {
                    val check = next()
                    val npc = next()
                    if (check == 0) {
                        pc = npc
                    }
                }
                7 -> {
                    val storeThis = if (next() < next()) 1 else 0
                    registers[nextValue(IMMEDIATE)] = storeThis
                }
                8 -> {
                    val storeThis = if (next() == next()) 1 else 0
                    registers[nextValue(IMMEDIATE)] = storeThis
                }
                99 -> {
                    return Output(outputs.last(), outputs.dropLast(1))
                }
                else -> throw Exception("Unknown opCode ${instruction.opCode} at pc=${pc-1}")
            }
        }
    }

    enum class Mode {
        POSITION, IMMEDIATE;
        companion object {
            fun of(mode: Int) = when (mode) {
                0 -> POSITION
                1 -> IMMEDIATE
                else -> throw IllegalArgumentException("Unknown Mode: $mode")
            }
        }
    }
    class Instruction(val opCode: Int, private val params: List<Int>) {
        fun modeIterator(): Iterator<Mode> = object: AbstractIterator<Mode>() {
            var idx = 0
            override fun computeNext() {
                setNext(Mode.of(if (idx < params.size) params[idx] else 0))
                idx++
            }
        }

        companion object {
            fun parse(text: String) =
                Instruction(
                    text.takeLast(2).toInt(),
                    text.dropLast(2)
                        .toCharArray()
                        .map(Char::toString)
                        .map(String::toInt)
                        .reversed()
                )
        }
    }

    override fun toString(): String =
            "Day 5 [registers=[${registers.joinToString()}]]"
}