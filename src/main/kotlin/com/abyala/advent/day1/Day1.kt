package com.abyala.advent.day1

fun Int.fuelRequired() = this / 3 - 2

class Day1 {
    fun part1(inputs: Sequence<Int>) =
        inputs.sumBy(Int::fuelRequired)

    fun part2(inputs: Sequence<Int>) =
        inputs.map { totalFuel(it) }
            .sum()

    // I do love me some tail recursion!
    tailrec fun totalFuel(mass: Int, acc: Int = 0): Int {
        val fuel = mass.fuelRequired()
        val remainder = fuel.fuelRequired()
        return when {
            fuel <= 0 -> acc
            remainder <= 0 -> acc + fuel
            else -> totalFuel(remainder, acc + fuel + remainder)
        }
    }
}