@file:Suppress("PackageDirectoryMismatch")

package day13

import java.io.File
import java.net.URLEncoder

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
        .mapIndexedNotNull { i, time -> if (time == "x") null else Pair(time.toLong(), i) }
    var wolframAlpha = ""
    for ((schedule, offset) in times)
        wolframAlpha += "(n + $offset) mod $schedule = 0, "
    println("https://wolframalpha.com/input/?i=" + URLEncoder.encode(wolframAlpha, "UTF8"))
    // will fix when i know how to
    //println(803025030761664)
}