import kotlin.math.absoluteValue

object Day11 : Day(11) {
    override fun main() {
        val map = input.map { it.replaceAll("#1", ".0") }.toIntMap().filter { it.value != 0 }.keys
        println(calc(map, 2) / 2)
        println(calc(map, 1000000) / 2)
    }

    private fun calc(map: Set<Point>, size: Int): Long {
        val (minX, maxX) = map.map { it.first }.minMax()
        val (minY, maxY) = map.map { it.second }.minMax()
        val skipX = (minX..maxX) - map.map { it.first }.toSet()
        val skipY = (minY..maxY) - map.map { it.second }.toSet()
        val expanded = map.map {
            it.first + skipX.filter { x -> x < it.first }.size * (size - 1) to
                    it.second + skipY.filter { y -> y < it.second }.size * (size - 1)
        }.toSet()
        var sum = 0L
        for ((x1, y1) in expanded) for ((x2, y2) in expanded) if (x1 to y1 != x2 to y2) {
            sum += (x1 - x2).absoluteValue + (y1 - y2).absoluteValue
        }
        return sum
    }
}