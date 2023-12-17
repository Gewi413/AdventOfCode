object Day17 : Day(17) {
    val map = input.toIntMap()
    override fun main() {
        val goal = input[0].length - 1 to input.size - 1
        val path = dijkstra(0 to 0, goal)
        map.print(path.toSet())
        //println(path.sumBy { map[it]!! })
        // ..800
    }

    private fun dijkstra(start: Point, end: Point): List<Point> {
        val openSet = mutableSetOf(State(start, 0 to 0, 0))
        val cameFrom = mutableMapOf<State, State>()
        val distance = mutableMapOf(State(start, 0 to 0, 0) to 0)
        while (openSet.isNotEmpty()) {
            val current = openSet.minByOrNull { distance[it]!! }!!
            //openSet.map { it.first }.eachCount().print(reconstructPath(cameFrom, current).toSet())
            // println(current)
            // map.print(reconstructPath(cameFrom, current).toSet())
            if (current.pos == end) {
                println(distance[current])
                return reconstructPath(cameFrom, current)
            }
            openSet.remove(current)
            for (next in current.pos.neighbors()) {
                val dir = next - current.pos
                if (next !in map) continue
                if (dir == -current.dir) continue
                val newDist = (distance[current]!!) + map[next]!!
                val left = if(dir == current.dir) current.movesLeft - 1 else 3
                if(left <= 0) continue
                val newState = State(next, dir, left)
                if (newDist < (distance[newState] ?: 2147483647)) {
                    cameFrom[newState] = current
                    distance[newState] = newDist
                    openSet.add(newState)
                }
            }
        }
        throw NotImplementedError()
    }

    private fun reconstructPath(cameFrom: Map<State, State>, from: State): List<Point> {
        var current = from
        val path = mutableListOf(current.pos)
        while (current.dir != 0 to 0) {
            current = cameFrom[current]!!
            path += current.pos
        }
        return path.reversed()
    }

    data class State(val pos: Point, val dir: Point, val movesLeft: Int)
}
