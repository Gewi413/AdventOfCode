object Day13 : Day(13) {
    override fun main() {
        val (points, folds) = input.joinToString("\n").split("\n\n").map { it.split("\n") }
        var print = true
        val map = folds.fold(points.toPoint().toSet()) { acc, curr ->
            val (axis, line) = curr.drop(11).toInstruction('=')
            acc.map {
                val (x, y) = it
                when (axis.last()) {
                    'x' -> (if (x > line) line * 2 - x else x) to y
                    'y' -> x to (if (y > line) line * 2 - y else y)
                    else -> TODO()
                }
            }.toSet().also { if (print) println(it.size); print = false }
        }

        val chars = listOf( // telephone keypad grouping ABC, DEF, GHI, JKL, MNO, PQRS, TUV, WXYZ
            // missing: DIMNOQSTVWXY - if you have those, tell me
            422148690, 959335004, 422068812,
            -1, 1024344606, 1024344592,
            422074958, 623856210, -1,
            203491916, 625758866, 554189342,
            -1, -1, -1,
            959017488, -1, 959017618, -1,
            -1, 623462988, -1,
            -1, -1, -1, 1008869918,
        ).mapIndexed { i, v -> v to 'A' + i }.toMap()

        val message = (0..7).map { char ->
            (0..5).map { y -> (0..4).map { x -> char * 5 + x to y in map } }.flatten().reversed()
                .mapIndexed { i, c -> if (c) 1 shl i else 0 }.sum()
        }
        println(message.map { chars[it] ?: TODO("this char doesnt have a mapping") }.joinToString(""))
    }
}