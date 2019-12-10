package com.abyala.advent.day8

class Day8(input: String, private val width: Int = 25, height: Int = 6) {
    private val layers = input.chunked(width * height)

    fun part1(): Int {
        val layerCount = layers.map { it.groupingBy { c -> c.toString().toInt() }.eachCount() }
        return layerCount.minBy { it.getValue(0) }!!.run {
            getValue(1) * getValue(2)
        }
    }

    fun part12() =
        layers.map { it.groupingBy { c -> c.toString().toInt() }.eachCount() }
            .minBy { it.getValue(0) }!!.run {
            getValue(1) * getValue(2)
        }

    fun part2() =
        layers.fold("2".repeat(layers[0].length)) { acc: String, line: String ->
            acc.mapIndexed { index, c -> if (c == '2') line[index] else c }.joinToString("")
        }.chunked(width).joinToString("\n")

}