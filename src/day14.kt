@file:Suppress("PackageDirectoryMismatch")

package day14

import java.io.File

fun main() {
    val input = File("challenges/day14").readLines()
    var mask = ""
    val memory = mutableMapOf(0L to 0L)
    for (line in input)
        if (line.startsWith("mask")) mask = line.drop(7)
        else {
            var (address, value) = line.drop(4).split("] = ").map { it.toLong() }
            for ((i, bit) in mask.reversed().withIndex()) {
                when (bit) {
                    '0' ->
                        value = value and (Long.MAX_VALUE xor (1L shl i))
                    '1' ->
                        value = value or (1L shl i)
                }
            }
            memory[address] = value
        }
    println(memory.values.sum())

    memory.clear()
    for (line in input)
        if (line.startsWith("mask")) mask = line.drop(7)
        else {
            var (address, value) = line.drop(4).split("] = ").map { it.toLong() }
            for ((i, bit) in mask.reversed().withIndex())
                if (bit == '1') address = address or (1L shl i)

            val combinations = mutableSetOf(address)
            for ((i, bit) in mask.reversed().withIndex()) {
                val copy = combinations.toSet()
                if (bit == 'X') for (combination in copy)
                    combinations += combination xor (1L shl i)
            }
            for (combination in combinations) memory[combination] = value
        }
    println(memory.values.sum())
}
