package com.abyala.advent

import java.lang.IllegalArgumentException
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

class IntcodeComputer(val registers: IntArray) {

    constructor(registers:String): this(registers.split(",").map (String::toInt).toIntArray())

    private var pc = 0
    private fun nextIndex() = pc++
    private fun nextValue(mode: Mode = Mode.POSITION): Int = valueAt(nextIndex(), mode)
    private fun valueAt(pos: Int, mode: Mode): Int = when (mode) {
        Mode.POSITION -> registers[registers[pos]]
        Mode.IMMEDIATE -> registers[pos]
    }

    data class Output(val diagnosticCode: Int, val testOutputs: List<Int>) {
        fun passedAllTests() = testOutputs.all { it == 0 }
    }

    fun execute(input: Int): Output = execute(intArrayOf(input))
    fun execute(inputs: IntArray): Output {
        val outputQueue = LinkedBlockingQueue<Int>()
        execute(ArrayBlockingQueue(inputs.size, true, inputs.asList()), outputQueue)

        val data = outputQueue.toList()
        return Output(data.last(), data.dropLast(1))
    }

    fun execute(inputQueue: BlockingQueue<Int>, outputQueue: BlockingQueue<Int>) {

        while(true) {
            val instruction =
                Instruction.parse(nextValue(Mode.IMMEDIATE).toString())
            val modeIterator = instruction.modeIterator()
            fun next() = nextValue(modeIterator.next())
            when (instruction.opCode) {
                1 -> {
                    val sum = next() + next()
                    val nextValue = nextValue(Mode.IMMEDIATE)
                    registers[nextValue] = sum
                }
                2 -> {
                    val product = next() * next()
                    registers[nextValue(Mode.IMMEDIATE)] = product
                }
                3 -> {
                    val nextValue = nextValue(Mode.IMMEDIATE)
                    registers[nextValue] = inputQueue.take()
                }
                4 -> {
                    outputQueue.put(next())
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
                    registers[nextValue(Mode.IMMEDIATE)] = storeThis
                }
                8 -> {
                    val storeThis = if (next() == next()) 1 else 0
                    registers[nextValue(Mode.IMMEDIATE)] = storeThis
                }
                99 -> {
                    return
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
        "IntcodeComputer[registers=[${registers.joinToString()}]]"
}

