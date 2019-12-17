package com.abyala.advent.day10

import com.abyala.advent.gcd
import kotlin.math.absoluteValue

data class Slope(val x: Int, val y: Int) {
    val clockRadians = when {
        x == 0 && y >= 0 -> Math.PI
        x == 0 -> 0.0
        x > 0 && y <= 0 -> Math.PI / 2 + Math.atan2(y.toDouble(), x.toDouble()) // Quadrant 1
        x > 0 -> Math.PI /2 + Math.atan2(y.toDouble(), x.toDouble())            // Quadrant 2
        y >= 0 -> Math.PI /2 + Math.atan2(y.toDouble(), x.toDouble())           // Quadrant 3
        else ->  Math.PI * 5 / 2 + Math.atan2(y.toDouble(), x.toDouble())       // Quadrant 4
    }

    companion object {
        fun of(x: Int, y: Int): Slope = gcd(x, y).let {
            Slope(x / it.absoluteValue, y / it.absoluteValue)
        }
    }
}

data class Point(val x: Int, val y: Int) : Comparable<Point> {
    fun slopeTo(other: Point) = Slope.of(other.x - x, other.y - y)
    fun distanceTo(other: Point): Double = Math.pow((x - other.x).toDouble(), 2.0) + Math.pow((y - other.y).toDouble(), 2.0)

    fun printable() = "$x,$y"

    override fun compareTo(other: Point): Int = if (y == other.y) x - other.x else y - other.y
}

data class DistantPoint(val point: Point, val clockRadians: Double, val order: Int)

class Day10(input: List<String>) {
    private val asteroids = input.mapIndexed { row, line ->
        line.mapIndexed { col, c ->
            if (c == ASTEROID) Point(col, row) else null
        }.filterNotNull()
    }.flatten().sorted()


    fun numDetectedAsteroids(x: Int, y: Int) = numDetectedAsteroids(Point(x, y))

    private fun slopesToAsteroids(point: Point) =
        asteroids
            .filter { it.x != point.x || it.y != point.y }
            .groupBy { point.slopeTo(it) }

    private fun numDetectedAsteroids(point: Point) = slopesToAsteroids(point).count()

    fun part1(): Int = asteroids.map { numDetectedAsteroids(it) }.max()!!

    fun part2(index: Int) = vaporizationOrder()[index - 1].let { it.x * 100 + it.y }

    fun vaporizationOrder(): List<Point> {
        val (station, slopes) = asteroids.associateWith { slopesToAsteroids(it) }
            .maxBy { it.value.size }
            .let { Pair(it!!.key, it.value) }

        return slopes.flatMap { entry ->
            entry.value
                .sortedBy { it.distanceTo(station) }
                .mapIndexed { index, point ->
                    DistantPoint(point, entry.key.clockRadians, index)
                }
        }.sortedWith(Comparator { o1, o2 ->
            when {
                o1.order != o2.order -> o1.order - o2.order
                o1.clockRadians <= o2.clockRadians -> -1
                else -> 1
            }
        }).map { it.point }
    }

    companion object {
        const val ASTEROID = '#'
    }
}