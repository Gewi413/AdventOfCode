import kotlin.math.max

object Day16 : Day(16) {
    override fun main() {
        // Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
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
        val start = valves.first { it.id == "AA" }
        //println(partA(start))
        println(partB(start, start))
    }

    private fun partA(position: Valve, timeRemaining: Int = 30, open: Set<Valve> = setOf()): Int =
        position.pathFind()
            .filter { (valve, _) -> valve.rate != 0 && valve !in open }
            .map { (next, distance) -> next to timeRemaining - distance } // convert to List<Pair>
            .filter { (_, distance) -> distance > 0 }
            .sortedBy { (next, distance) -> distance * next.rate }
            .maxOfOrNull { (next, distance) -> distance * next.rate + partA(next, distance, open + next) } ?: 0

    private fun partB(
        position: Valve, positionElephant: Valve,
        timeRemaining: Int = 26, timeElephant: Int = 26,
        open: Set<Valve> = setOf()
    ): Int {
        val nextMe = position.pathFind()
            .filter { (valve, _) -> valve.rate != 0 && valve !in open }
            .map { (next, distance) -> next to timeRemaining - distance } // convert to List<Pair>
            .filter { (_, distance) -> distance > 0 }
            .sortedBy { (next, distance) -> distance * next.rate }
            .maxOfOrNull { (next, distance) ->
                distance * next.rate + partB(next, positionElephant, distance, timeElephant, open + next)
            } ?: 0

        val nextE = positionElephant.pathFind()
            .filter { (valve, _) -> valve.rate != 0 && valve !in open }
            .map { (next, distance) -> next to timeElephant - distance } // convert to List<Pair>
            .filter { (_, distance) -> distance > 0 }
            .sortedBy { (next, distance) -> distance * next.rate }
            .maxOfOrNull { (next, distance) ->
                distance * next.rate + partB(position, next, timeRemaining, distance, open + next)
            } ?: 0

        return max(nextMe, nextE)
    }

    private data class Valve(val id: String, val rate: Int, val connections: MutableList<Valve> = mutableListOf()) {
        fun pathFind(): Map<Valve, Int> {
            val todo = mutableSetOf(this)
            val done = mutableSetOf<Valve>()
            val costs = mutableMapOf(this to 1)
            while (todo.isNotEmpty()) {
                val current = todo.first()
                todo -= current
                done += current
                val nextTime = costs[current]!! + 1
                for (next in current.connections) {
                    if ((costs[next] ?: 1000) > nextTime)
                        costs[next] = nextTime
                    if (next !in done) todo += next
                }
            }
            return costs
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