import kotlin.math.abs
import kotlin.math.min

object Day16 : Day(16) {
    override fun main() {
        val map = input.map {
            it
                .replace('#', '1')
                .replace('.', '0')
                .replace('S', '2')
                .replace('E', '3')
        }.toIntMap()
        val start = map.entries.single { it.value == 2 }.key
        val end = map.entries.single { it.value == 3 }.key
        val (a,b) = aStar(start to (0 to 1), end, map)
        println(a)
        println(b)
    }

    private fun aStar(start: Pair<Point, Point>, end: Point, map: Map<Point, Int>): Pair<Int, Int> {
        var i = 1
        val openSet = mutableListOf(start)
        val cameFrom = mutableMapOf<Pair<Point, Point>, Set<Pair<Point, Point>>>()
        val g = mutableMapOf(start to 0)
        val f = mutableMapOf(start to hCost(start.first, end))
        var res = 2147483647
        while (openSet.isNotEmpty()) {
            if (i++ % 100000 == 0) println("$i steps done")
            val current = openSet.minByOrNull { f[it] ?: 2147483647 }!!
            if (current.first == end) {
                res = min(res, g[current]!!)
            }
            openSet.remove(current)
            for (neighbor in current.first.neighbors()) {
                if (map[neighbor] == 1) {
                    continue
                }
                val dir = neighbor - current.first
                var newG = (g[current] ?: 2147483647) + 1
                if (dir != current.second) {
                    newG += 1000
                }
                if (newG <= (g[neighbor to dir] ?: 2147483647)) {
                    val toAdd = neighbor to dir
                    if (newG != g[neighbor to dir]) {
                        cameFrom[toAdd] = emptySet()
                    }
                    cameFrom[toAdd] = (cameFrom[toAdd] ?: emptySet()) + current
                    g[toAdd] = newG
                    f[toAdd] = newG + hCost(neighbor, end)
                    if (toAdd !in openSet) {
                        openSet.add(toAdd)
                    }
                }
            }
        }
        val ends = (0 to 0)
            .neighbors()
            .map { end to it }
            .groupBy { g[it] }
            .minByOrNull { it.key ?: 2147483647 }!!
            .value
        val p = reconstructPath(ends, cameFrom)
        return p.size to res
    }

    private fun reconstructPath(
        end: List<Pair<Point, Point>>,
        cameFrom: MutableMap<Pair<Point, Point>, Set<Pair<Point, Point>>>
    ): Set<Point> {
        val visited = mutableSetOf<Pair<Point, Point>>()
        val todo = end.toMutableList()
        while (todo.isNotEmpty()) {
            val curr = todo.removeFirst()
            if (curr in visited) {
                continue
            }
            visited += curr
            todo += cameFrom[curr] ?: continue
        }
        return visited.map { it.first }.toSet()
    }

    private fun hCost(start: Point, end: Point) =
        abs(start.first - end.first) + abs(start.second - end.second)
}