object Day13 : Day(13) {
    override fun main() {
        val (points, folds) = input.joinToString("\n").split("\n\n").map { it.split("\n") }
        var print = false
        val map = folds.fold(points.toPoint().toSet()) { acc, fold ->
            val (ins, pos) = fold.split("=")
            val line = pos.toInt()
            acc.map {
                val (x, y) = it
                when (ins.last()) {
                    'x' -> (if (x > line) line * 2 - x else x) to y
                    'y' -> x to (if (y > line) line * 2 - y else y)
                    else -> TODO()
                }
            }.toSet().also { if (!print) println(it.size); print = true }
        }

        for (y in 0..map.maxOf { it.second }) {
            for (x in 0..map.maxOf { it.first }) {
                print(if (x to y in map) "#" else ".")
                if (x % 5 == 4) print(" ")
            }
            println()
        }
        // sadly I am a robot (or lazy) and can't parse this text
    }
}