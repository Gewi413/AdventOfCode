@file:Suppress("PackageDirectoryMismatch")

package day20

import java.io.File

fun main() {
    val input = File("challenges/day20").readText().split("\n\n")
    val tiles = mutableListOf<Tile>()
    for (tile in input) {
        val (idText, square) = tile.split(":\n")
        val id = idText.drop(5).toInt()
        tiles += Tile(square.split("\n").map { it.toMutableList() }.toMutableList(), id)
    }
    for (tile in tiles)
        tile.calculateNeighbors(tiles)
    println(tiles.filter { it.neighbors.size == 2 }.fold(1L) { acc, i -> acc * i.id })

    val grid = mutableMapOf<Pair<Int, Int>, Tile>()
    var tile = tiles.first { it.neighbors.size == 2 }
    val available = tiles.toMutableSet()
    for (x in 0..10) {
        available -= tile
        grid[Pair(x, 0)] = tile
        tile = tile.neighbors.first { it.neighbors.size <= 3 && it in available }
    }
    available -= tile
    grid[Pair(11, 0)] = tile
    tile = tiles.first { it.neighbors.size == 2 }
    for (y in 0..10) {
        available -= tile
        grid[Pair(0, y)] = tile
        tile = tile.neighbors.first { it.neighbors.size <= 3 && it in available }
    }
    available -= tile
    grid[Pair(0, 11)] = tile
    for (x in 1..11) for (y in 1..11) {
        tile =
            (grid[Pair(x - 1, y)]!!.neighbors intersect grid[Pair(x, y - 1)]!!.neighbors).first { it in available }
        available -= tile
        grid[Pair(x, y)] = tile
    }

    tile = grid[Pair(0, 0)]!!
    val next = grid[Pair(0, 1)]!!
    next.flip()
    next.rotateR()
    tile.rotateR()
    for (i in 0 until 8) {
        tile.rotateR()
        if (Tile.matches(tile.down, next)) break
        if (Tile.matches(tile.down.reversed(), next)) tile.flip()
    }
    for (y in 1..11) {
        tile = grid[Pair(0, y)]!!
        val prev = grid[Pair(0, y - 1)]!!
        while (true) {
            tile.rotateR()
            if (Tile.matches(tile.up.reversed(), prev)) tile.flip()
            if (Tile.matches(tile.up, prev)) break
        }
    }
    for (x in 1..11) for (y in 0..11) {
        val prev = grid[Pair(x - 1, y)]!!
        tile = grid[Pair(x, y)]!!
        while (true) {
            tile.rotateR()
            if (Tile.matches(tile.left, prev)) break
            if (Tile.matches(tile.left.reversed(), prev)) tile.flip()
        }
    }

    val map = mutableListOf<MutableList<Char>>()
    for (y in 0 until 12) {
        for (i in 0 until 8)
            map.plusAssign("".toCharArray().toMutableList())
        for (x in 0 until 12) {
            tile = grid[Pair(x, y)]!!
            for ((i, line) in tile.text.drop(1).dropLast(1).withIndex())
                map[y * 8 + i].plusAssign(line.drop(1).dropLast(1))
        }
    }
    val sea = Tile(map, 0)
    val roughness = sea.text.sumBy { it.count { c -> c == '#' } }
    var monsters: Int
    while(true) {
        monsters = sea.countMonsters()
        if(monsters != 0) break
        sea.rotateR()
        monsters = sea.countMonsters()
        if(monsters != 0) break
        sea.flip()
    }
    println(roughness - monsters * Tile.monster.size)
}

private data class Tile(var text: MutableList<MutableList<Char>>, val id: Int) {
    val up get() = text.first().toList()
    val down get() = text.last()
    val left get() = text.map { it.first() }
    val right get() = text.map { it.last() }
    val neighbors = mutableSetOf<Tile>()

    fun rotateR() {
        val copy = text.map { it.toList() }
        for (x in text.indices) for (y in text.indices) {
            text[x][y] = copy[copy.size - y - 1][x]
        }
    }

    fun flip() {
        text = text.reversed().toMutableList()
    }

    private fun match(other: Tile): Boolean {
        if (this == other) return false
        if (listOf(up, down, left, right).map { Pair(it, it.reversed()) }.flatMap { (a, b) -> listOf(a, b) }
                .any { matches(it, other) }) neighbors += other
        else return false
        return true
    }

    fun calculateNeighbors(tiles: List<Tile>): Int {
        for (other in tiles) match(other)
        return neighbors.size
    }

    fun countMonsters(): Int {
        var out = 0
        for (x in 0 until text.first().size - monsterDimensions.first)
            map@ for (y in 0 until text.size - monsterDimensions.second) {
                for ((dx, dy) in monster)
                    if (text[y + dy][x + dx] != '#') continue@map
                out++
            }


        return out
    }

    fun toText() = text.joinToString("\n", "", "\n")

    override fun toString() = "Tile(up='$up', down='$down', left='$left', right='$right', neighbors=${neighbors.size})"

    companion object {
        fun matches(side: List<Char>, other: Tile) = when (side) {
            other.up -> true
            other.down -> true
            other.left -> true
            other.right -> true
            else -> false
        }

        val seaMonster = """
                              # 
            #    ##    ##    ###
             #  #  #  #  #  #   """.trimIndent()
        val monsterDimensions = Pair(seaMonster.split("\n").first().length, seaMonster.split("\n").size)
        val monster: List<Pair<Int, Int>> = run {
            val positions = mutableListOf<Pair<Int, Int>>()
            for ((y, line) in seaMonster.split("\n").withIndex()) for ((x, char) in line.withIndex()) {
                if (char == '#') positions += Pair(x, y)
            }
            return@run positions.toList()
        }
    }
}