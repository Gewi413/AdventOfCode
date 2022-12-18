import kotlin.math.max

object Day17 : Day(17) {
    override fun main() {
        val wind = input[0]
        val pieces = listOf(
            (2..5).map { it to 0 }, // -
            listOf(3 to 0, 3 to 2) + (2..4).map { it to 1 }, // +
            (2..4).map { it to 0 } + listOf(4 to 1, 4 to 2), //J
            (0..3).map { 2 to it }, // |
            listOf(2 to 0, 2 to 1, 3 to 0, 3 to 1) // box
        )
        run(pieces, wind)
        println(history[2022])

        val differences = history.zipWithNext { a, b -> b - a }.windowed(5000)

        // performance go brr
        val firstRep = differences.withIndex().first { (i, d) -> differences.lastIndexOf(d) != i }.index
        val secondRep = differences.withIndex().first { (i, d) -> d == differences[firstRep] && i != firstRep }.index

        val cycle = secondRep - firstRep
        val length = 1000000000000
        val cyclesNeeded = (length - firstRep) / cycle
        val remaining = (length - firstRep) % cycle
        val diffPerCycle = history[secondRep] - history[firstRep]
        println(diffPerCycle * cyclesNeeded + history[(firstRep + remaining).toInt()])
    }

    private val history = mutableListOf<Long>() // wind*5+piece -> pieceCount, height
    private fun run(pieces: List<List<Point>>, wind: String): Long {
        history.clear()
        history += 0
        var currWind = 0
        var height = 0L
        val board = ((0..6).map { it to 0L } +
                (0L..(10000 * 2.5 + 10).toLong()).flatMap { listOf(-1 to it, 7 to it) }).toMutableSet()

        for (i in 0 until 10000) {
            var current = pieces[i % pieces.size].map { (x, y) -> x to y + height + 4L }
            //            board.associate { (x, y) -> x to -(if (y <= height + 8) y else height + 8) to 8 }.print(current.map { (x, y) -> x to -y }.toSet())
            while (true) {
                when (wind[currWind]) {
                    '<' -> if (current.all { (x, y) -> x - 1 to y !in board })
                        current = current.map { (x, y) -> x - 1 to y }

                    '>' -> if (current.all { (x, y) -> x + 1 to y !in board })
                        current = current.map { (x, y) -> x + 1 to y }
                }
                currWind = (currWind + 1) % wind.length
                val test = current.map { (x, y) -> x to y - 1 }
                if ((test intersect board).isNotEmpty()) {
                    height = max(current.maxOfOrNull { (_, y) -> y }!!, height)
                    board += current
                    history += height
                    break
                }
                current = test
            }
        }
        return height
    }

    private operator fun <E> List<E>.times(i: Int): List<E> {
        val copy = mutableListOf<E>()
        repeat(i) {
            copy += this
        }
        return copy
    }
}