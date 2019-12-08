package com.abyala.advent.day6

class Day6(input: List<String>) {
    private val spaceObjects: Map<String, SpaceObject>

    init {
        val parentToChildren = input
            .flatMap { it.split(")") }
            .windowed(2, 2, false) {Pair(it[0], it[1])}
            .groupBy { it.first }

        val spaceObjects = mutableMapOf("COM" to SpaceObject("COM"))
        val toProcess = mutableListOf(parentToChildren["COM"])
        while (toProcess.isNotEmpty()) {
            toProcess.removeAt(0)!!.forEach { orbit ->
                val parent = spaceObjects[orbit.first]!!
                val child = SpaceObject(orbit.second, parent)
                parent.children += child
                spaceObjects[child.name] = child
                parentToChildren[child.name]?.let {
                    toProcess += it
                }
            }
        }
        this.spaceObjects = spaceObjects.toMap()
    }

    val directOrbits = spaceObjects.size - 1
    val totalOrbits = spaceObjects.values.sumBy(SpaceObject::distance)

    fun distanceBetween(start: String, end: String) =
        distanceToTarget(end,
            spaceObjects.getValue(start).neighbors().map { Pair(it, 1) })

    private tailrec fun distanceToTarget(target: String, options: List<Pair<SpaceObject, Int>>, visited: Set<SpaceObject> = emptySet()): Int {
        val (spaceObject, distance) = options[0]
        return when {
            spaceObject.name == target -> distance
            visited.contains(spaceObject) -> distanceToTarget(target, options.drop(1), visited)
            else -> distanceToTarget(target,
                options.drop(1).plus(spaceObject.neighbors().map { Pair(it, distance+1) }),
                visited.plus(spaceObject))
        }
    }
}

data class SpaceObject(val name: String, var parent: SpaceObject? = null) {
    val children = mutableSetOf<SpaceObject>()
    val distance: Int by lazy {
        parent?.let {
            it.distance + 1
        } ?: 0
    }

    fun neighbors() = children.plus(parent).filterNotNull()

    override fun toString(): String = "SpaceObject[name=$name, parent=${parent?.name}"
}

