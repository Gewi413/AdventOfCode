@file:Suppress("PackageDirectoryMismatch")

package day17

import java.io.File

fun main() {
    val input = File("challenges/day17").readLines()
    var space = mutableSetOf<Triple<Int, Int, Int>>()
    var spaceB = mutableSetOf<Quadruplet>()
    for ((i, row) in input.withIndex()) for ((j, cube) in row.withIndex())
        if (cube == '#') {
            space.plusAssign(Triple(j, i, 0))
            spaceB.plusAssign(Quadruplet(j, i, 0, 0))
        }
    for (i in 1..6) {
        val new = mutableSetOf<Triple<Int, Int, Int>>()
        val neighbors = mutableMapOf<Triple<Int, Int, Int>, Int>()
        for (cell in space)
            for (x in -1..1) for (y in -1..1) for (z in -1..1) {
                val pos = Triple(x + cell.first, y + cell.second, z + cell.third)
                if (pos == cell) continue
                neighbors[pos] = (neighbors[pos] ?: 0) + 1
            }
        for ((cell, neighbor) in neighbors)
            when (neighbor) {
                3 -> new += cell
                2 -> if (cell in space) new += cell
            }
        space = new
    }
    println(space.size)

    for (i in 1..6) {
        val new = mutableSetOf<Quadruplet>()
        val neighbors = mutableMapOf<Quadruplet, Int>()
        for (cell in spaceB)
            for (x in -1..1) for (y in -1..1) for (z in -1..1) for (w in -1..1) {
                val pos = cell + Quadruplet(x, y, z, w)
                if (pos == cell) continue
                neighbors[pos] = (neighbors[pos] ?: 0) + 1
            }
        for ((cell, neighbor) in neighbors)
            when (neighbor) {
                3 -> new += cell
                2 -> if (cell in spaceB) new += cell
            }
        spaceB = new
    }

    println(spaceB.size)
}

private data class Quadruplet(val x: Int, val y: Int, val z: Int, val w: Int) {
    operator fun plus(other: Quadruplet) =
        Quadruplet(this.x + other.x, this.y + other.y, this.z + other.z, this.w + other.w)
}