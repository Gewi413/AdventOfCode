object Day16 : Day(16) {
    override fun main() {
        val map = input.map { it.replaceAll(".0", "/1", "\\2", "-3", "|4") }.toIntMap().filter { it.value != 0 }
        val dist = (input[0].indices.flatMap { listOf((it to 0) to 2, (it to input.size - 1) to 0) } +
                input.indices.flatMap { listOf((0 to it) to 3, (input[0].length - 1 to it) to 1) }).parallelStream().mapToInt { path(map, it) }.toArray()
        println(dist.first()) // has to be down because first input is a slash
        println(dist.maxOrNull())
    }

    private fun path(map: Map<Point, Int>, start: Pair<Point, Int>): Int {
        val dirs = mutableMapOf(0 to (0 to -1), 2 to (0 to 1), 1 to (-1 to 0), 3 to (1 to 0))
        val done = mutableSetOf<Pair<Point, Int>>()
        val todo = mutableSetOf(start) //up 0, down 2, left 1, right 3
        while (todo.isNotEmpty()) {
            val curr = todo.first()
            val (pos, dir) = curr
            todo.remove(curr)
            if (pos.first !in input[0].indices || pos.second !in input.indices) continue
            if (curr in done) continue
            done += curr
            val next = pos + dirs[dir]!!
            when {
                map[next] == 1 -> todo += when (dir) {
                    0 -> next to 3
                    1 -> next to 2
                    2 -> next to 1
                    3 -> next to 0
                    else -> throw NotImplementedError()
                }

                map[next] == 2 -> todo += when (dir) {
                    0 -> next to 1
                    1 -> next to 0
                    2 -> next to 3
                    3 -> next to 2
                    else -> throw NotImplementedError()
                }

                map[next] == 3 && (dir == 0 || dir == 2) -> {
                    todo += next to 1
                    todo += next to 3
                }

                map[next] == 4 && (dir == 1 || dir == 3) -> {
                    todo += next to 0
                    todo += next to 2
                }

                else -> todo += next to dir
            }
        }
        return done.map { it.first }.toSet().size
    }
}