object Day08 : Day(8) {
    override fun main() {
        val map = input.toIntMap()
        val visible = mutableSetOf<Point>()
        for (x in input[0].indices) {
            var temp = -1
            for (y in input.indices) {
                if ((map[x to y] ?: -1) > temp) {
                    visible += x to y
                    temp = map[x to y]!!
                }
            }
            temp = -1
            for (y in input.indices.reversed()) {
                if ((map[x to y] ?: -1) > temp) {
                    visible += x to y
                    temp = map[x to y]!!
                }
            }
        }

        for (y in input.indices) {
            var temp = -1
            for (x in input[0].indices) {
                if ((map[x to y] ?: -1) > temp) {
                    visible += x to y
                    temp = map[x to y]!!
                }
            }
            temp = -1
            for (x in input[0].indices.reversed()) {
                if ((map[x to y] ?: -1) > temp) {
                    visible += x to y
                    temp = map[x to y]!!
                }
            }
        }

        println(visible.size)

        val visibility = mutableMapOf<Point, Int>()
        for (y in input.indices) for (x in input[0].indices) {
            val counts = mutableListOf(0, 0, 0, 0)
            val current = map[x to y]!!
            val temp = mutableSetOf<Point>()

            var ix = x
            do {
                ix--
                if(map[ix to y] == null) break
                counts[0]++
                temp += ix to y
            } while (( map[ix to y]?: 10) < current)
            ix = x
            do {
                ix++
                if(map[ix to y] == null) break
                counts[1]++
                temp += ix to y
            } while ((map[ix to y] ?: 10) < current)

            var iy = y
            do {
                iy--
                if(map[x to iy] == null) break
                counts[2]++
                temp += x to iy
            } while ((map[x to iy] ?: 10) < current)
            iy = y
            do {
                iy++
                if(map[x to iy] == null) break
                counts[3]++
                temp += x to iy
                if (map[x to iy] == null) break
            } while ((map[x to iy] ?: 10) < current)

            visibility[x to y] = counts.fold(1) { acc, i -> acc * i }
        }
        println(visibility.values.maxOrNull())
    }
}