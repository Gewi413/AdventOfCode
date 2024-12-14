import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

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
        for (i in 0..20000) {
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
            ImageIO.write(render(robots.map { it.pos }.toSet()), "BMP", File("/tmp/aoc/$i.bmp"))
            robots.forEach {
                // all my homies hate java for not mathematically doing modulo
                it.pos = (it.pos.first + it.vel.first + 100 * width) % width to
                        (it.pos.second + it.vel.second + 100 * height) % height
            }
        }
        println("the answer is somewhere in your /tmp/aoc folder ¯\\_(ツ)_/¯")

    }

    data class Robot(var pos: Point, val vel: Point)

    private fun render(map: Set<Point>): BufferedImage {
        val (minX, maxX) = map.map { it.first }.minMax()
        val (minY, maxY) = map.map { it.second }.minMax()
        val width: Int = maxX - minX
        val height: Int = maxY - minY
        val imageOut = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val imageOutPixels = IntArray((width + 1) * (height + 1))
        for (y in 0..height) for (x in 0..width) {
            imageOutPixels[y * width + x] = if (minX + x to minY + y in map) 0x000000 else 0xFFFFFF
        }
        imageOut.setRGB(0, 0, width, height, imageOutPixels, 0, width)
        return imageOut
    }
}