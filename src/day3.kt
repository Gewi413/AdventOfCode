import java.io.File

fun main() {
    val input = File("challenges/day3").readLines()
    val length = input[0].length
    fun traverse(x: Int, y: Int): Int {
        var pos = 0
        var trees = 0
        for(i in input.indices step y) {
            if(input[i][pos] == '#') trees++
            pos = (pos + x) % length
        }
        return trees
    }
    println(traverse(3, 1))
    val options = "1,1;3,1;5,1;7,1;1,2"
    println(options.split(";").map {
        val (x, y) = it.split(",").map { it.toInt() }
        traverse(x, y)
    }.map {it.toLong()}.reduce { i, acc ->	acc * i	}
    )
}