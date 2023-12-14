import kotlin.system.measureTimeMillis

object Day12 : Day(12) {
    override fun main() {
        println(input.map { line ->
            val (input, nums) = line.split(" ")
            val rules = nums.split(",").toInt()
            (0 until 5).joinToString("?") { input }.replace("\\.+", ".")
                .trim('.') to ((0 until 5).flatMap { rules })
        }.parallelStream().mapToLong {
            var x: Long
            val t = measureTimeMillis { x = solve(it.first, it.second) }
            println("foobar: ${t}ms $it");
            return@mapToLong x
        }.sum())
    }


    private fun solve(input: String, rules: List<Int>): Long {
        val firstLen = rules.firstOrNull() ?: return if ('#' in input) 0 else 1
        val newRules = rules.drop(1)
        val next = input.windowed(firstLen).withIndex().takeWhile { (i, _) ->
            '#' !in input.substring(0, i)
        }.filter { (i, chars) ->
            chars.all { c -> c != '.' } && input.getOrNull(i + firstLen) != '#'
        }.map { it.index }
        return next.sumOf { solve(input.drop(it + firstLen + 1), newRules) }
    }
}