import java.util.*
import kotlin.math.abs

object Day12 : Day(12) {
    override fun main() {
        var start = 0 to 0
        var end = 0 to 0
        val space = input
            .map { it.replace("S", "9").replace("E", "0") }
            .toIntMap(36)
            .mapValues { (k, v) ->
                when (v) {
                    9 -> {
                        start = k
                        0
                    }

                    0 -> {
                        end = k
                        25
                    }

                    else -> v - 10
                }
            }
        val distances = space.mapNotNull { (k, v) ->
            if (v != 0) null
            else aStar(k, end, space)?.size?.let { k to it }
        }.toMap()

        println(distances[start])
        println(distances.minOf { (_, v) -> v })
    }

    // stolen from 2021/15
    private fun aStar(start: Point, end: Point, space: Map<Point, Int>): List<Point>? {
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
                if (space[next]!! > space[current]!! + 1) continue // I thought kotlin had smart checking (next in space, thus no !! needed)
                val newG = (g[current] ?: 2147483647) + 1
                if (newG < (g[next] ?: 2147483647)) {
                    cameFrom[next] = current
                    g[next] = newG
                    f[next] = newG + hCost(next, end)
                    if (next !in openSet) openSet.add(next)
                }
            }
        }
        return null
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