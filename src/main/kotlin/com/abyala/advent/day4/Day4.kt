package com.abyala.advent.day4

fun String.charsIncrease() = (1 until length).none { get(it - 1) > get(it) }
fun String.indexOfDups(): IntArray = (0 until length - 1).filter { this[it] == this[it + 1] }.toIntArray()
fun String.hasAdjacentDup() = indexOfDups().isNotEmpty()
fun String.hasExactAdjacentDup() = indexOfDups().toSet().run {
    any { !contains(it - 1) && !contains(it + 1) }
}

fun String.isSimplePassword() = charsIncrease() && hasAdjacentDup()
fun String.isComplexPassword() = charsIncrease() && hasExactAdjacentDup()

class Day4 {

    fun part1(low: Int, high: Int): Int =
        (low..high).map(Int::toString).count { it.isSimplePassword() }

    fun part2(low: Int, high: Int): Int =
        (low..high).map(Int::toString).count { it.isComplexPassword() }
}
