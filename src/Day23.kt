object Day23 : Day(23) {
    override fun main() {
        val trans = mutableMapOf<String, Set<String>>()
        input.forEach { line ->
            val (a, b) = line.split("-")
            trans[a] = (trans[a] ?: setOf()) + b
            trans[b] = (trans[b] ?: setOf()) + a
        }

        val clusters = buildClusters(setOf(), trans)
        println(clusters.count { it.size == 3 && it.any{pc -> pc.startsWith('t')}})
        println(clusters.maxByOrNull { it.size }!!.sorted().joinToString(","))
    }

    private val cache = mutableMapOf<Set<String>, Set<Set<String>>>()

    private fun buildClusters(contained: Set<String>, map: Map<String, Set<String>>): Set<Set<String>> {
        if (contained in cache) {
            return cache[contained]!!
        }
        val possibilities = if (contained.isEmpty()) map.keys else map[contained.first()]!!
        val res = mutableSetOf(contained)
        for (poss in possibilities) {
            if (contained.all { poss in map[it]!! }) {
                res += buildClusters(contained + poss, map)
            }
        }
        cache[contained] = res
        return res
    }
}
