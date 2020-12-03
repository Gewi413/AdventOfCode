import java.io.File

fun main() {
    val numbers = File("challenges/day1").readLines().map { it.toInt() }
    println(numbers.filter { n -> numbers.contains(2020 - n) }.reduce { acc, i -> acc * i })
    println(numbers.filter { n1 -> numbers.any { n2 -> numbers.contains(2020 - n1 - n2) } }
        .reduce { acc, i -> acc * i })
}