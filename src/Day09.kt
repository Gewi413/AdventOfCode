object Day09 : Day(9) {
    override fun main() {
        val heights = mutableMapOf<Point, Int>()
        val width = input[0].length
        val height = input.size
        for ((y, line) in input.withIndex()) for ((x, char) in line.withIndex())
            heights[x to y] = char - '0'
        val low = mutableListOf<Point>()
        for (x in 0 until width) for (y in 0 until height) {
            val curr = heights[x to y]!!
            if (listOf(x to y + 1, x to y - 1, x + 1 to y, x - 1 to y).all { (heights[it] ?: 10) > curr })
                low += x to y
        }
        println(low.sumBy { heights[it]!! + 1 })
        println(low.map { checkSize(heights, it)!!.size }.sorted().takeLast(3).fold(1) { a, b -> a * b })
    }

    private fun checkSize(heights: Map<Point, Int>, point: Point): Set<Point>? {
        if ((heights[point] ?: 9) == 9) return null
        val (x, y) = point
        return listOf(x to y + 1, x to y - 1, x + 1 to y, x - 1 to y)
            .mapNotNull { if (heights[point]!! < (heights[it] ?: 9)) checkSize(heights, it) else null }
            .fold(setOf(point)) { a, b -> a + b }
    }
}