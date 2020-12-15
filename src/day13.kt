@file:Suppress("PackageDirectoryMismatch")

package day13

import java.io.File

fun main() {
    val input = File("challenges/day13").readLines()
    val arrival = input[0].toInt()
    val busses = input[1].split(",").mapNotNull { if (it == "x") null else it.toInt() }
    outer@ for (time in 0..busses.maxOrNull()!!) {
        for (it in busses.filter { (arrival + time) % it == 0 }) {
            println(it * time)
            break@outer
        }
    }

    val times = input[1].split(",")
        .mapIndexedNotNull { i, time -> if (time == "x") null else Pair(time.toLong(), i.toLong()) }
    var step = 1L
    var time = 1L
    for ((schedule, offset) in times) {
        while ((time + offset) % schedule != 0L) time += step
        step *= schedule
    }
    println(time)
}