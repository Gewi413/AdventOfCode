object Day07 : Day(7) {
    private const val values = "AKQJT98765432"
    private val values2 = values.replace("J", "") + "J"
    override fun main() {
        println(solve(values, ::handType))
        println(solve(values2, ::handType2))
    }

    private fun solve(values: String, mapper: (String) -> Int) =
        input.toInstruction().sortedWith { (hand1, _), (hand2, _) ->
            val hands = listOf(hand1, hand2)
            val (t1, t2) = hands.map(mapper)
            if (t1 != t2) return@sortedWith t1 - t2
            val (v1, v2) = hands.map {
                it.toCharArray().map { c -> values.indexOf(c).toChar() }.joinToString()
            }
            -v1.compareTo(v2)
        }.mapIndexed { i, v -> (i.toLong() + 1) * v.second }.sum()

    private fun handType(hand: String): Int {
        val x = hand.toCharArray().asList().eachCount()
        if (x.values.any { it > 4 }) return 6
        if (x.values.any { it > 3 }) return 5
        if (x.values.any { it == 3 }) {
            if (x.values.any { it == 2 }) return 4
            return 3
        }
        return x.values.count { it == 2 }
    }

    private fun handType2(hand: String): Int {
        return values2.map { hand.replace('J', it) }.maxOf { handType(it) }
    }
}