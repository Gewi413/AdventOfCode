object Day15 : Day(15) {
    override fun main() {
        val (grid, moves) = input.joinToString("\n").split("\n\n")
        partA(grid, moves)
        partB(grid, moves)
    }

    private fun partB(grid: String, moves: String) {
        val map = grid
            .replace(".", "00")
            .replace("#", "11")
            .replace("O", "23")
            .replace("@", "40")
            .split("\n")
            .toIntMap()
            .filter { it.value != 0 }
            .toMutableMap()
        var pos: Point = map.entries.single { it.value == 4 }.key
        for (c in moves) {
            val dir = when (c) {
                '^' -> 0 to -1
                'v' -> 0 to 1
                '<' -> -1 to 0
                '>' -> 1 to 0
                '\n' -> continue
                else -> throw RuntimeException("how")
            }
            if (dir.second == 0) {

                if (tryMove(map, pos, dir)) {
                    pos += dir
                }
            } else {
                if (tryMoveB(map, pos, dir)) {
                    pos += dir
                }
            }
        }
        println(
            map.entries
                .filter { it.value == 2 }
                .sumBy { it.key.first + 100 * it.key.second })
    }


    private fun partA(grid: String, moves: String) {
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
        for (c in moves) {
            val dir = when (c) {
                '^' -> 0 to -1
                'v' -> 0 to 1
                '<' -> -1 to 0
                '>' -> 1 to 0
                '\n' -> continue
                else -> throw RuntimeException("how")
            }
            if (tryMove(map, pos, dir)) {
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
        if (pos !in map) {
            return true
        }
        if (map[pos] == 1) {
            return false
        }
        if (!tryMove(map, pos + dir, dir)) {
            return false
        }
        map[pos + dir] = map.remove(pos)!!
        return true
    }

    private fun tryMoveB(map: MutableMap<Point, Int>, pos: Point, dir: Point): Boolean {
        if (!canMoveB(map, pos, dir)) {
            return false
        }
        if (pos !in map) {
            return true
        }
        val moveme = when {
            map[pos] == 2 -> listOf(pos, pos + (1 to 0))
            map[pos] == 3 -> listOf(pos, pos + (-1 to 0))
            else -> listOf(pos)
        }
        for(move in moveme) {
            tryMoveB(map, move + dir, dir)
        map[move + dir] = map.remove(move)!!

        }
        return true
    }

    private fun canMoveB(map: MutableMap<Point, Int>, pos: Point, dir: Point): Boolean {
        if (pos !in map) {
            return true
        }
        if (map[pos] == 1) {
            return false
        }
        return when {
            map[pos] == 2 -> canMoveB(map, pos + dir, dir) && canMoveB(map, pos + dir + (1 to 0), dir)
            map[pos] == 3 -> canMoveB(map, pos + dir, dir) && canMoveB(map, pos + dir + (-1 to 0), dir)
            else -> canMoveB(map, pos + dir, dir)
        }
    }

}