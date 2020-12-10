@file:Suppress("PackageDirectoryMismatch")

package day10

import java.io.File

fun main() {
    val input = File("challenges/day10").readLines().map { it.toInt() }.toSortedSet()
    var previous = 0
    var three = 1
    var one = 0
    for (adapter in input) {
        when (adapter - previous) {
            1 -> one++
            3 -> three++
            else -> TODO("illegal adapter")
        }
        previous = adapter
    }
    println(one * three)

    var combinations = 0L
    val device = input.maxOrNull()!! + 3
    val todo = mutableMapOf(0 to 1L)
    while (todo.isNotEmpty()) {
        val (current, amount) = todo.minByOrNull { (k, _) -> k }!!
        println(todo)
        todo.remove(current)
        if (current + 1 == device || current + 3 == device) {
            combinations += amount
            continue
        }
        if (current + 1 in input) todo[current + 1] = (todo[current + 1] ?: 0L) + amount
        if (current + 2 in input) todo[current + 2] = (todo[current + 2] ?: 0L) + amount
        if (current + 3 in input) todo[current + 3] = (todo[current + 3] ?: 0L) + amount
    }
    println(combinations)
}