@file:Suppress("PackageDirectoryMismatch")

package day25

import java.io.File

fun main() {
    val (a, b) = File("challenges/day25").readLines().map { it.toLong() }
    var value = 1L
    var door = -1
    var card = -1
    var loop = 0
    while (true) {
        if (value == a) card = loop
        if (value == b) door = loop
        if (card != -1 && door != -1) break
        value = (value * 7) % 20201227
        loop++
    }
    value = 1L
    for (i in 1..card)
        value = (value * b) % 20201227

    println(value)
}