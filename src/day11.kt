@file:Suppress("PackageDirectoryMismatch")

package day11

import java.io.File

fun main() {
    val input = File("challenges/day11").readLines().map { it.toList() }
    var state = input.toList()
    while (true) {
        val copy = state.toMutableList().map { it.toMutableList() }
        for (y in copy.indices) for (x in state[0].indices) {
            var neighbors = 0
            for (dy in -1..1) for (dx in -1..1) {
                try {
                    if (state[y + dy][x + dx] == '#')
                        neighbors++
                } catch (_: IndexOutOfBoundsException) {
                    // if outside, no neighbor
                }
            }
            when (state[y][x]) {
                '#' -> {
                    if (neighbors >= 5) copy[y][x] = 'L'
                }
                'L' -> {
                    if (neighbors == 0) copy[y][x] = '#'
                }
            }
        }
        if (copy == state) break
        state = copy
    }
    println(state.map { it.count { c -> c == '#' } }.sum())

    state = input.toList()
    while (true) {
        val copy = state.toMutableList().map { it.toMutableList() }
        for (y in copy.indices) for (x in state[0].indices) {
            var neighbors = 0
            for (dy in -1..1) for (dx in -1..1) for (sight in 1..state[0].size) {
                try {
                    when (state[y + dy * sight][x + dx * sight]) {
                        '#' -> {
                            neighbors++
                            break
                        }
                        'L' -> break
                    }
                } catch (_: IndexOutOfBoundsException) {
                    // if outside, no neighbor; continue to other direction
                    break
                }
            }
            when (state[y][x]) {
                '#' -> {
                    if (neighbors >= 6) copy[y][x] = 'L'
                }
                'L' -> {
                    if (neighbors == 0) copy[y][x] = '#'
                }
            }
        }
        if (copy == state) break
        state = copy
    }
    println(state.map { it.count { c -> c == '#' } }.sum())
}