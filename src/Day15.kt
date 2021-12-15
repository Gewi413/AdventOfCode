import java.util.*
import kotlin.math.abs

object Day15 : Day(15) {
    override fun main() {
        val space = input.toIntMap()
        println(solve(space))

        val width = space.maxOf { it.key.first } + 1
        val height = space.maxOf { it.key.second } + 1
        val horizontal = (0..4).map { x ->
            space.entries.map {
                Pair(x * width + it.key.first to it.key.second, (it.value - 1 + x) % 9 + 1)
            }
        }.flatten().toMap()
        val newMap = (0..4).map { y ->
            horizontal.entries.map {
                Pair(it.key.first to y * height + it.key.second, (it.value - 1 + y) % 9 + 1)
            }
        }.flatten().toMap()
        println(solve(newMap))
    }

    private fun solve(space: Map<Pair<Int, Int>, Int>): Int {
        val width = space.maxOf { it.key.first }
        val height = space.maxOf { it.key.second }
        val path = aStar(0 to 0, width to height, space)
        return path.sumBy { space[it]!! } - 1
    }

    private fun aStar(start: Point, end: Point, space: Map<Point, Int>): List<Point> {
        val compare: (a: Point, b: Point) -> Int = { (a, b), (c, d) -> (a shl 16 xor b) - (c shl 16 xor d) }
        val f = TreeMap<Point, Int>(compare)
        val g = TreeMap<Point, Int>(compare)
        f[start] = hCost(start, end)
        g[start] = 0
        val openSet = PriorityQueue<Point> { a, b -> f[a]!! - f[b]!! }
        openSet.add(start)
        val cameFrom = TreeMap<Point, Point>(compare)
        while (openSet.isNotEmpty()) {
            val current = openSet.poll()
            if (current == end) return reconstructPath(cameFrom, current)
            for (next in current.neighbors()) {
                if (next !in space) continue
                val newG = (g[current] ?: 2147483647) + space[current]!!
                if (newG < (g[next] ?: 2147483647)) {
                    cameFrom[next] = current
                    g[next] = newG
                    f[next] = newG + hCost(next, end)
                    if (next !in openSet) openSet.add(next)
                }
            }
        }
        return emptyList()
    }

    private fun reconstructPath(cameFrom: Map<Point, Point>, from: Point): List<Point> {
        var current = from
        val path = mutableListOf(current)
        while (current in cameFrom) {
            current = cameFrom[current]!!
            path += (current)
        }
        return path.reversed()
    }

    private fun hCost(start: Point, end: Point) =
        abs(start.first - end.first) + abs(start.second - end.second)
}