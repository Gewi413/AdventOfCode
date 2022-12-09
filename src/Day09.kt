object Day09 : Day(9) {
    override fun main() {
        var headPos = 0 to 0
        var tailPos = headPos
        val positions = mutableSetOf(tailPos)
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
                headPos += direction
                if (chessDistance(headPos.toList(), tailPos.toList()) > 1) {
                    tailPos = when (headPos - tailPos) {
                        2 to 1, 2 to 0, 2 to -1 -> headPos.first - 1 to headPos.second
                        -2 to 1, -2 to 0, -2 to -1 -> headPos.first + 1 to headPos.second
                        1 to 2, 0 to 2, -1 to 2 -> headPos.first to headPos.second - 1
                        1 to -2, 0 to -2, -1 to -2 -> headPos.first to headPos.second + 1
                        else -> headPos
                    }
                }
                positions += tailPos
            }
        }
        println(positions.size)
    }
}