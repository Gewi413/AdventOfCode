import java.io.File

fun main() {
    var first = 0
    var second = 0
    val input = File("challenges/day2").readLines()
    input.forEach {
        val password = it.split(" ")[2]
        val char = it.split(" ")[1][0]
        val (lower, upper) = it.split(" ")[0].split("-").map { n -> n.toInt() }
        if (password.count { c -> c == char } in lower..upper) first++
        if ((password[lower - 1] == char) xor (password[upper - 1] == char)) second++
    }
    println(first)
    println(second)
}