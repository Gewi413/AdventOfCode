import java.io.File

object Day14 : Day(14) {
    override fun main() {
        File("/tmp/aoc/").mkdirs()
        val robots = input.map { line ->
            val (p, v) = line.split(" ")
                .map { it.drop(2) }
                .map { it.split(",") }
                .map {
                    val (a, b) = it.toInt()
                    a to b
                }
            Robot(p, v)
        }
        val width = 101
        val height = 103
        var cycle = 0 to 0
        for (i in 1..20000) {
            robots.forEach {
                // all my homies hate java for not mathematically doing modulo
                it.pos = (it.pos.first + it.vel.first + 100 * width) % width to
                        (it.pos.second + it.vel.second + 100 * height) % height
            }
            if (i == 100) {
                println(robots.groupBy {
                    when {
                        it.pos.first < width / 2 && it.pos.second < height / 2 -> 1
                        it.pos.first < width / 2 && it.pos.second > height / 2 -> 2
                        it.pos.first > width / 2 && it.pos.second < height / 2 -> 3
                        it.pos.first > width / 2 && it.pos.second > height / 2 -> 4
                        else -> 0
                    }
                }
                    .filter { it.key != 0 }
                    .map { it.value.size.toLong() }
                    .reduce { acc, j -> acc * j })
            }
            if (robots.groupBy { it.pos.first }.filter { it.value.size > 30 }.size == 2) {
                cycle = i to cycle.second
                if(cycle.second != 0) {
                    break
                }
            }
            if (robots.groupBy { it.pos.second }.filter { it.value.size > 30 }.size == 2) {
                cycle = cycle.first to i
                if(cycle.first != 0) {
                    break
                }
            }
        }
        // cba, but https://en.wikipedia.org/wiki/Chinese_remainder_theorem
        for(i in 1..width * height) {
            if(i % width == cycle.first && i % height == cycle.second) {
                println(i)
            }
        }
    }

    data class Robot(var pos: Point, val vel: Point)
}