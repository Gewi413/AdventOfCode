object Day02 : Day(2) {
    override fun main() {
        val (a, b) = input.map { line ->
            val (him, me) = line.split(" ").map { "ABCXYZ".indexOf(it) % 3 }
            ((me + 1) + if (me == him) 3 else if (me == him + 1 || (me == 0 && him == 2)) 6 else 0) to
                when (me) {
                    1 -> him + 1 + 3
                    0 -> (him + 2) % 3 + 1
                    else -> 6 + (him + 1) % 3 + 1
                }
        }.fold(0 to 0) { a, b -> a.first + b.first to a.second + b.second }
        println(a)
        println(b)
    }
}