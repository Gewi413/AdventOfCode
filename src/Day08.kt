object Day08 : Day(8) {
    override fun main() {
        val nums = listOf(1, 4, 7, 8).map { it.toString()[0] }
        println(input.map { val (hints, num) = it.split(" | "); solve(hints, num) }
            .also { println(it.sumBy { s -> s.count { c -> c in nums } }) }
            .toInt().sum())
    }

    private fun solve(hints: String, num: String): String {
        val counts = hints.split(" ").groupBy { it.length }
        val solved = Array(10) { setOf<Char>() }
        solved[1] = counts[2]!![0].toSet()
        solved[4] = counts[4]!![0].toSet()
        solved[7] = counts[3]!![0].toSet()
        solved[8] = counts[7]!![0].toSet()
        // 3 is when both left sides are lit aka when 1 is lit
        solved[3] = counts[5]!!.first { solved[1].all { c -> c in it } }.toSet()
        // 5 is when both top sides of 4 are used
        solved[5] = counts[5]!!.first { (solved[4] - solved[1]).all { c -> c in it } }.toSet()
        // remaining must be 2
        solved[2] = counts[5]!!.first { it.toSet() !in solved }.toSet()
        // 0 is when top and bottom each lit
        solved[0] = counts[6]!!.first { (solved[3] - solved[1]).count { c -> c in it } == 2 }.toSet()
        // 9 is subset of 1
        solved[9] = counts[6]!!.first { (solved[1] + solved[3]).all { c -> c in it } }.toSet()
        // remaining must be 6
        solved[6] = counts[6]!!.first { it.toSet() !in solved }.toSet()
        return num.split(" ").map { solved.indexOf(it.toSet()) }.joinToString("")
    }
}