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
        println(run(pieces, 2022, wind))

        val repetition = run(pieces, 10000, wind, true).toInt()

        val length = 1000000000000
        val (firstPiece, firstHeight) = history[repetition]!!
        val (secondPiece, secondHeight) = history[-1]!!
        val deltaPiece = secondPiece - firstPiece
        val deltaHeight = secondHeight - firstHeight
        val cyclesNeeded = length / deltaPiece - 1
        val baseHeight = firstHeight + cyclesNeeded * deltaHeight
        val stillNeeded = length - deltaPiece * cyclesNeeded - firstPiece
        val stillHeight = run(pieces, stillNeeded.toInt() + firstPiece, wind) - firstHeight
        println(baseHeight + stillHeight)

        // 1564265129668
    }

    private val history = mutableMapOf<Int, Pair<Int, Long>>() // wind*5+piece -> pieceCount, height
    private fun run(pieces: List<List<Point>>, amount: Int, wind: String, cycles: Boolean = false): Long {
        history.clear()
        var currWind = 0
        var height = 0L
        val board = ((0..6).map { it to 0L } +
                (0L..(amount * 2.5 + 10).toLong()).flatMap { listOf(-1 to it, 7 to it) }).toMutableSet()

        for (i in 0 until amount) {
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
                    height = max(current.maxOfOrNull { (x, y) -> y }!!, height)
                    board += current
                    if (cycles && 5 * currWind + i % pieces.size in history) {
                        history[-1] = i to height
                        return (5 * currWind + i % pieces.size).toLong()
                    }
                    history[5 * currWind + i % pieces.size] = i to height
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