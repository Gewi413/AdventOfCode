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
        parts[i] = evaluateA(exp.drop(1).dropLast(1)).toString()

    for ((i, exp) in parts.withIndex()) when (exp) {
        "+" -> parts[i + 1] = (parts[i - 1].toLong() + parts[i + 1].toLong()).toString()
        "*" -> parts[i + 1] = (parts[i - 1].toLong() * parts[i + 1].toLong()).toString()
    }
    return parts.last().toLong()
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