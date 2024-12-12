object Day12 : Day(12) {
    override fun main() {
        val map = input.toIntMap(36)

        val (minX, maxX) = map.keys.map { it.first }.minMax()
        val (minY, maxY) = map.keys.map { it.second }.minMax()

        val mapped = mutableSetOf<Point>()
        var sum = 0
        var sumB = 0
        for (x in minX..maxX) {
            for (y in minY..maxY) {
                val point = x to y
                if (point in mapped) {
                    continue
                }
                val next = mutableListOf(point)
                val region = mutableSetOf<Point>()
                val edges = mutableListOf<Pair<Point, Point>>()
                val type = map[point]!!
                while (next.isNotEmpty()) {
                    val curr = next.removeFirst()
                    if (curr in region) {
                        continue
                    }
                    region.add(curr)
                    for (neighbor in curr.neighbors()) {
                        if (map[neighbor] != type) {
                            edges += curr to (neighbor - curr)
                            continue
                        }
                        next.add(neighbor)
                    }
                }
                sum += region.size * edges.size
                var edgeCount = 0
                while (edges.isNotEmpty()) {
                    val (curr, dir) = edges.removeFirst()
                    edgeCount++
                    // getting fired any% (sadly kotlin has no for(;;))
                    var i = 0
                    while (edges.remove((curr + (dir.second to dir.first) * ++i) to dir));
                    i = 0
                    while (edges.remove((curr + (dir.second to dir.first) * --i) to dir));
                }
                sumB += region.size * edgeCount
                mapped += region
            }
        }
        println(sum)
        println(sumB)
    }
}