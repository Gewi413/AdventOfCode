@file:Suppress("PackageDirectoryMismatch")

package day09

import java.io.File

fun main() {
    val input = File("challenges/day9").readLines().map { it.toLong() }
    var invalid = -1L
    for (i in 25 until input.size) {
        val possibles = input.subList(i - 25, i).toSet()
        if (!possibles.any { input[i] - it in possibles }) {
            invalid = input[i]
            break
        }
    }
    println(invalid)

    if (invalid == -1L) return
    var lower = 0
    var upper = 25
    while (true) {
        val sub = input.subList(lower, upper)
        when {
            sub.sum() < invalid -> upper++
            sub.sum() > invalid -> lower++
            else -> {
                println(sub.minOrNull()!! + sub.maxOrNull()!!)
                return
            }
        }
    }
}