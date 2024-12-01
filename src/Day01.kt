import kotlin.math.absoluteValue

object Day01 : Day(1) {
    override fun main() {
        val (first, second) = input.map { line ->
            val (x, y) = line
                .split("   ")
                .map { it.toInt() }
            x to y
        }.unzip()

        println(first.sorted().zip(second.sorted()).sumBy {
            (it.first - it.second).absoluteValue
        })

        println(first.sumBy {
            it * second.count { num -> num == it }
        })
    }
}