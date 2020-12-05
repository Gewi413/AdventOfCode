import java.io.File
import kotlin.math.pow

fun main() {
    val input = File("challenges/day5").readLines()
    val seats = input.map { line ->
        line.reversed().mapIndexed { i, c -> if (c in "BR") 2.0.pow(i.toDouble()) else 0.0 }.sum().toInt()
    }
    println(seats.maxOrNull())
    println(((seats.minOrNull()!!..seats.maxOrNull()!!) - seats).first())
}