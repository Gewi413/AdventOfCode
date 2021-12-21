object Day21 : Day(21) {
    override fun main() {
        val (start1, start2) = input.map { it.split(" ").last().toInt() }
        var state = State(start1, start2)
        while (true) {
            state = advance(state)
            if (state.score1 >= 1000) {
                println(state.turn * state.score2)
                break
            }
            if (state.score2 >= 1000) {
                println(state.turn * state.score1)
                break
            }
        }

        val states = mutableMapOf(State(start1, start2) to 1L)
        var winners = 0L to 0L
        while (states.isNotEmpty()) {
            states.entries.removeIf { (k, v) ->
                when {
                    k.score1 >= 21 -> {
                        winners = winners.first + v to winners.second
                        true
                    }
                    k.score2 >= 21 -> {
                        winners = winners.first to winners.second + v
                        true
                    }
                    else -> false
                }
            }
            val copy = states.toMutableMap()
            states.entries.removeIf { true }
            for ((k, v) in copy) {
                for (i in 1..3) {
                    if (!(k.turn == 3 || k.turn == 7) || i == 1) {
                        val out = State(
                            if (k.turn < 3) (k.p1 + i - 1) % 10 + 1 else k.p1,
                            if (k.turn in 4..6) (k.p2 + i - 1) % 10 + 1 else k.p2,
                            k.score1 + if (k.turn == 3) k.p1 else 0,
                            k.score2 + if (k.turn == 7) k.p2 else 0,
                            (k.turn + 1) % 8
                        )
                        states.compute(out) { _, value -> (value ?: 0L) + v }
                    }
                }
            }
        }
        println(if (winners.first > winners.second) winners.first else winners.second)
    }

    private fun advance(state: State): State {
        var (p1, p2, score1, score2, turn) = state
        if (turn % 6 < 3) {
            for (i in 1..3) {
                p1 = (p1 + turn % 100) % 10 + 1
                turn++
            }
            score1 += p1
        } else {
            for (i in 1..3) {
                p2 = (p2 + turn % 100) % 10 + 1
                turn++
            }
            score2 += p2
        }
        return State(p1, p2, score1, score2, turn)
    }

    data class State(val p1: Int, val p2: Int, val score1: Int = 0, val score2: Int = 0, val turn: Int = 0)
}