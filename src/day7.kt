import java.io.File

fun main() {
    val input = File("challenges/day7").readLines()
    val tree = mutableMapOf<String, List<Pair<Int, String>>>()
    for (line in input) {
        val (outer, inner) = line.split(" bags contain")
        tree[outer] = inner.split(", ").map {
            val filtered = it.replace(Regex("bags?\\.?"), "").trim()
            val number = if (filtered != "no other")
                filtered.first().toInt() - 0x30
            else 0
            val bag = filtered.replace(Regex("\\d"), "").trim()
            Pair(number, bag)
        }
    }

    val goal = mutableSetOf("shiny gold")
    var copy = setOf<String>()
    while (copy != goal) {
        copy = goal.toSet()
        for ((k, v) in tree)
            if (v.any { it.second in goal })
                goal += k
    }
    println(goal.size - 1)
    var bags = 0
    val queue = mutableListOf(Pair(1, "shiny gold"))
    while (queue.isNotEmpty()) {
        val item = queue.first()
        queue.removeAt(0)
        if (item.first != 0)
            for (bag in tree[item.second]!!) {
                bags += bag.first * item.first
                queue += Pair(bag.first * item.first, bag.second)
            }
    }
    println(bags)
}