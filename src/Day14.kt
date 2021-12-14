object Day14 : Day(14) {
    override fun main() {
        val reactions = input.drop(2).map { Pair(it[0] to it[1], it[6]) }
        val counts = input[0].toList().eachCount().mapValues { it.value.toLong() }.toMutableMap()
        var bonds = input[0].zipWithNext().eachCount().mapValues { it.value.toLong() }
        for (i in 1..40) {
            val copy = mutableMapOf<Pair<Char, Char>, Long>()
            for ((from, to) in reactions) {
                val occurrences = bonds[from] ?: 0L
                counts[to] = (counts[to] ?: 0L) + occurrences
                val a = from.first to to
                val b = to to from.second
                copy[a] = (copy[a] ?: 0) + occurrences
                copy[b] = (copy[b] ?: 0) + occurrences
            }
            bonds = copy
            if (i == 10 || i == 40) println(counts.values.maxOrNull()!! - counts.values.minOrNull()!!)
        }
    }
}