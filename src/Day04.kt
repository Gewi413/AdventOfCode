object Day04 : Day(4) {
    override fun main() {
        val nums = input[0].split(",").toInt()
        val boards = input.drop(1).chunked(6).map { Bingo(it.drop(1)) }.toMutableSet()
        Bingo.drawn -= Bingo.drawn
        var i = 0
        var won = false
        while (true) {
            Bingo.drawn += nums[i]
            val winners = boards.filter { it.won }.toSet()
            boards -= winners
            if ((winners.isNotEmpty() && !won) || boards.isEmpty()) {
                println(winners.first().score * nums[i])
                if (won) return
                won = true
            }
            i++
        }
    }

    class Bingo(text: List<String>) {
        private val nums = text.map { it.trim().split(Regex(" +")).toInt() }
        val won: Boolean
            get() = nums.any { row -> row.all { it in drawn } } ||
                    nums[0].indices.any { column -> nums.all { it[column] in drawn } }

        val score: Int get() = nums.flatten().filter { it !in drawn }.fold(0, Int::plus)

        companion object {
            val drawn = mutableSetOf<Int>()
        }
    }
}

