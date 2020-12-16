@file:Suppress("PackageDirectoryMismatch")

package day16

import java.io.File

fun main() {
    val input = File("challenges/day16").readText().split("\n\n")
    val requirements = input[0].split("\n").map { line ->
        val split = line.split(": ")
        val (start, end) = split[1].split(" or ").map { value ->
            val (start, end) = value.split("-").map { it.toInt() }
            start..end
        }
        Pair(split[0], start union end)
    }.toMap()
    val tickets = input[2].split("\n").drop(1).map { line ->
        line.split(",").map { it.toInt() }
    }

    var invalid = 0
    val required = requirements.values.flatten().toSet()
    val filteredTickets = tickets.filter { ticket ->
        for (value in ticket)
            if (value !in required) {
                invalid += value
                return@filter false
            }
        true
    }
    println(invalid)

    val myTicket = input[1].split("\n")[1].split(",").map { it.toLong() }

    var todoReq = requirements.keys.toList()
    val todoInd = requirements.keys.indices.toMutableList()
    val done = mutableMapOf<String, Int>()

    while (todoReq.isNotEmpty()) {
        println(todoReq)
        todoReq = todoReq.filter { requirement ->
            val values = requirements[requirement] ?: error("")
            val working = mutableSetOf<Int>()
            for (i in todoInd)
                if (filteredTickets.all { it[i] in values })
                    working += i
            if (working.size == 1) {
                todoInd.remove(working.first())
                done[requirement] = working.first()
                return@filter false
            }
            true
        }
    }
    val important = done.filter { (k, _) -> "departure" in k }.map { (_, v) -> v }
    println(important.map { myTicket[it] }.reduce { a, i -> a * i })
}