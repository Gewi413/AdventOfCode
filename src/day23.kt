@file:Suppress("PackageDirectoryMismatch")

package day23

import java.io.File
import java.util.*

fun main() {
    val input = File("challenges/day23").readLines().first().map { it.toInt() - 48 }
    val game = input.toMutableList()
    var current = 0
    for (i in 1..100) {
        val number = game[current]
        var wanted = number - 1
        val picked = mutableListOf<Int>()
        for (j in 0 until 3) {
            var ind = (current + 1) % game.size
            if (ind <= current) ind = 0
            picked += game.removeAt(ind)
        }
        var index: Int
        do {
            index = game.indexOf(wanted)
            wanted--
            if (wanted < 1) wanted = game.maxOrNull()!!
        } while (index == -1)
        game.addAll(index + 1, picked)
        current = game.indexOf(number)
        current = (current + 1) % game.size
    }
    val ind = game.indexOf(1)
    Collections.rotate(game, -ind)
    println(game.joinToString("").drop(1))

    val cups = mutableMapOf<Int, Int>()
    for ((cup, next) in input.zipWithNext()) {
        cups[cup] = next
    }
    var prev = input.last()
    for (i in (input.maxOrNull()!! + 1)..1000000) {
        cups[prev] = i
        prev = i
    }
    cups[prev] = input.first()
    current = input.first()
    for (i in 1..10000000) {
        val next = cups[current]!!
        cups[current] = cups[cups[cups[next]!!]!!]!!
        val taken = listOf(0, next, cups[next]!!, cups[cups[next]!!]!!)
        var number = current - 1
        while (number in taken) {
            number--
            if (number <= 0) number = 1000000
        }
        val num = cups[number]!!
        cups[number] = next
        cups[cups[cups[next]!!]!!] = num
        current = cups[current]!!
    }
    val number1 = cups[1]!!
    val number2 = cups[number1]!!
    println(number1.toLong() * number2.toLong())
}