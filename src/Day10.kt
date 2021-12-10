object Day10 : Day(10) {
    override fun main() {
        val scoreA = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
        val scoreB = "([{<".mapIndexed { i, c -> c to (i + 1).toLong() }.toMap() // yes, hardcode would be easier
        val matching = "()[]{}<>".chunked(2).associate { it[0] to it[1] }
        val parsed = input.map { line ->
            val tokens = mutableListOf<Char>()
            for (c in line) when (c) {
                '(', '[', '{', '<' -> tokens += c
                else -> if (matching[tokens.last()] == c) tokens.removeLast() else return@map scoreA[c]!!
            }
            tokens.foldRight(0L) { c, a -> a * 5L + scoreB[c]!! }
        }
        println(parsed.sumBy { if (it is Int) it else 0 })
        val completions = parsed.filterIsInstance<Long>().sorted()
        println(completions[completions.size / 2])
    }
}