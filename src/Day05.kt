object Day05 : Day(5) {
    override fun main() {
        val split = input.indexOf("")
        val width = input[split - 1].length / 4
        val startConfig = (0..width).map { x ->
            (split - 2 downTo 0).map { y ->
                input[y][x * 4 + 1]
            }.filter { it != ' ' }
        }
        val instructions = input.drop(split + 1)
        println(run(instructions, startConfig, true))
        println(run(instructions, startConfig))
    }

    private fun run(instructions: List<String>, startConfig: List<List<Char>>, reversed: Boolean = false) =
        instructions.fold(startConfig) { config, line ->
            var (num, from, to) = line.split(" ").mapNotNull { it.toIntOrNull() }
            val mut = config.toMutableList()
            from--
            to--
            mut[to] += config[from].takeLast(num).let { if (reversed) it.reversed() else it }
            mut[from] = config[from].dropLast(num)
            mut
        }.joinToString("") { it.last().toString() }
}