import java.io.File

fun main() {
    val numbers = File("challenges/day1").readLines().map { it.toInt() }
    val filtered1 = numbers.filter { n1 -> numbers.any { n2 -> n1 + n2 == 2020 } }
    println(filtered1.reduce { acc, i -> acc * i })
    val filtered2 = numbers.filter { n1 -> numbers.any { n2 -> numbers.any { n3 -> n1 + n2 + n3 == 2020 } } }
    println(filtered2.reduce { acc, i -> acc * i })
}