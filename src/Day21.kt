object Day21 : Day(21) {
    override fun main() {
        val todo = input.toMutableList()
        val values = doUntilSettled(mapOf<String, Long>()) { initial ->
            val copy = initial.toMutableMap()
            todo.removeIf { line ->
                val parts = line.split(": ", " ")
                if (parts.size == 2) {
                    val (key, value) = parts
                    copy[key] = value.toLong()
                    true
                } else {
                    val (key, first, op, second) = parts
                    copy[key] = when(op) {
                        "+" -> (initial[first] ?: return@removeIf false) + (initial[second] ?: return@removeIf false)
                        "-" -> (initial[first] ?: return@removeIf false) - (initial[second] ?: return@removeIf false)
                        "*" -> (initial[first] ?: return@removeIf false) * (initial[second] ?: return@removeIf false)
                        "/" -> (initial[first] ?: return@removeIf false) / (initial[second] ?: return@removeIf false)
                        else -> return@removeIf false
                    }
                    true
                }
            }
            copy
        }
        println(values["root"])
        val proc = ProcessBuilder("python", "src/Day21.py")
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()
        val (x) = proc.inputStream.bufferedReader().readText().split(" = ", ", ", "]").mapNotNull { it.toLongOrNull() }
        println(x)
    }
}