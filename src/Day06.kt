object Day06 : Day(6) {
    override fun main() {
        var pos = 0 to 0
        val blockers = input
            .map {
                it
                    .replace('.', '0')
                    .replace('#', '1')
                    .replace('^', '2')
            }
            .toIntMap()
            .entries
            .also { pos = it.single { v -> v.value == 2 }.key }
            .filter { it.value == 1 }
            .map { it.key }
            .toSet()

        val visited = run(pos, blockers)
        println(visited.first.size)
        println(visited.first.parallelStream().filter {
            it != pos // <- fuck this
                    && run(pos, blockers + it).second == Reason.Loop

        }.count())
    }

    private fun run(
        startpos: Pair<Int, Int>,
        blockers: Set<Point>
    ): Pair<Set<Point>, Reason> {
        val maxY = blockers.maxOf { it.second }
        val maxX = blockers.maxOf { it.first }
        var pos = startpos
        var facing = 0
        val visited = mutableSetOf<Pair<Point, Int>>()
        while ((pos to facing !in visited) &&
            pos.first >= 0 && pos.second >= 0 && pos.first <= maxX && pos.second <= maxY
        ) {
            visited += pos to facing
            val dir = when (facing % 4) {
                0 -> 0 to -1
                1 -> 1 to 0
                2 -> 0 to 1
                3 -> -1 to 0
                else -> throw IllegalStateException()
            }
            if (pos + dir in blockers) {
                facing = (facing + 1) % 4
                continue
            }
            pos += dir
        }
        return visited.map { it.first }.toSet() to if (pos to facing in visited) Reason.Loop else Reason.Border
    }

    private enum class Reason {
        Border, Loop
    }
}