@file:Suppress("PackageDirectoryMismatch")

package day03

import java.io.File

fun main() {
    val input = File("challenges/day3").readLines()
    val length = input[0].length

    val directions = listOf(Pair(3, 1), Pair(1, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2)).map {
        var x = 0
        var trees = 0
        for (y in input.indices step it.second) {
            if (input[y][x] == '#') trees++
            x = (x + it.first) % length
        }
        trees
    }

    println(directions.first())
    println(directions.map { it.toLong() }.reduce { acc, i -> acc * i })
}