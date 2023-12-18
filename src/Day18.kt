import kotlin.math.abs

object Day18 : Day(18) {
    override fun main() {
        val (movesA, movesB) = input.map { line ->
            val (dir, count, col) = line.split(" ")
            val foo = col.drop(2).dropLast(1)
            (dir.first() to count.toInt()) to (foo.last() to foo.take(5).toInt(16))
        }.unzip()
        println(solve(movesA))
        println(solve(movesB))
    }

    private fun solve(moves: List<Pair<Char, Int>>): Long {
        var pos = 0 to 0
        val dug = mutableListOf(pos)
        var sum = 1L //yes (I think starting pos issue)
        for ((dir, count) in moves) {
            var outsideCorner = false
            val direction = when (dir) {
                in "0R" -> {
                    outsideCorner = true
                    1 to 0
                }
                in "1D" -> {
                    outsideCorner = true
                    0 to 1
                }
                in "2L" -> -1 to 0
                in "3U" -> 0 to -1
                else -> throw NotImplementedError()
            }
            pos += direction * count
            dug += pos
            if(outsideCorner) sum += count
        }
        var shoelace = 0L
        for (i in 0 until dug.lastIndex) {
            shoelace += dug[i].first.toLong() * dug[i + 1].second
            shoelace -= dug[i].second.toLong() * dug[i + 1].first
        }
        return abs(shoelace / 2) + sum
    }
}