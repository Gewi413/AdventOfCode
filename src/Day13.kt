object Day13 : Day(13) {
    override fun main() {
        println(input.joinToString("\n").split("\n\n").map { it.split("\n") }.map { map ->
            val r = reflection(map)
            if (r != -1) return@map r * 100

            val transpose = map[0].indices.map { i -> map.map { it[i] }.joinToString("") }

            val r2 = reflection(transpose)
            if (r2 != -1) return@map r2
            throw NotImplementedError()
        }.sum())
    }

    private fun reflection(map: List<String>): Int {
        var s = map
        while (s.isNotEmpty()) {
            if (s == s.reversed() && s.size % 2 == 0) {
                return (map.size - s.size / 2)
            }
            s = s.drop(1)
        }

        s = map.reversed()
        while (s.isNotEmpty()) {
            if (s == s.reversed() && s.size % 2 == 0) {
                return (s.size / 2)
            }
            s = s.drop(1)
        }

        return -1
    }
}