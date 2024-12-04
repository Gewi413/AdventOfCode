object Day04 : Day(4) {
    override fun main() {
        val map = input.toIntMap(36)
        println(partA(map))
        println(partB(map))
    }

    private fun partA(map: Map<Point, Int>): Int {
        val (minX, maxX) = map.keys.map { it.first }.minMax()
        val (minY, maxY) = map.keys.map { it.second }.minMax()
        val xmas = "XMAS".toCharArray().map { it.toString().toInt(36) }
        var count = 0
        for (x in minX..maxX) {
            for (y in minY..maxY) {
                for (dx in -1..1) {
                    outer@ for (dy in -1..1) {
                        val dir = dx to dy
                        for (i in 0..3) {
                            val pos = (x to y) + dir * i
                            if (map[pos] != xmas[i]) {
                                continue@outer
                            }
                        }
                        count++
                    }
                }
            }
        }
        return count
    }

    private fun partB(map: Map<Point, Int>): Int {
        val (minX, maxX) = map.keys.map { it.first }.minMax()
        val (minY, maxY) = map.keys.map { it.second }.minMax()
        val xmas = "AMS".toCharArray().map { it.toString().toInt(36) }
        var count = 0
        for (x in minX..maxX) {
            for (y in minY..maxY) {
                val pos = x to y
                if (map[pos] != xmas[0]) {
                    continue
                }
                if (
                    map[pos + (1 to 1)] in xmas.drop(1) &&
                    map[pos + (-1 to -1)] in xmas.drop(1) &&
                    (map[pos + (1 to 1)] != map[pos + (-1 to -1)]) &&
                    ((map[pos + (1 to 1)] == map[pos + (-1 to 1)] &&
                            map[pos + (1 to -1)] == map[pos + (-1 to -1)])
                            ||
                            (map[pos + (1 to 1)] == map[pos + (1 to -1)] &&
                                    map[pos + (-1 to 1)] == map[pos + (-1 to -1)]))
                ) {
                    count++
                }
            }
        }
        return count
    }
}
