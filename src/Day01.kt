object Day01 : Day(1) {
    override fun main() {
        println(solve())
        println(solve(3))

    }

    private val nums = input.toInt()
    private fun solve(averaging: Int = 1) = nums
        .windowed(averaging)
        .zipWithNext()
        .count { (a, b) -> a.sum() < b.sum() }
}