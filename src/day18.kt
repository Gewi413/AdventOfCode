@file:Suppress("PackageDirectoryMismatch")

package day18

import java.io.File

fun main() {
    val input = File("challenges/day18").readLines()
    var sumA = 0L
    var sumB = 0L
    for (expression in input) {
        sumA += evaluateA(expression)
        sumB += evaluateB(expression)
    }
    println(sumA)
    println(sumB)
}

private fun evaluateA(expression: String): Long {
    val expr = expression.trim().split(" ")
    var out = 0L
    var op = "+"
    var parenthesis = ""
    var par = 0
    for (thing in expr) when {
        thing.startsWith("(") -> {
            parenthesis += " $thing"
            par += thing.count { it == '(' }
        }
        thing.endsWith(")") -> {
            parenthesis += " $thing"
            par -= thing.count { it == ')' }
            if (par == 0) {
                val value = evaluateA(parenthesis.drop(2).dropLast(1))
                when (op) {
                    "+" -> out += value
                    "*" -> out *= value
                }
                parenthesis = ""
            }
        }
        par != 0 -> parenthesis += " $thing"
        thing == "+" || thing == "*" -> op = thing
        else -> when (op) {
            "+" -> out += thing.toInt()
            "*" -> out *= thing.toInt()
        }
    }
    return out
}

private fun evaluateB(expression: String): Long {
    if (expression.toIntOrNull() != null) return expression.toLong()
    val parts = mutableListOf("")
    var par = 0
    for (char in expression.trim()) {
        if (char == ' ' && par == 0) parts += ""
        else parts[parts.size - 1] = parts.last() + char
        if (char == '(') par++
        if (char == ')') par--
    }
    for ((i, exp) in parts.withIndex()) if (exp.startsWith("("))
        parts[i] = evaluateB(exp.drop(1).dropLast(1)).toString()
    for ((i, exp) in parts.withIndex()) if (exp == "+") {
        parts[i + 1] = (parts[i - 1].toLong() + parts[i + 1].toLong()).toString()
        parts[i - 1] = "0"
    }
    return parts.filter { it != "0" && it != "+" && it != "*" }.fold(1L) { acc, i -> acc * i.toLong() }
}