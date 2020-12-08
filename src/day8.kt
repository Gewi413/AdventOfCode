@file:Suppress("PackageDirectoryMismatch")

package day8

import java.io.File

fun main() {
    val input = File("challenges/day8").readLines()
    val instructions = input.toMutableList()
    var ptr = 0
    var acc = 0
    while (true) {
        val (instruction, argument) = instructions[ptr].split(" ")
        instructions[ptr] = "stp 0"
        when (instruction) {
            "acc" -> acc += argument.toInt()
            "jmp" -> ptr += argument.toInt() - 1
            "stp" -> break
        }
        ptr++
    }
    println(acc)

    outer@ for (i in input.indices) {
        val copy = input.toMutableList()
        if (copy[i].startsWith("acc")) continue
        val (ins, arg) = copy[i].split(" ")
        copy[i] = when (ins.split(" ")[0]) {
            "jmp" -> "nop $arg"
            "nop" -> "jmp $arg"
            else -> "stp 1"
        }
        ptr = 0
        acc = 0
        while (true) {
            if (ptr == input.size) break
            if (ptr < 0) continue@outer
            val (instruction, argument) = copy[ptr].split(" ")
            copy[ptr] = "stp 0"
            when (instruction) {
                "acc" -> acc += argument.toInt()
                "jmp" -> ptr += argument.toInt() - 1
                "stp" -> continue@outer
            }
            ptr++
        }
        println(acc)
        break@outer
    }
}