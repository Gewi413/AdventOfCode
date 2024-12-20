import kotlin.math.abs

object Day20 : Day(20) {
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
        val maxX = map.maxOf { it.key.first }
        val maxY = map.maxOf { it.key.second }
        val blockers = map.filter { it.value == 1 }.keys.toSet()
        val default = aStar(start, end, blockers)

        println(
            blockers.parallelStream()
                .filter { it.first in 1 until maxX && it.second in 1 until maxY }
                .filter { default - aStar(start, end, blockers - it) >= 100 }.count())
    }


    private fun aStar(start: Point, end: Point, blockers: Set<Point>): Int {
        val openSet = mutableListOf(start)
        val cameFrom = mutableMapOf<Point, Point>()
        val g = mutableMapOf(start to 0)
        val f = mutableMapOf(start to hCost(start, end))
        while (openSet.isNotEmpty()) {
            val current = openSet.minByOrNull { f[it] ?: 2147483647 }!!
            if (current == end) {
                break
            }
            openSet.remove(current)
            for (neighbor in current.neighbors()) {
                if (neighbor in blockers) {
                    continue
                }

                val newG = (g[current] ?: 2147483647) + 1
                if (newG <= (g[neighbor] ?: 2147483647)) {
                    cameFrom[neighbor] = current
                    g[neighbor] = newG
                    f[neighbor] = newG + hCost(neighbor, end)
                    if (neighbor !in openSet) {
                        openSet.add(neighbor)
                    }
                }
            }
        }
        return g[end] ?: -1
    }

    private fun hCost(start: Point, end: Point) =
        abs(start.first - end.first) + abs(start.second - end.second)
}