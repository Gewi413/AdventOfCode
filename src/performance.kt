import java.io.OutputStream
import java.io.PrintStream
import kotlin.system.measureNanoTime
import day01.main as day01
import day02.main as day02
import day03.main as day03
import day04.main as day04
import day05.main as day05
import day06.main as day06
import day07.main as day07
import day08.main as day08
import day09.main as day09
import day10.main as day10
import day11.main as day11
import day12.main as day12
import day13.main as day13
import day14.main as day14
import day15.main as day15

fun main() {
    val days = listOf(
        ::day01, ::day02, ::day03, ::day04, ::day05, ::day06,
        ::day07, ::day08, ::day09, ::day10, ::day11, ::day12,
        ::day13, ::day14, ::day15
    )
    System.setOut(PrintStream(OutputStream.nullOutputStream()))
    var sum = 0L
    for ((i, solution) in days.withIndex()) {
        val time = measureNanoTime { solution() }
        sum += time
        System.err.println("day ${i + 1}:\t${time / 1000000}ms")
    }
    System.err.println("=============")
    System.err.println("sum:\t${sum / 1000000}ms")
}