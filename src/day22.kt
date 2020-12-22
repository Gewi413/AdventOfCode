@file:Suppress("PackageDirectoryMismatch")

package day22

import java.io.File

fun main() {
    val (p1original, p2original) = File("challenges/day22").readText().split("\n\n")
        .map { it.split("\n").drop(1).map { card -> card.toInt() } }
    val p1 = p1original.toMutableList()
    val p2 = p2original.toMutableList()
    while (true) {
        if (p1.isEmpty() || p2.isEmpty()) break
        val a = p1.removeAt(0)
        val b = p2.removeAt(0)
        if (a > b) p1 += listOf(a, b)
        else p2 += listOf(b, a)
    }
    println(p2.reversed().foldIndexed(0L) { i, acc, card -> acc + card * (i + 1) })

    p2.clear()
    p1.addAll(p1original)
    p2.addAll(p2original)

    playRecursive(p1, p2)
    if (p2.isEmpty()) p2 += p1
    println(p2.reversed().foldIndexed(0L) { i, acc, card -> acc + card * (i + 1) })
}

fun playRecursive(p1: MutableList<Int>, p2: MutableList<Int>): Boolean {
    val playedGames = mutableListOf<Pair<List<Int>, List<Int>>>()
    while (true) {
        val game = Pair(p1.toList(), p2.toList())
        if (game in playedGames) return true
        playedGames += game
        if (p2.isEmpty()) return true
        if (p1.isEmpty()) return false
        val a = p1.removeAt(0)
        val b = p2.removeAt(0)
        val winner = if (p1.size >= a && p2.size >= b)
            playRecursive(p1.slice(0 until a).toMutableList(), p2.slice(0 until b).toMutableList())
        else a > b
        if (winner) p1 += listOf(a, b)
        else p2 += listOf(b, a)
    }
}