object Day07 : Day(7) {
    override fun main() {
        val eqs = input.map {
            val parts = it.split(": ")
            parts[0].toLong() to parts[1].split(" ").map(String::toLong)
        }
        println(eqs.filter { eval(it.second, it.first) }
            .sumOf { it.first })

        println(eqs.filter { eval(it.second, it.first, true) }
            .sumOf { it.first })
    }

    private fun eval(nums: List<Long>, goal: Long, enableConcat: Boolean = false): Boolean {
        if (nums.size == 1) {
            return nums.single() == goal
        }
        if (nums[0] > goal) {
            return false
        }
        val rest = nums.drop(2)
        val resA = mutableListOf(nums[0] + nums[1])
        val resB = mutableListOf(nums[0] * nums[1])
        resA.addAll(rest)
        resB.addAll(rest)
        val res = eval(resA, goal, enableConcat) || eval(resB, goal, enableConcat)

        if (!enableConcat) {
            return res
        }

        val resC = mutableListOf((nums[0].toString() + nums[1].toString()).toLong()) // performance is my passion
        resC.addAll(rest)
        return res || eval(resC, goal, true)
    }
}