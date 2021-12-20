import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

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
            ImageIO.write(save(state), "PNG", File("test/$i.png"))
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

fun save(map: Set<Point>): BufferedImage {
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
