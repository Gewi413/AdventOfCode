object Day18 : Day(18) {
    override fun main() {
        val moves = input.map { line ->
            val (dir, count, col) = line.split(" ")
            dir to (count.toInt() to col.drop(2).dropLast(1).toInt(16))
        }
        println(solve(moves.map { it.first to it.second.first}))
        println(solve(moves.map { it.first to it.second.second}))
    }

    private fun solve(moves: List<Pair<String, Int>>): Int {
        var pos = 0 to 0
        val dug = mutableSetOf<Point>()
        for ((dir, count) in moves) {
            val direction = when (dir) {
                "L" -> -1 to 0
                "R" -> 1 to 0
                "U" -> 0 to -1
                "D" -> 0 to 1
                else -> throw NotImplementedError()
            }
            repeat(count) {
                pos += direction
                dug += pos
            }
        }

        val done = mutableSetOf<Point>()
        val todo = mutableSetOf(1 to 1) //if(!halts) use another point
        while (todo.isNotEmpty()) {
            val next = todo.first()
            todo.remove(next)
            done += next
            todo += next.neighbors().filter { it !in done && it !in dug }
        }
        val hole = (done + dug)
        return (hole.size)
    }
}