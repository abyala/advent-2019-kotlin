package com.abyala.advent.day14


data class Component(val element: String, val quantity: Long) {
    operator fun plus(num: Long) = if (num == 0L) this else Component(element, quantity + num)
    operator fun plus(other: Component): Component {
        require(element == other.element) { "Element $element must match ${other.element}" }
        return plus(other.quantity)
    }

    operator fun times(num: Long) = if (num == 1L) this else Component(element, quantity * num)
}

data class Reaction(val inputs: Map<String, Component>, val output: Component) {
    val targetElement = output.element

    fun expandToTarget(targetOutput: Long): Pair<List<Component>, Long> {
        val numReactions = (targetOutput + output.quantity - 1) / output.quantity
        return Pair(inputs.map { it.value * numReactions }, numReactions * output.quantity)
    }
}

class Day14(input: List<String>) {
    val reactions: Map<String, Reaction> = input.map { line ->
        val (left, right) = line.split(" => ").take(2).map { side ->
            side.split(", ").map { comp ->
                val (quantity, element) = comp.split(" ").take(2)
                Component(element, quantity.toLong())
            }
        }
        Reaction(left.associateBy(Component::element), right.first())
    }.associateBy(Reaction::targetElement)

    val pathsToRoot: Map<String, Set<String>> by lazy {
        val paths = mutableMapOf<String, Set<String>>()

        fun getPath(element: String): Set<String> {
            val elementPaths =
                reactions[element]
                    ?.inputs
                    ?.map { it.value.element }
                    ?.flatMap { (paths[it] ?: getPath(it)).plus(it) }
                    ?.toSet() ?: emptySet()
            paths[element] = elementPaths
            return elementPaths
        }

        getPath(FUEL)
        return@lazy paths.toMap()
    }

    fun maxFuel(maxOre: Long = 1_000_000_000_000): Long {
        val orePerFuel = oreNeeded()
        return maxFuel(maxOre, orePerFuel, maxOre / orePerFuel)
    }

    private tailrec fun maxFuel(maxOre: Long, orePerFuel: Long, targetFuel: Long): Long {
        val oreNeeded = oreNeeded(targetFuel)
        val unusedOre = maxOre - oreNeeded

        return if (oreNeeded > maxOre) {
            targetFuel - 1
        } else {
            val amountToAdd = (targetFuel * unusedOre.toDouble() / oreNeeded).toLong()
            val newTarget = targetFuel + amountToAdd.coerceAtLeast(1)
            maxFuel(maxOre, orePerFuel, newTarget)
        }
    }

    fun oreNeeded(targetFuel: Long = 1): Long {
        val needed = mutableMapOf(FUEL to Component(FUEL, targetFuel))
        val inventory: MutableMap<String, Long> = mutableMapOf()

        while (needed.any { it.key != ORE }) {
            val target = needed.removeBestChoice()
            val reaction = reactions.getValue(target.element)
            val amountToCreate = target.quantity // - (inventory[target.element] ?: 0)

            val (allInputs, outputCreated) = reaction.expandToTarget(amountToCreate)

            // Add the output into the inventory
            inventory.merge(target.element, outputCreated) { old: Long, new: Long -> old + new }

            // Add the input into the list of what's needed
            allInputs.forEach { input ->
                needed.merge(input.element, input) { old, new -> old + new }
            }
        }

        return needed.getValue(ORE).quantity
    }

    private fun MutableMap<String, Component>.removeBestChoice(): Component {
        if (size == 1) return remove(keys.iterator().next())!!

        val allNeeded = values
            .flatMap {
                reactions[it.element]?.inputs?.values?.map(Component::element).orEmpty()
            }
            .toSet()

        val bestKey = filterKeys { it !in allNeeded && it != ORE }
            .toSortedMap(compareByDescending { pathsToRoot[it]?.size ?: 0 })
            .firstKey()
        return remove(bestKey)!!
    }

    companion object {
        const val ORE = "ORE"
        const val FUEL = "FUEL"
    }
}