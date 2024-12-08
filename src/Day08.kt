object Day08 : Day(8) {
    override fun main() {
        val map = mutableMapOf<Char, List<Point>>()
        for ((y, line) in input.withIndex()) {
            for ((x, char) in line.withIndex()) {
                if (char == '.') {
                    continue
                }
                if (char !in map) {
                    map[char] = listOf()
                }
                map[char] = map[char]!! + (x to y)
            }
        }

        val antinodes = mutableSetOf<Point>()
        val antinodesB = mutableSetOf<Point>()
        for ((_, poss) in map) {
            for (pos1 in poss) {
                for (pos2 in poss) {
                    if (pos1 == pos2) {
                        continue
                    }
                    for (i in 0..50) {
                        antinodesB += pos1 + (pos1 - pos2) * i
                        antinodesB += pos2 + (pos2 - pos1) * i
                    }
                    antinodes += pos1 + (pos1 - pos2)
                    antinodes += pos2 + (pos2 - pos1)
                }
            }
        }
        val (partA, partB) = listOf(antinodes, antinodesB).map { it.filter { (x, y) -> x in input[0].indices && y in input.indices }.size }
        println(partA)
        println(partB)
    }
}
