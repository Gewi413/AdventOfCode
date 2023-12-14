object Day12 : Day(12) {
    override fun main() {
        println(input.sumOf { line ->
            val (input, nums) = line.split(" ")
            val rules = nums.split(",").toInt()
            solve(input, rules)
        })
    }

    private fun solve(input: String, rules: List<Int>): Int {
        return when {
            '?' in input -> solve(input.replaceFirst('?', '.'), rules) + solve(input.replaceFirst('?', '#'), rules)
            valid(input, rules) -> 1
            else -> 0
        }
    }

    private fun valid(line: String, rules: List<Int>): Boolean {
        return line.trim('.').split("\\.+".toRegex()).map { it.length } == rules
    }
}