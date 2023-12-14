object Day13 : Day(13) {
    override fun main() {
        val (a, b) = input.joinToString("\n").split("\n\n").map { it.split("\n") }.map { map ->
            val horizontalReflection = reflection(map).singleOrNull()
            val transpose = map[0].indices.map { i -> map.map { it[i] }.joinToString("") }
            val verticalReflection = reflection(transpose).singleOrNull()
            val reflection = (horizontalReflection?.times(100) ?: verticalReflection)!!
            for (c in changes(map)) {
                val rr = reflection(c)
                for (r in rr) if (r != horizontalReflection) return@map reflection to r * 100
            }

            for (c in changes(transpose)) {
                val rr = reflection(c)
                for (r in rr) if (r != verticalReflection) return@map reflection to r
            }
            throw NotImplementedError()
        }.fold(0 to 0) { acc, i -> acc.first + i.first to acc.second + i.second }
        println(a)
        println(b)
    }

    private fun changes(map: List<String>): List<List<String>> {
        val out = mutableListOf<List<String>>()
        for (y in map.indices) for (x in map[0].indices) {
            val mutMap = map.map { it.toMutableList() }
            mutMap[y][x] = if (mutMap[y][x] == '#') '.' else '#'
            out.add(mutMap.map { it.joinToString("") })
        }
        return out
    }

    private fun reflection(map: List<String>): List<Int> {
        val lines = mutableListOf<Int>()
        var s = map
        while (s.isNotEmpty()) {
            if (s == s.reversed() && s.size % 2 == 0) {
                lines.add(map.size - s.size / 2)
            }
            s = s.drop(1)
        }

        s = map.reversed()
        while (s.isNotEmpty()) {
            if (s == s.reversed() && s.size % 2 == 0) {
                lines.add(s.size / 2)
            }
            s = s.drop(1)
        }
        return lines
    }
}