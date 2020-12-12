@file:Suppress("PackageDirectoryMismatch")

package day12

import java.io.File
import kotlin.math.absoluteValue

fun main() {
    val input = File("challenges/day12").readLines()
    var x = 0
    var y = 0
    var dir = 0
    for (line in input) {
        val value = line.drop(1).toInt()
        var command = line.first()
        if (line.first() == 'F') command = listOf('E', 'N', 'W', 'S')[dir % 4]
        when (command) {
            'N' -> y += value
            'S' -> y -= value
            'E' -> x += value
            'W' -> x -= value
            'L' -> dir += value / 90
            'R' -> dir += (4 - value / 90)
        }
    }
    println(x.absoluteValue + y.absoluteValue)
    x = 0
    y = 0
    var wayX = 10
    var wayY = 1
    for (line in input) {
        var value = line.drop(1).toInt()
        var command = line.first()
        if (command == 'R') {
            value = 360 - value
            command = 'L'
        }
        when (command) {
            'N' -> wayY += value
            'S' -> wayY -= value
            'E' -> wayX += value
            'W' -> wayX -= value
            'L' -> {
                val (prevX, prevY) = Pair(wayX, wayY)
                when (value / 90) {
                    1 -> {
                        wayX = -prevY
                        wayY = prevX
                    }
                    2 -> {
                        wayX = -prevX
                        wayY = -prevY
                    }
                    3 -> {
                        wayX = prevY
                        wayY = -prevX
                    }
                }
            }
            'F' -> {
                x += wayX * value
                y += wayY * value
            }
        }
    }
    println(x.absoluteValue + y.absoluteValue)
}