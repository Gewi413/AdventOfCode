import kotlin.math.pow

object Day04 : Day(4) {
    override fun main() {
        val counts = input.map { line ->
            val (_, goal, actual) = line.split(": ", "|")
            val numbers = goal.split(" ").toSet() - ""
            actual.split(" ").count { it in numbers }
        }
        println(counts.sumBy { if (it == 0) 0 else 2.0.pow(it - 1).toInt() })
        val out = MutableList(counts.size) { 1 }
        counts.forEachIndexed { i, count ->
            var index = i
            repeat(count) {
                out[++index] += out[i]
            }
        }
        println(out.sum())
    }
}