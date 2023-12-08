object Day08 : Day(8) {
    override fun main() {
        val moves = input[0]
        val transitions = input.drop(2).associate {
            val (from, l, r, _) = it.split(" = (", ", ", ")", ")")
            from to (l to r)
        }
        var state1 = "AAA"
        var i = 0
        while (state1 != "ZZZ") {
            val dir = moves[(i++ % moves.length)]
            val next = transitions[state1]
            state1 = if (dir == 'L') next!!.first else next!!.second
        }
        println(i)

        val goals = mutableMapOf<Triple<Long, String, String>, Long>()
        val cycles = mutableMapOf<String, Long>()
        transitions.keys.filter { it.endsWith("A") }.stream().forEach { start -> // dont parallelize
            var stat = start
            var step = 0L
            while (true) {
                val cycle = step % moves.length
                val thing = Triple(cycle, start, stat)
                if (thing in goals) {
                    cycles[start] = step - goals[thing]!!
                    break
                }
                goals[thing] = step
                val dir = moves[cycle.toInt()]
                val next = transitions[stat]
                stat = if (dir == 'L') next!!.first else next!!.second
                step++
            }
        }
        println(cycles.values.fold(1L) { a, b -> gcd(a, b) })
    }

    private fun gcd(a: Long, b: Long) = a * b / lcm(a, b)

    private tailrec fun lcm(a: Long, b: Long): Long = when {
        a == 0L -> b
        a > b -> lcm(b, a)
        else -> lcm(a, b % a)
    }
}
