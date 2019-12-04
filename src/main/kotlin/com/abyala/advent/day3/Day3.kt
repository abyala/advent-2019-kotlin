package com.abyala.advent.day3

import kotlin.math.absoluteValue

data class Point(val x: Int, val y: Int) {
    fun distance(): Int = x.absoluteValue + y.absoluteValue

    companion object {
        val ORIGIN = Point(0, 0)
    }
}

data class Step(val point: Point, val distance: Int) {
    fun walk(move: Move): List<Step> = (1..move.count).map { step ->
        when (move.direction) {
            "R" -> Step(Point(point.x + step, point.y), distance + step)
            "L" -> Step(Point(point.x - step, point.y), distance + step)
            "U" -> Step(Point(point.x, point.y + step), distance + step)
            else -> Step(Point(point.x, point.y - step), distance + step)
        }
    }.toList()

    companion object {
        val ORIGIN = Step(Point.ORIGIN, 0)
    }
}

data class Move(val direction: String, val count: Int) {
    companion object {
        fun of(move: String): Move = Move(move.substring(0, 1), move.substring(1).toInt())
    }
}

class Day3 {

    fun part1(pathA: String, pathB: String): Int =
        pathA.allPoints().intersect(pathB.allPoints())
            .map(Point::distance)
            .min()!!

    fun part2(pathA: String, pathB: String): Int =
        pathA.initialSteps().plus(pathB.initialSteps())
            .groupBy(Step::point)
            .filterValues { it.size > 1 }
            .map { it.value.sumBy(Step::distance) }
            .min()!!

    private fun String.initialSteps() = allSteps().groupBy { it.point }.map { it.value.minBy(Step::distance)!! }
    private fun String.allSteps() = toMoves().fold(mutableListOf(Step.ORIGIN)) { acc, move ->
        acc.walk(move)
    }.apply {
        // Remove the origin, since it doesn't count as part of the actual path
        removeAt(0)
    }.toList()

    private fun String.allPoints() = allSteps().map(Step::point)
    private fun String.toMoves() = split(",").map(Move.Companion::of)
    private fun MutableList<Step>.walk(move: Move) = also {
        addAll(last().walk(move))
    }
}