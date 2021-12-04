object Day04 : Day(4) {
    override fun main() {
        val nums = input[0].split(",").toInt()
        val boards = input
            .joinToString("\n")
            .split("\n\n")
            .drop(1)
            .map { Bingo(it) }
        var i = 0
        while (true) {
            Bingo.drawn += nums[i++]
            if (boards.count { it.won } == 1) println(boards.first { it.won }.score * nums[i - 1])
            if (boards.all { it.won }) break
        }
        Bingo.drawn -= nums[--i]
        println((boards.first { !it.won }.score - nums[i]) * nums[i])
    }

    data class Bingo(val text: String) {
        private val nums = text.split("\n").map { it.trim().split(Regex(" +")).toInt() }
        val won: Boolean
            get() = nums.any { row -> row.all { it in drawn } } ||
                    nums[0].indices.any { column -> nums.all { it[column] in drawn } }

        val score: Int get() = nums.flatten().filter { it !in drawn }.fold(0, Int::plus)

        companion object {
            val drawn = mutableListOf<Int>()
        }
    }
}

