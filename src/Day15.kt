object Day15: Day(15) {
    override fun main() {
        val (grid, moves) = input.joinToString("\n").split("\n\n")
        val map = grid
            .replace('.', '0')
            .replace('#', '1')
            .replace('O', '2')
            .replace('@', '3')
            .split("\n")
            .toIntMap()
            .filter { it.value != 0 }
            .toMutableMap()
        var pos: Point = map.entries.single { it.value == 3 }.key
        for(c in moves) {
            val dir = when(c) {
                '^' -> 0 to -1
                'v' -> 0 to 1
                '<' -> -1 to 0
                '>' -> 1 to 0
                '\n' -> continue
                else -> throw RuntimeException("how")
            }
            if(tryMove(map, pos, dir)) {
                pos += dir
            }

            //map.print(mutableSetOf(pos))
        }
        println(
            map.entries
            .filter { it.value == 2 }
            .sumBy { it.key.first + 100 * it.key.second })
    }

    private fun tryMove(map: MutableMap<Point, Int>, pos: Point, dir: Point): Boolean {
        if(pos !in map) {
            return true
        }
        if(map[pos] == 1) {
            return false
        }
        if(!tryMove(map, pos + dir, dir)) {
            return false
        }
        map[pos + dir] = map.remove(pos)!!
        return true
    }
}