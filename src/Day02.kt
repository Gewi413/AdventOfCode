object Day02 : Day(2) {
    override fun main() {
        println(input.fold(0 to 0) { acc, next ->
            val (op, arg) = next.toInstruction()
            val (x, y) = acc
            when (op) {
                "forward" -> x + arg to y
                "down" -> x to y + arg
                "up" -> x to y - arg
                else -> throw Exception("wrong input D:")
            }
        }.let { it.first * it.second })

        println(input.fold(0 to 0 to 0) { acc, next ->
            val (op, arg) = next.toInstruction()
            val (x, y, aim) = acc
            when (op) {
                "forward" -> x + arg to y + aim * arg to aim
                else -> x to y to when (op) {
                    "down" -> aim + arg
                    "up" -> aim - arg
                    else -> throw Exception("wrong input D:")
                }
            }
        }.let { it.first * it.second })
    }
}