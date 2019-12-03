package com.abyala.advent

import java.io.File

fun readFileAsListOfStrings(filename: String): List<String> =
    File(filename).readLines()

fun lineSequenceOf(filename: String): Sequence<String> =
        File(filename).bufferedReader().lineSequence()

fun lineSequenceOfInts(filename: String): Sequence<Int> =
        lineSequenceOf(filename).map(String::toInt)
