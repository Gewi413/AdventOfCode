object Day17 : Day(17) {
    override fun main() {
        val target = input[0].split("..", ", y=", "=").drop(1).toInt()
        val heights = (0..target[1]).map { x ->
            (target[2]..1000).map { y -> x to y }
        }.flatten().mapNotNull { (dx, dy) ->
            var x = 0
            var y = 0
            var max = 0
            var velX = dx
            var velY = dy
            while (true) {
                x += velX
                y += velY--
                if (velX > 0) velX--
                if (y > max) max = y
                if (y in target[2]..target[3] && x in target[0]..target[1]) {
                    return@mapNotNull max
                } else if (y < target[2] || x > target[1]) return@mapNotNull null
            }
            null
        }
        println(heights.maxOrNull())
        println(heights.count())
    }
}