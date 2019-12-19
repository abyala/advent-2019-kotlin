package com.abyala.advent.day12

import com.abyala.advent.lcm
import java.util.regex.Matcher
import kotlin.math.absoluteValue

typealias Galaxy = List<Moon>

data class Moon(val x: Int, val y: Int, val z: Int, val dx: Int = 0, val dy: Int = 0, val dz: Int = 0) {
    private var gravityX = 0
    private var gravityY = 0
    private var gravityZ = 0
    val potentialEnergy = x.absoluteValue + y.absoluteValue + z.absoluteValue
    val kineticEnergy = dx.absoluteValue + dy.absoluteValue + dz.absoluteValue
    val totalEnergy = potentialEnergy * kineticEnergy

    fun applyGravity(other: Moon) {
        gravityX += x.gravityFrom(other.x)
        gravityY += y.gravityFrom(other.y)
        gravityZ += z.gravityFrom(other.z)
    }

    fun move() = Moon(
        x + dx + gravityX, y + dy + gravityY, z + dz + gravityZ,
        dx + gravityX, dy + gravityY, dz + gravityZ
    )

    private fun Int.gravityFrom(other: Int) = when {
        other > this -> 1
        other < this -> -1
        else -> 0
    }

    companion object {
        fun from(input: String): Moon {
            return "<x=([^,]+), y=([^,]+), z=([^>]+)>".toPattern().matcher(input).let {
                require(it.matches()) { "Input $input is not a moon!" }
                Moon(it[1], it[2], it[3])
            }
        }

        private operator fun Matcher.get(index: Int) = group(index).toInt()
    }
}

class Day12(input: List<String>) {
    private val moons = input.map { Moon.from(it) }

    fun part1(numDays: Int = 1000) = moveDays(numDays).sumBy { it.totalEnergy }

    tailrec fun moveDays(numDays: Int, galaxy: Galaxy = moons.map { it.copy() }): Galaxy {
        if (numDays <= 0) return galaxy

        galaxy.forEachIndexed { index, moon ->
            galaxy.filterIndexed { j, _ -> index != j }
                .forEach { moon.applyGravity(it) }
        }
        return moveDays(numDays - 1, galaxy.map(Moon::move))
    }

    fun findCopy() = findCopy(moons, moons)

    private tailrec fun findCopy(target: Galaxy,
                                 current: Galaxy,
                                 result: Triple<Long?, Long?, Long?> = Triple(null, null, null),
                                 acc: Long=0): Long {
        if (result.first != null && result.second != null && result.third != null) {
            return lcm(lcm(result.first!!, result.second!!), result.third!!)
        }

        val next = moveDays(1, current)
        val allPairs = target.zip(next)
        val newResult = result.firstIf(acc+1) {
            allPairs.all { it.first.x == it.second.x && it.first.dx == it.second.dx }
        }.secondIf(acc+1) {
            allPairs.all { it.first.y == it.second.y && it.first.dy == it.second.dy }
        }.thirdIf(acc+1) {
            allPairs.all { it.first.z == it.second.z && it.first.dz == it.second.dz }
        }

        return findCopy(target, next, newResult, acc+1)
    }

    private fun Triple<Long?, Long?, Long?>.firstIf(new: Long, condition: () -> Boolean) =
        if (first == null && condition.invoke()) copy(first = new) else this
    private fun Triple<Long?, Long?, Long?>.secondIf(new: Long, condition: () -> Boolean) =
        if (second == null && condition.invoke()) copy(second = new) else this
    private fun Triple<Long?, Long?, Long?>.thirdIf(new: Long, condition: () -> Boolean) =
        if (third == null && condition.invoke()) copy(third = new) else this
}