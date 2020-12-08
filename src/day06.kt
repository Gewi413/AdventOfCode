@file:Suppress("PackageDirectoryMismatch")

package day06

import java.io.File

fun main() {
    val input = File("challenges/day6").readText()
    println(input.split("\n\n").map { it.replace("\n", "").toSet().size }.sum())
    println(
        input.split("\n\n").map {
            it.replace("\n", "").toSet().toMutableSet()
                .filter { c -> it.split("\n").all { answer -> c in answer } }.size
        }.sum()
    )
}
