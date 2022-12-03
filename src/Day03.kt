object Day03 : Day(3) {
    override fun main() {
        println('a'.points())
        println(input.sumBy { line ->
            val (a, b) = line
                .chunked(line.length / 2)
                .map { it.toSet() }
            (a intersect b)
                .first()
                .points()
        })
        println(input.chunked(3).sumBy { lines ->
            lines
                .fold(('A'..'z').toSet()) { a, i -> a intersect i.toSet() }
                .first()
                .points()
        })
    }

    private fun Char.points() = 1 + if (isLowerCase()) this - 'a' else this - 'A' + 26
}