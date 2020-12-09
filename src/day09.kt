@file:Suppress("PackageDirectoryMismatch")

package day09

import java.io.File

fun main() {
    val input = File("challenges/day9").readLines().map { it.toLong() }
    var invalid = -1L
    for (i in 25 until input.size) {
        val possibles = input.subList(i - 25, i).map { it }.toSet()
        if (!possibles.any { input[i] - it in possibles }) {
            invalid = input[i]
            break
        }
    }
    println(invalid)
    if (invalid == -1L) return
    for (i in input.indices)
        for (j in i until input.size) {
            if (input[j] > invalid) break
            val sub = input.subList(i, j).map { it }
            if (sub.sum() == invalid) {
                println(sub.minOrNull()!! + sub.maxOrNull()!!)
                return
            }
        }
}