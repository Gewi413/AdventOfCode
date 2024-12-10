object Day10 : Day(10) {
    override fun main() {
        val map = input.toIntMap()
        val (a, b) = map.entries.map { (k, v) ->
            if (v != 0) {
                return@map null
            }
            val todo = mutableSetOf(k)
            val targets = mutableSetOf<Point>()
            val reachable = mutableMapOf<Point, MutableList<Point>>()
            while (todo.isNotEmpty()) {
                val curr = todo.first()
                todo.remove(curr)

                for (next in curr.neighbors()) {
                    if (map[curr]!! + 1 != map[next]) {
                        continue
                    }
                    reachable[next] = reachable[next] ?: mutableListOf()
                    reachable[next]!!.add(curr)
                    if (map[next] == 9) {
                        targets.add(next)
                        continue
                    }
                    todo.add(next)
                }
            }
            cache.clear()
            targets.size to targets.sumBy { reconstructPaths(it, reachable) }
        }
            .filterNotNull()
            .reduce { a, b -> a + b }
        println(a)
        println(b)
    }

    private val cache = mutableMapOf<Point, Int>()
    private fun reconstructPaths(from: Point, reachable: Map<Point, List<Point>>): Int {
        if (from !in reachable) {
            return 1
        }
        if (from in cache) {
            return cache[from]!!
        }

        val res = reachable[from]!!.sumBy { reconstructPaths(it, reachable) }
        cache[from] = res
        return res
    }
}