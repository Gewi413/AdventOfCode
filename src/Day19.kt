object Day19 : Day(19) {
    override fun main() {
        val towels = input.first().split(", ")

        val res = input.drop(2).map { solve(it, towels) }
        println(res.count { it > 0 })
        println(res.sumOf { it })
    }

    private val cache: MutableMap<String, Long> = mutableMapOf()

    private fun solve(pattern: String, towels: List<String>): Long {
        if(pattern.isEmpty()) {
            return 1
        }
        if(pattern in cache) {
            return cache[pattern]!!
        }
        val res = towels
            .filter { pattern.startsWith(it) }
            .sumOf { solve(pattern.removePrefix(it), towels) }
        cache[pattern] = res
        return res
    }
}