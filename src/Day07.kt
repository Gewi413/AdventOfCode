import java.util.stream.Collectors

object Day07 : Day(7) {
    override fun main() {
        val eqs = input.map {
            val parts = it.split(": ")
            parts[0].toLong() to parts[1].split(" ").map(String::toLong)
        }
        println(eqs.filter { it.first in eval(it.second) }
            .sumOf { it.first })

        println(eqs.parallelStream()
            .filter { it.first in eval(it.second, true) }
            .map { it.first }
            .collect(Collectors.summingLong { it }))

    }

    private fun eval(nums: List<Long>, enableB: Boolean = false): Set<Long> {
        if (nums.size == 1) {
            return nums.toSet()
        }
        val rest = nums.drop(2)
        val resA = mutableListOf(nums[0] + nums[1])
        val resB = mutableListOf(nums[0] * nums[1])
        resA.addAll(rest)
        resB.addAll(rest)
        val res = eval(resA, enableB) + eval(resB, enableB)

        if (!enableB) {
            return res
        }
        val resC = mutableListOf((nums[0].toString() + nums[1].toString()).toLong()) // performance is my passion
        resC.addAll(rest)
        return res + eval(resC, true)
    }
}