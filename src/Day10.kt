object Day10 : Day(10) {
    override fun main() {
        val instructions = input.flatMap { line ->
            when (line) {
                "noop" -> listOf("noop")
                else -> listOf("noop", line)
            }
        }
        var x = 1
        var cycle = 0
        var strength = 0
        val crt = mutableListOf('#') //idk why i needed that forced # and deleting last char, but it works like that
        instructions.forEach { ins ->
            cycle++
            if (cycle % 40 == 20) strength += cycle * x

            if (ins.startsWith("addx"))
                x += ins.split(" ")[1].toInt()

            val pix = cycle % 40
            crt += if (x in pix - 1..pix + 1) '#' else ' '
        }
        println(strength)
        crt.removeLast()

        // stolen from 2021/13
        val chars = listOf(
            // telephone keypad grouping ABC, DEF, GHI, JKL, MNO, PQRS, TUV, WXYZ
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

        val letters = crt.chunked(40).map { it.chunked(5) }
        val message = (0..7).map { char ->
            (0..5).flatMap { line -> letters[line][char] }.reversed()
                .mapIndexed { i, c -> if (c == '#') 1 shl i else 0 }.sum()
        }

        println(message.map { chars[it] ?: "_" }.joinToString(""))
    }
}