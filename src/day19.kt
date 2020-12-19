@file:Suppress("PackageDirectoryMismatch")

package day19

import java.io.File

fun main() {
    val (rules, messages) = File("challenges/day19").readText().split("\n\n").map { it.split("\n") }
    val realRules = mutableMapOf<Int, String>()
    for (rule in rules) {
        val (id, text) = rule.split(": ")
        realRules[id.toInt()] = text
    }
    val rule0 = "^" + expand(realRules, 0).replace(" |\\(\"([ab])\"\\)".toRegex(), "$1") + "$"
    println(messages.filter { it.matches(rule0.toRegex()) }.count())

    realRules[8] = "42 | 42 8"
    realRules[11] = "42 31 | 42 11 31"
    val maxDepth = realRules.values.maxByOrNull { it.length }!!.length
    val rule1 = "^" + expand(realRules, 0, maxDepth).replace(" |\\(\"([ab])\"\\)".toRegex(), "$1") + "$"
    println(messages.filter { it.matches(rule1.toRegex()) }.count())
}

private fun expand(realRules: MutableMap<Int, String>, id: Int, maxDepth: Int = Int.MAX_VALUE): String {
    if (maxDepth <= 0) return ""
    val rule = realRules[id]!!
    if (rule.startsWith('"')) return rule
    var out = ""
    for (partialRule in rule.split("|")) {
        for (inside in partialRule.trim().split(" ").map { it.toInt() }) {
            out += "(" + expand(realRules, inside, maxDepth - 1) + ") "
        }
        out += "|"
    }
    return out.dropLast(2)
}
