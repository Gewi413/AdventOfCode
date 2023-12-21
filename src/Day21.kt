object Day21 : Day(21) {
    override fun main() {
        val all = input.map { it.replaceAll("#1", ".0", "S2") }.toIntMap()
        val start = all.entries.single { it.value == 2 }.key
        val hedge = all.entries.filter { it.value == 1 }.map { it.key }.toSet()
        var positions = setOf(start)
        val sizes = mutableListOf<Int>()
        repeat(100) {
            sizes += positions.size
            positions = positions.flatMap { it.neighbors() }.toSet().filter { (x, y) ->
                (x % input[0].length + input[0].length) % input[0].length to (y % input.size + input.size) % input.size !in hedge
            }.toSet()
        }
        println(sizes[64])
    }
}