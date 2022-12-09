object Day09 : Day(9) {
    override fun main() {
        val pos = (0..9).map { 0 to 0 }.toMutableList()
        val positions = mutableSetOf(pos[0])
        val positionsB = mutableSetOf(pos[0])
        input.forEach { line ->
            val (dir, dist) = line.split(" ")
            val direction = when (dir) {
                "U" -> 0 to -1
                "D" -> 0 to 1
                "L" -> -1 to 0
                "R" -> 1 to 0
                else -> 0 to 0
            }
            repeat(dist.toInt()) {
                pos[0] += direction
                for (i in 0..8)
                    pos[i + 1] = when (pos[i] - pos[i + 1]) {
                        2 to 1, 2 to 0, 2 to -1 -> pos[i].first - 1 to pos[i].second
                        -2 to 1, -2 to 0, -2 to -1 -> pos[i].first + 1 to pos[i].second
                        1 to 2, 0 to 2, -1 to 2 -> pos[i].first to pos[i].second - 1
                        1 to -2, 0 to -2, -1 to -2 -> pos[i].first to pos[i].second + 1
                        2 to 2 -> pos[i].first - 1 to pos[i].second - 1
                        -2 to 2 -> pos[i].first + 1 to pos[i].second - 1
                        2 to -2 -> pos[i].first - 1 to pos[i].second + 1
                        -2 to -2 -> pos[i].first + 1 to pos[i].second + 1
                        else -> pos[i + 1]
                    }

                positions += pos[1]
                positionsB += pos[9]
            }
        }
        println(positions.size)
        println(positionsB.size)
    }
}