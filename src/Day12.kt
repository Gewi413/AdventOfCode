object Day12 : Day(12) {
    override fun main() {
        val nodes = mutableMapOf<String, MutableList<String>>()
        input.forEach { line ->
            val (a, b) = line.split("-")
            if (a in nodes) nodes[a]!! += b
            else nodes[a] = mutableListOf(b)
            if (b in nodes) nodes[b]!! += a
            else nodes[b] = mutableListOf(a)
        }
        println(explore(nodes, "start"))
        println(explore(nodes, "start", 2))
    }

    private fun explore(
        map: Map<String, List<String>>,
        from: String,
        maxExplorations: Int = 1,
        already: List<String> = listOf("start")
    ): Int {
        if (from !in map) TODO("corrupted map")
        if (from == "end") return 1
        val new = map[from]!!.mapNotNull { next ->
            val visits = already.count { it == next }
            if (next[0] in 'a'..'z' && visits >= maxExplorations) return@mapNotNull null
            explore(map, next, if (next[0] in 'a'..'z' && visits == 1) 1 else maxExplorations, already + from)
        }
        return new.sum()
    }
}