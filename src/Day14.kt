object Day14 : Day(14) {
    override fun main() {
        val data = input.map { it.replace('.', '0').replace('#', '1').replace('O', '2') }
            .toIntMap()
        val rocks = data.filter { it.value == 1 }.keys +
                input[0].indices.flatMap { listOf(it to -1, it to input.size) } +
                input.indices.flatMap { listOf(-1 to it, input[0].length to it) }
        var rollable = data.filter { it.value == 2 }.keys.toList()
        println(roll(rollable, rocks).sumBy { input.size - it.second })

        val cache = mutableMapOf<List<Point>, Int>()
        var i = 0
        while (true) {
            if (rollable in cache) break
            cache[rollable] = i

            val dirs = listOf(0 to -1, -1 to 0, 0 to 1, 1 to 0)
            for (dir in dirs) {
                rollable = roll(rollable, rocks, dir)
            }
            i++
        }
        val cycleStart = cache[rollable]!!
        val offset = (1000000000 - cycleStart) % (i - cycleStart)
        println(cache.entries.single { it.value == cycleStart + offset }.key.sumBy { input.size - it.second })
    }

    private fun rollNotWorking(rollable: List<Point>, rocks: Set<Point>, dir: Point = (0 to -1)): List<Point> {
        return doUntilSettled(rollable) { state ->
            state.map {
                val next = it + dir
                if (next in rocks || next in state) {
                    it
                } else next
            }
        }
    }

    private fun roll(rollable: List<Point>, rocks: Set<Point>, dir: Point = (0 to -1)): List<Point> {
        val (minX, maxX) = rollable.map { it.first }.minMax()
        val (minY, maxY) = rollable.map { it.second }.minMax()
        val state = mutableSetOf<Point>()
        when (dir) {
            0 to -1 -> for (x in (minX..maxX)) {
                var rock = -1
                var r = 0
                for (y in (-10..110)) {
                    val pos = x to y
                    if (pos in rocks) {
                        state.addAll((rock + 1..rock + r).map { x to it })
                        r = 0
                        rock = pos.second
                    }
                    if (pos in rollable) r++
                }
            }

            0 to 1 -> for (x in (minX..maxX)) {
                var rock = 101
                var r = 0
                for (y in (110 downTo -10)) {
                    val pos = x to y
                    if (pos in rocks) {
                        state.addAll((rock - 1 downTo rock - r).map { x to it })
                        r = 0
                        rock = pos.second
                    }
                    if (pos in rollable) r++
                }
            }

            -1 to 0 -> for (y in (minY..maxY)) {
                var rock = -1
                var r = 0
                for (x in (-10..110)) {
                    val pos = x to y
                    if (pos in rocks) {
                        state.addAll((rock + 1..rock + r).map { it to y })
                        r = 0
                        rock = pos.first
                    }
                    if (pos in rollable) r++
                }
            }

            1 to 0 -> for (y in (minY..maxY)) {
                var rock = 101
                var r = 0
                for (x in (110 downTo -10)) {
                    val pos = x to y
                    if (pos in rocks) {
                        state.addAll((rock - 1 downTo rock - r).map { it to y })
                        r = 0
                        rock = pos.first
                    }
                    if (pos in rollable) r++
                }
            }

            else -> throw NotImplementedError()
        }
        return state.toList()
    }
}
