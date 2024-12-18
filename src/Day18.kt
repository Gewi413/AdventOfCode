import kotlin.math.abs

object Day18 : Day(18) {
    override fun main() {
        val blockers = input.toPoint()
        val path = aStar(0 to 0, 70 to 70, blockers.take(1024).toSet())
        println(path.size -1)
        for(i in blockers.size downTo 0) {
            val p = aStar(0 to 0, 70 to 70, blockers.take(i).toSet())
            if(p.size > 1) {
                println(blockers[i])
                break
            }
        }
    }

    private fun aStar(start: Point, end: Point, blockers: Set<Point>): List<Point> {
        var i = 1
        val openSet = mutableListOf(start)
        val cameFrom = mutableMapOf<Point, Point>()
        val g = mutableMapOf(start to 0)
        val f = mutableMapOf(start to hCost(start, end))
        while (openSet.isNotEmpty()) {
            if (i++ % 100000 == 0) println("$i steps done")
            val current = openSet.minByOrNull { f[it] ?: 2147483647 }!!
            if (current == end) {
                break
            }
            openSet.remove(current)
            for (neighbor in current.neighbors()) {
                if (neighbor.first !in start.first..end.first
                    || neighbor.second !in start.second..end.second
                    || neighbor in blockers
                ) {
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
        return reconstructPath(end, cameFrom)
    }

    private fun reconstructPath(
        end: Point,
        cameFrom: Map<Point, Point>
    ): List<Point> {
        val visited = mutableListOf<Point>()
        var curr: Point? = end
        while (curr != null) {
            visited += curr
            curr = cameFrom[curr]
        }
        return visited
    }

    private fun hCost(start: Point, end: Point) =
        abs(start.first - end.first) + abs(start.second - end.second)

}