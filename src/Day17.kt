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

        val cycle = pieces.size * wind.length

        val length = 1000000000000
        val once = run(pieces, cycle, wind)
        val twice = run(pieces, 2 * cycle, wind)
        val offset = length % cycle
        val final = run(pieces, (cycle + offset).toInt(), wind)
        val each = twice - once
        val times = length / cycle - 1
        println(times * each + once + final) // 1565434624629 too low

        // cycle: 10091 (len of my input) * 5 (num pieces)
        // 78982 at cycle: offset
        // 157966 at 2*cycle
        // each cycle: 157966
        // num cycles: 99098206
        // 3254 off: 84071 -> 5089
        // 99098205 * 157966 + 78982 + 5089 = 15654147135101: too high
    }

    private fun run(pieces: List<List<Point>>, amount: Int, wind: String): Long {
        var currWind = 0
        var height = 0L
        val board =
            ((0..6).map { it to 0L } +
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
