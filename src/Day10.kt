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
        println(crt.dropLast(1).chunked(40).joinToString("\n") { it.joinToString("") })
    }
}