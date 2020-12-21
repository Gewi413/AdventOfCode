@file:Suppress("PackageDirectoryMismatch")

package day21

import java.io.File

fun main() {
    val input = File("challenges/day21").readLines()
    val ingredients = mutableListOf<Pair<MutableList<String>, MutableList<String>>>()
    val allergens = mutableSetOf<String>()
    for (line in input) {
        val (ing, aller) = line.split(" (contains ")
        val allergen = aller.dropLast(1).split(", ").toMutableList()
        ingredients += Pair(ing.split(" ").toMutableList(), allergen)
        allergens += allergen
    }
    val safe = ingredients.map { it.first }.flatten().toMutableSet()
    for (allergen in allergens) {
        var possible = safe.toSet()
        for ((ingredient, aller) in ingredients)
            if (allergen in aller)
                possible = possible intersect ingredient
        safe -= possible
    }
    println(ingredients.map { it.first }.flatten().filter { it in safe }.count())

    ingredients.map { it.first }.forEach { list -> list.removeIf { it in safe } }
    val available = ingredients.map { it.first }.flatten().toMutableSet()
    val hazard = mutableMapOf<String, String>()
    while (hazard.size != allergens.size)
        for (allergen in allergens) {
            var possible = available.toSet()
            for ((ingredient, aller) in ingredients) {
                if (allergen !in aller) continue
                possible = possible intersect ingredient
            }
            if (possible.size == 1) {
                available -= possible.first()
                hazard[possible.first()] = allergen
            }
        }
    println(hazard.map { (k, v) -> Pair(k, v) }.sortedBy { (_, v) -> v }.joinToString(",") { it.first })
}