object Day10 : Day(10) {
    override fun main() {
        val map = mutableMapOf<Point, Char>()
        var start: Point? = null
        for ((y, line) in input.withIndex())
            for ((x, char) in line.withIndex()) {
                if (char == 'S') start = x to y
                map[x to y] = char
            }
        var pos = start!!
        var dir = 3 //up 0, down 2, left 1, right 3 // TODO calculate

        val transitions = mapOf(
            'F' to (0 to 1),
            'J' to (2 to 3),
            '7' to (0 to 3),
            'L' to (2 to 1),
        )
        val markings = mutableSetOf<Point>()
        var i = 0
        do {
            i++
            markings += pos

            if (map[pos]!! in transitions) {
                val next = transitions[map[pos]]!!
                dir = when (dir) {
                    next.first -> (next.second + 2) % 4
                    next.second -> (next.first + 2) % 4
                    else -> throw NotImplementedError("${map[pos]}, $dir, $next, $pos")
                }
            }
            when (dir) {
                0 -> pos += 0 to -1
                1 -> pos += -1 to 0
                2 -> pos += 0 to 1
                3 -> pos += 1 to 0
            }
        } while (pos != start)
        println(i / 2)


        val (minX, maxX) = map.keys.map { it.first }.minMax()
        val rangeX = (minX.toDouble() - 1)..(maxX.toDouble() + 1)
        val (minY, maxY) = map.keys.map { it.second }.minMax()
        val rangeY = (minY.toDouble() - 1)..(maxY.toDouble() + 1)

        val blockers = (markings.toList() + markings.first())
            .zipWithNext()
            .flatMap { (a, b) ->
                listOf(
                    a.first.toDouble() to a.second.toDouble(),
                    b.first.toDouble() to b.second.toDouble(),
                    (a.first + b.first) / 2.0 to (a.second + b.second) / 2.0
                )
            }

        val done = mutableSetOf<PointD>()
        val todo = mutableSetOf(0.0 to 0.0)

        while (todo.isNotEmpty()) {
            val next = todo.first()
            todo.remove(next)
            done += next
            if (next.first !in rangeX) continue
            if (next.second !in rangeY) continue
            if (next in blockers) continue
            val neighbors = listOf(
                next.first + .5 to next.second,
                next.first - .5 to next.second,
                next.first to next.second + .5,
                next.first to next.second - .5,
            )
            todo += neighbors.filter { it !in done }
        }

        val outside = done
            //.filter { it.first % 1 == 0.0 || it.second % 1 == 0.0 }
            .map { it.first.toInt() to it.second.toInt() }
            .toSet()
        println((map.keys - outside - markings).size)
    }
}

typealias PointD = Pair<Double, Double>