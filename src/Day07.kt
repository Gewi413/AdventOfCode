import kotlin.math.abs

object Day07 : Day(7) {
    private val nums = input[0].split(",").toInt()
    private val range = nums.minOrNull()!!..nums.maxOrNull()!!

    override fun main() {
        listOf<(Int, Int) -> Int>(
            { a, b -> abs(a - b) },
            { a, b -> val d = abs(a - b); d * (d + 1) / 2 }
        ).forEach { println(solve(it)) }
    }

    private fun solve(dist: (Int, Int) -> Int): Int = range.minOf { target -> nums.sumBy { dist(target, it) } }
}