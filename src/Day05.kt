import kotlin.math.abs

object Day05 : Day(5) {
    override fun main() {
        println(parse())
        println(parse(true))
    }

    private fun parse(diags: Boolean = false): Int {
        val pipes = mutableMapOf<Pair<Int, Int>, Int>()
        input.forEach { line ->
            val (x1, y1, x2, y2) = line.split(" -> ", ",").toInt()
            when {
                x1 == x2 -> (if (y1 < y2) y1..y2 else y2..y1).forEach { pipes[x1 to it] = (pipes[x1 to it] ?: 0) + 1 }
                y1 == y2 -> (if (x1 < x2) x1..x2 else x2..x1).forEach { pipes[it to y1] = (pipes[it to y1] ?: 0) + 1 }
                diags && abs(x1 - x2) == abs(y1 - y2) -> {
                    val side = (x1 < x2 && y1 < y2) || (x1 > x2 && y1 > y2)
                    val x = if ((x1 > x2) xor side) x1 else x2
                    val y = if (y1 < y2) y1 else y2
                    (0..abs(x1 - x2)).forEach {
                        val pos = x + (if (side) it else -it) to y + it
                        pipes[pos] = (pipes[pos] ?: 0) + 1
                    }
                }
            }
        }
        return pipes.values.count { it >= 2 }
    }
}