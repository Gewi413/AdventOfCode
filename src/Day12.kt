object Day12 : Day(12) {
    override fun main() {
        println(solve(1))
        println(solve(5))
    }

    private fun solve(repeats: Int): Long {
        return input.map { line ->
            val (input, nums) = line.split(" ")
            val rules = nums.split(",").toInt()
            (0 until repeats).joinToString("?") { input }.replace("\\.+".toRegex(), ".")
                .trim('.') to ((0 until repeats).flatMap { rules })
        }.sumOf { solve(it.first.toList(), it.second) }
    }

    private val cache = HashMap<Pair<List<Char>, List<Int>>, Long>()

    private fun solve(input: List<Char>, rules: List<Int>): Long {
        val state = input to rules
        if (cache.contains(state)) return cache.getOrDefault(state, 0)
        val firstLen = rules.firstOrNull() ?: return if ('#' in input) 0 else 1
        val x = when (input.firstOrNull()) {
            null -> 0
            '.' -> solve(input.drop(1), rules)
            '#' -> {
                if (input.size >= firstLen && input.take(firstLen)
                        .all { it != '.' } && input.getOrNull(firstLen) != '#'
                )
                    solve(input.drop(firstLen + 1), rules.drop(1))
                else 0
            }

            '?' -> {
                solve(input.drop(1), rules) + solve(listOf('#') + input.drop(1), rules)
            }

            else -> throw NotImplementedError(input.joinToString())
        }
        cache[state] = x
        return x
    }
}