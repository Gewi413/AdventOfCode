import java.io.File

fun main() {
    val input = File("challenges/day3").readLines()
    val length = input[0].length
    fun traverse(dir: Pair<Int, Int>): Int {
        var x = 0
        var trees = 0
        for (y in input.indices step dir.second) {
            if (input[y][x] == '#') trees++
            x = (x + dir.first) % length
        }
        return trees
    }
    println(traverse(Pair(3, 1)))

    val options = listOf(Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2))
    println(options.map { traverse(it) }.map { it.toLong() }.reduce { acc, i -> acc * i })
}