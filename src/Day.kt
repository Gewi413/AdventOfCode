import java.io.File
import java.io.OutputStream
import java.io.PrintStream
import kotlin.system.measureNanoTime

abstract class Day(val id: Int) {
    val input = File("challenges/day$id.txt").readLines()
    abstract fun main()

    companion object {
        private val days = (1..25)
            .mapNotNull {
                try {
                    val name = it.toString().padStart(2, '0')
                    val clazz = Class.forName("Day$name")
                    clazz.declaredFields.first { f -> f.name == "INSTANCE" }.get(null) as Day
                } catch (ignored: Exception) {
                    null
                }
            }

        @JvmStatic
        fun main(args: Array<String>) {
            if (args.isEmpty()) days.last().main()
            else if ("-p" in args) {
                val stdout = System.out
                System.setOut(PrintStream(OutputStream.nullOutputStream()))
                val times = days.map {
                    val time = (0..9).sumOf { _ -> measureNanoTime { it.main() } } / 10
                    Triple(it, formatTime(time), time)
                }
                System.setOut(stdout)

                times.forEach { println("Day ${it.first.id}:${it.second}") }
                println("\nTotal:${formatTime(times.sumOf { it.third })}")
            } else if (args[0].toIntOrNull() != null) days[args[0].toInt() - 1].main()
        }

        private fun formatTime(time: Long) = when (time) {
            in 0..999 -> "${time}ns"
            in 1000..999999 -> "${time / 1000}µs"
            in 1000000..999999999 -> "${time / 1000000}ms"
            in 1000000000..59999999999 -> "${time / 1000000000}s"
            else -> "${time / 60000000000}min"
        }.padStart(7)
    }
}
