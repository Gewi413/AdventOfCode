@file:Suppress("PackageDirectoryMismatch")

package day15

import java.io.File

fun main() {
    val input = File("challenges/day15").readText().split(",").map { it.toInt() }
    if (input.count { it == input.last() } != 1) TODO("calculate the real difference")
    val lastSeen = mutableMapOf<Int, Int>()
    for ((i, num) in input.withIndex()) lastSeen[num] = i
    var num = 0
    for (i in input.size..30000000 - 2) {
        val new = i - (lastSeen[num] ?: i)
        lastSeen[num] = i
        if (i == 2019) println(num)
        num = new
    }
    println(num)
}