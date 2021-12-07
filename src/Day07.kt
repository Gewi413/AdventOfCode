import kotlin.math.abs

object Day07 : Day(7) {
    override fun main() {
        val nums = input[0].split(",").toInt()
        listOf<(Int, Int) -> Int>(
            { a, b -> abs(a - b) },
            { a, b -> val d = abs(a - b); d * (d + 1) / 2 }
        ).map { solve(nums, it) }.forEach { println(it) }
    }

    private fun solve(nums: List<Int>, dist: (Int, Int) -> Int) = (nums.minOrNull()!!..nums.maxOrNull()!!)
        .map { target -> nums.sumBy { dist(target, it) } }.minOrNull()
}