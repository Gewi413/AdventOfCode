import java.util.*

object Day17 : Day(17) {
    val map = input.toIntMap()
    override fun main() {
        val goal = input[0].length - 1 to input.size - 1
        println(dijkstra(0 to 0, goal, 0, 3))
        println(dijkstra(0 to 0, goal, 4, 10))
    }

    private fun dijkstra(start: Point, end: Point, minMoves: Int, maxMoves: Int): Int {
        val cameFrom = mutableMapOf<State, State>()
        val distance = mutableMapOf(State(start, 0 to 0, 0) to 0)
        val openSet = PriorityQueue<State>(50) { a, b -> (distance[a] ?: 1000000) - (distance[b] ?: 1000000) }
        openSet += State(start, 0 to 0, 0)
        while (openSet.isNotEmpty()) {
            val current = openSet.poll()
            if (current.pos == end && current.movesDone in minMoves..maxMoves) {
                return distance[current]!!
            }
            openSet.remove(current)
            for (next in current.pos.neighbors()) {
                val dir = next - current.pos
                if (next !in map) continue
                if (dir == -current.dir) continue
                val newDist = (distance[current]!!) + map[next]!!
                val done = if (dir == current.dir) current.movesDone + 1 else 1
                if (done > maxMoves) continue
                if (current.movesDone < minMoves && dir != current.dir && current.dir != 0 to 0) continue
                val newState = State(next, dir, done)
                if (newDist < (distance[newState] ?: 2147483647)) {
                    cameFrom[newState] = current
                    distance[newState] = newDist
                    openSet.add(newState)
                }
            }
        }
        throw NotImplementedError()
    }

    data class State(val pos: Point, val dir: Point, val movesDone: Int)
}
