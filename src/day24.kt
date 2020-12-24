@file:Suppress("PackageDirectoryMismatch")

package day24

import java.io.File

fun main() {
    val input = File("challenges/day24").readLines()
    val flipped = mutableMapOf<Pair<Int, Int>, Boolean>()
    for (line in input) {
        var x = 0
        var y = 0
        var buffered = ' '
        for (char in line) {
            when (char) {
                'n', 's' -> buffered = char
                'w' -> {
                    when (buffered) {
                        ' ' -> x++
                        'n' -> {
                            x++
                            y++
                        }
                        's' -> y--
                    }
                    buffered = ' '
                }
                'e' -> {
                    when (buffered) {
                        ' ' -> x--
                        'n' -> y++
                        's' -> {
                            y--
                            x--
                        }
                    }
                    buffered = ' '
                }
            }
        }
        flipped[Pair(x, y)] = !(flipped[Pair(x, y)] ?: false)
    }
    println(flipped.count { (_, v) -> v })

    var state = flipped.mapNotNull { (k, v) -> if (v) k else null }.toSet()
    for (i in 1..100) {
        val neighbors = mutableMapOf<Pair<Int, Int>, Int>()
        for (pos in state) {
            neighbors[pos] = (neighbors[pos] ?: 0)
            val next = listOf(
                Pair(pos.first + 1, pos.second),
                Pair(pos.first - 1, pos.second),
                Pair(pos.first + 1, pos.second + 1),
                Pair(pos.first - 1, pos.second - 1),
                Pair(pos.first, pos.second - 1),
                Pair(pos.first, pos.second + 1),
            )
            for (neighbor in next)
                neighbors[neighbor] = (neighbors[neighbor] ?: 0) + 1
        }
        val copy = state.toMutableSet()
        for ((pos, neighbor) in neighbors)
            if (neighbor == 2 || (pos in state && neighbor == 1))
                copy += pos
            else copy -= pos
        state = copy
    }
    println(state.count())
}