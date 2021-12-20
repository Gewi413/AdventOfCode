object Day20 : Day(20) {
    override fun main() {
        val rules = input[0].mapIndexedNotNull { i, c -> if (c == '#') i else null }.toSet()
        val initial = mutableSetOf<Point>()
        for ((y, line) in input.drop(2).withIndex()) for ((x, char) in line.withIndex())
            if (char == '#') initial += x to y
        var (minX, maxX) = initial.map { it.first }.minMax()
        var (minY, maxY) = initial.map { it.second }.minMax()
        minX -= 300
        minY -= 300
        maxX += 300
        maxY += 300
        var state = initial.toSet()
        for (i in 1..50) {
            val out = mutableSetOf<Point>()
            for (x in minX..maxX) for (y in minY..maxY) {
                val num = mutableListOf<Boolean>()
                for (dy in -1..1) for (dx in -1..1) {
                    num += (x + dx) to (y + dy) in state
                }
                if (num.toInt() in rules) out += x to y
            }
            state = out
            minX += 3
            minY += 3
            maxX -= 3
            maxY -= 3
            if (i == 2 || i == 50) println(state.size)
        }
    }
}