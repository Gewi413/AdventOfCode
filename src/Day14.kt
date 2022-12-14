object Day14 : Day(14) {
    override fun main() {
        val walls = input.flatMap { line ->
            line
                .split(" -> ")
                .toPoint()
                .zipWithNext { a, b ->
                    if (a.first == b.first) (a.second range b.second).map { a.first to it }
                    else (a.first range b.first).map { it to a.second }
                }.flatten()
        }.toSet()

        val floor = walls.maxOf { it.second } + 2
        val wallsB = walls + (-1000..1000).map { it to floor }

        println(exec(walls, floor))
        println(exec(wallsB, floor))
    }

    private fun exec(initial: Set<Pair<Int, Int>>, floor: Int) = doUntilSettled(initial) { walls ->
        var pos = 500 to 0
        val attempts = listOf(0 to 1, -1 to 1, 1 to 1)
        while (true) {
            if (pos.second > floor) return@doUntilSettled walls
            attempts.firstOrNull { pos + it !in walls }?.let { pos += it } ?: break
        }
        walls + pos
    }.size - initial.size

    private infix fun Int.range(to: Int) = if (to >= this) this..to else to..this
}