object Day16 : Day(16) {
    override fun main() {
        val (valves, connections) = input.map { line ->
            val parts = line.split(" ")
            val id = parts[1]
            val rate = parts[4].split("=")[1].dropLast(1).toInt()
            val connections = parts.drop(9).joinToString("").split(",")
            Valve(id, rate) to connections
        }.unzip()
        for ((valve, connection) in valves zip connections) {
            val others = connection.map { next -> valves.first { v -> v.id == next } }
            valve.connections += others
            for (other in others) {
                other.connections += valve
            }
        }
        start = valves.first { it.id == "AA" }
        println(partA())
        println(partB())
    }

    private lateinit var start: Valve

    private fun partA(position: Valve = start, timeRemaining: Int = 30, open: Set<Valve> = setOf()): Int =
        position.paths.filter { (valve, _) -> valve !in open }
            .map { (next, distance) -> next to timeRemaining - distance } // convert to List<Pair>
            .filter { (_, distance) -> distance > 0 }
            .maxOfOrNull { (next, distance) -> distance * next.rate + partA(next, distance, open + next) } ?: 0

    private data class State(val position: Valve, val timeRemaining: Int = 26, val open: Set<Valve> = setOf()) {
        fun advance(next: Valve, distance: Int) = State(next, distance, open + next)
    }

    private val cache = mutableMapOf<State, Int>()

    private fun partB(state: State = State(start), twice: Boolean = true): Int {
        if (state in cache) return cache[state]!!
        val next = state.position.paths
            .filter { (valve, _) -> valve !in state.open }
            .map { (next, distance) -> next to state.timeRemaining - distance }
            .filter { (_, distance) -> distance > 0 }
            .maxOfOrNull { (next, distance) ->
                distance * next.rate + partB(state.advance(next, distance), twice)
            }
            ?: if (twice) partB(state.advance(start, 26), false) else 0
        cache[state] = next
        return next
    }

    private data class Valve(val id: String, val rate: Int, val connections: MutableList<Valve> = mutableListOf()) {
        val paths by lazy {
            val todo = mutableSetOf(this)
            val done = mutableSetOf<Valve>()
            val costs = mutableMapOf(this to 1)
            while (todo.isNotEmpty()) {
                val current = todo.first()
                todo -= current
                done += current
                val nextTime = costs[current]!! + 1
                for (next in current.connections) {
                    if ((costs[next] ?: 1000) > nextTime) costs[next] = nextTime
                    if (next !in done) todo += next
                }
            }
            costs.filter { (valve, _) -> valve.rate != 0 }
        }

        override fun toString() = "Valve(id=$id, rate=$rate)"

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Valve

            if (id != other.id) return false
            if (rate != other.rate) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id.hashCode()
            result = 31 * result + rate
            return result
        }
    }
}