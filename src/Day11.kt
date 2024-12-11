object Day11 : Day(11) {
    override fun main() {
        val nums = input.single().split(" ").toLong()
        println(calculate(nums, 25))
        println(calculate(nums, 75))
    }

    private fun calculate(nums: List<Long>, time: Int): Long {
        return nums.sumOf { calculate(it, time) }
    }

    private val cache = mutableMapOf<Pair<Long, Int>, Long>()

    private fun calculate(num: Long, time: Int): Long {
        if (time == 0) {
            return 1
        }
        val lookup = num to time
        if (lookup in cache) {
            return cache[lookup]!!
        }
        val res =
            if (num == 0L) {
                return calculate(1, time - 1)
            } else {
                val str = num.toString()
                if (str.length % 2 == 0) {
                    str.chunked(str.length / 2).sumOf { calculate(it.toLong(), time - 1) }
                } else {
                calculate(num * 2024, time - 1)
                }
            }
        cache[lookup] = res
        return res
    }
}