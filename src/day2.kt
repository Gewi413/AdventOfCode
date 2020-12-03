import java.io.File

fun main() {
    var first = 0
    var second = 0
    val input = File("challenges/day2").readLines()
    for(line in input) {
        val password = line.split(" ")[2]
        val char = line.split(" ")[1][0]
        val (lower, upper) = line.split(" ")[0].split("-").map { it.toInt() }

        if (password.count { c -> c == char } in lower..upper) first++
        if ((password[lower - 1] == char) xor (password[upper - 1] == char)) second++
    }
    println(first)
    println(second)
}