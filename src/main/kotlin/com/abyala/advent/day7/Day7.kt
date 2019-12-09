package com.abyala.advent.day7

import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit

class Day7(val program: String) {
    private fun explodePhases(phases: IntRange) =
        phases.flatMap { a ->
            phases.flatMap { b ->
                phases.flatMap { c ->
                    phases.flatMap { d ->
                        phases.map { e ->
                            listOf(a, b, c, d, e).toIntArray()
                        }
                    }
                }
            }
        }.filter { it.groupBy { it }.all { it.value.size == 1 } }

    private val BASIC_PHASES = explodePhases((0..4))
    private val FEEDBACK_PHASES = explodePhases((5..9))

    fun part1() = BASIC_PHASES.map { calculate(it, 0, 0) }.max()!!
    fun part2() = FEEDBACK_PHASES.map { calculate2(it)}.max()!!

    private tailrec fun calculate(phases: IntArray, index: Int, lastValue: Int): Int =
        if (index >= phases.size) lastValue
        else calculate(
            phases,
            index + 1,
            IntcodeComputer(program).execute(intArrayOf(phases[index], lastValue)).diagnosticCode
        )

    private fun calculate2(phases: IntArray): Int {
        val computers = phases.map { IntcodeComputer(program) }

        // Init input queues
        val inputQueues = computers.mapIndexed { index, _ ->
            LinkedBlockingQueue<Int>(listOf(phases[index])) }
        inputQueues[0].put(0)

        // Init output queues
        val outputQueues = computers.mapIndexed { index, _ ->
            object : LinkedBlockingQueue<Int>() {
                override fun put(e: Int?) {
                    // Don't actually keep in the output queue; just move to the next input queue
                    super.put(e)
                    inputQueues[if (index == computers.size - 1) 0 else index + 1].put(e)
                }
            }
        }

        // Run
        val execs = Executors.newFixedThreadPool(computers.size)
        computers.forEachIndexed { index, computer ->
            execs.submit { computer.execute(inputQueues[index], outputQueues[index]) }
        }
        execs.shutdown()
        execs.awaitTermination(1, TimeUnit.MINUTES)

        return outputQueues.last().last()
    }

    fun IntArray.print() = toList().toString()
}
