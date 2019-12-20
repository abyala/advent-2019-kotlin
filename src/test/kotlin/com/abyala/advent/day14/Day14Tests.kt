package com.abyala.advent.day14

import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class Day14Tests {
    private val dataFile = "src/test/resources/day14.txt"

    private val SIMPLE_EXAMPLE: List<String> = """
                10 ORE => 10 A
                1 ORE => 1 B
                7 A, 1 B => 1 C
                7 A, 1 C => 1 D
                7 A, 1 D => 1 E
                7 A, 1 E => 1 FUEL
            """.trimIndent().lines()

    private val MEDIUM_EXAMPLE = """
                9 ORE => 2 A
                8 ORE => 3 B
                7 ORE => 5 C
                3 A, 4 B => 1 AB
                5 B, 7 C => 1 BC
                4 C, 1 A => 1 CA
                2 AB, 3 BC, 4 CA => 1 FUEL """.trimIndent()

    private val LARGE_EXAMPLE_1 = """
                157 ORE => 5 NZVS
                165 ORE => 6 DCFZ
                44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL
                12 HKGWZ, 1 GPVTF, 8 PSHF => 9 QDVJ
                179 ORE => 7 PSHF
                177 ORE => 5 HKGWZ
                7 DCFZ, 7 PSHF => 2 XJWVT
                165 ORE => 2 GPVTF
                3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF => 8 KHKGT """.trimIndent()

    private val LARGE_EXAMPLE_2 = """
                2 VPVL, 7 FWMGM, 2 CXFTF, 11 MNCFX => 1 STKFG
                17 NVRVD, 3 JNWZP => 8 VPVL
                53 STKFG, 6 MNCFX, 46 VJHF, 81 HVMC, 68 CXFTF, 25 GNMV => 1 FUEL
                22 VJHF, 37 MNCFX => 5 FWMGM
                139 ORE => 4 NVRVD
                144 ORE => 7 JNWZP
                5 MNCFX, 7 RFSQX, 2 FWMGM, 2 VPVL, 19 CXFTF => 3 HVMC
                5 VJHF, 7 MNCFX, 9 VPVL, 37 CXFTF => 6 GNMV
                145 ORE => 6 MNCFX
                1 NVRVD => 8 CXFTF
                1 VJHF, 6 MNCFX => 4 RFSQX
                176 ORE => 6 VJHF """.trimIndent()

    private val LARGE_EXAMPLE_3 = """
            171 ORE => 8 CNZTR
            7 ZLQW, 3 BMBT, 9 XCVML, 26 XMNCP, 1 WPTQ, 2 MZWV, 1 RJRHP => 4 PLWSL
            114 ORE => 4 BHXH
            14 VRPVC => 6 BMBT
            6 BHXH, 18 KTJDG, 12 WPTQ, 7 PLWSL, 31 FHTLT, 37 ZDVW => 1 FUEL
            6 WPTQ, 2 BMBT, 8 ZLQW, 18 KTJDG, 1 XMNCP, 6 MZWV, 1 RJRHP => 6 FHTLT
            15 XDBXC, 2 LTCX, 1 VRPVC => 6 ZLQW
            13 WPTQ, 10 LTCX, 3 RJRHP, 14 XMNCP, 2 MZWV, 1 ZLQW => 1 ZDVW
            5 BMBT => 4 WPTQ
            189 ORE => 9 KTJDG
            1 MZWV, 17 XDBXC, 3 XCVML => 2 XMNCP
            12 VRPVC, 27 CNZTR => 2 XDBXC
            15 KTJDG, 12 BHXH => 5 XCVML
            3 BHXH, 2 VRPVC => 7 MZWV
            121 ORE => 7 VRPVC
            7 XCVML => 6 RJRHP
            5 BHXH, 4 VRPVC => 5 LTCX""".trimIndent()

    @Test
    fun `Verify Parser`() {
        val input = SIMPLE_EXAMPLE

        // Test 7A, 1D => 1E
        val expected =
            Reaction(
                mapOf(
                    "A" to Component("A", 7),
                    "D" to Component("D", 1)
                ),
                Component("E", 1)
            )
        val day14 = Day14(input)
        day14.pathsToRoot
        assertEquals(expected, day14.reactions["E"])
    }

    @Test
    fun `Part 1 - samples`() {
        assertEquals(31, Day14(SIMPLE_EXAMPLE).oreNeeded())
        assertEquals(165, oreNeeded(MEDIUM_EXAMPLE))
        assertEquals(13312, oreNeeded(LARGE_EXAMPLE_1))
        assertEquals(180697, oreNeeded(LARGE_EXAMPLE_2))
        assertEquals(2210736, oreNeeded(LARGE_EXAMPLE_3))
    }

    @Test
    fun `Part 1 - puzzle`() {
        assertEquals(2486514, oreNeeded(File(dataFile).readText()))
    }


    @Test
    fun `Part 2 - samples`() {
        assertEquals(82892753, maxFuel(LARGE_EXAMPLE_1))
        assertEquals(5586022, maxFuel(LARGE_EXAMPLE_2))
        assertEquals(460664, maxFuel(LARGE_EXAMPLE_3))
    }

    @Test
    fun `Part 2 - puzzle`() {
        assertEquals(998536, maxFuel(File(dataFile).readText()))
    }

    private fun oreNeeded(input: String) = Day14(input.lines()).oreNeeded()
    private fun maxFuel(input: String) = Day14(input.lines()).maxFuel()
}