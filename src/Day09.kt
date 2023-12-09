object Day09 : Day(9) {
    override fun main() {
        val (a, b) = input.map { line ->
            val nums = line.split(" ").toInt()
            extrapolate(nums) to extrapolate(nums.reversed())
        }.fold(0 to 0) { acc, i -> acc.first + i.first to acc.second + i.second }
        println("$a\n$b")
    }

    private fun extrapolate(nums: List<Int>): Int {
        if (nums.all { it == 0 }) return 0
        val next = extrapolate(nums.zipWithNext { a, b -> b - a })
        return nums.last() + next
    }
}