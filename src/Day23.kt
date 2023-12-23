import java.util.*

object Day23 : Day(23) {
    override fun main() {
        val map = input.map { it.replaceAll(".0", "#1", "^2", "<3", "v4", ">5") }.toIntMap().filter { it.value != 1 }
        val end = input[0].lastIndex - 1 to input.lastIndex
        println(dijkstra(1 to 0, end, map))
    }

    private fun dijkstra(start: Point, end: Point, map: Map<Point, Int>): Int {
        val cameFrom = mutableMapOf<Point, Point>()
        val distance = mutableMapOf(start to 0)
        val openSet = PriorityQueue<Point>(50) { a, b -> (distance[a] ?: 1000000) - (distance[b] ?: 1000000) }
        openSet += start
        while (openSet.isNotEmpty()) {
            val current = openSet.poll()
            val path = reconstructPath(cameFrom, start, current)
            val neighbors = when (map[current]) {
                0 -> current.neighbors()
                2 -> listOf(current + (0 to -1))
                3 -> listOf(current + (-1 to 0))
                4 -> listOf(current + (0 to 1))
                5 -> listOf(current + (1 to 0))
                else -> throw NotImplementedError()
            }
            for (next in neighbors) {
                if (next !in map) continue
                if (next in path) continue
                val newDist = (distance[current]!!) + 1
                if (newDist > (distance[next] ?: 0)) {
                    cameFrom[next] = current
                    distance[next] = newDist
                    if (next !in openSet) openSet.add(next)
                }
            }
        }

       return distance[end]!!
    }

    private fun reconstructPath(cameFrom: Map<Point, Point>, start: Point, from: Point): List<Point> {
        var current = from
        val path = mutableListOf(from)
        while (current != start) {
            current = cameFrom[current]!!
            path += current
        }
        return path
    }
}