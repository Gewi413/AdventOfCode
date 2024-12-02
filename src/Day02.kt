import kotlin.math.absoluteValue

object Day02 : Day(2) {
    override fun main() {
        println(input.map { it.split(" ").toInt() }.count {
            valid(it)
        })

        println(input.map { it.split(" ").toInt() }.count { line ->
            line.indices.any { i -> valid(line.filterIndexed { index, _ -> index != i }) }
        })
    }

    private fun valid(line: List<Int>): Boolean {
        val ints = line.windowed(2)
        return ints.all { (it[0] - it[1]).absoluteValue in 1..3 } and
                (ints.all { (it[0] < it[1]) } or ints.all { (it[0] > it[1]) })
    }
}