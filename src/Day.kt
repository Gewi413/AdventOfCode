import java.io.File

abstract class Day(val id: Int) {
    val input = File("challenges/day$id").readLines()
    abstract fun main()

    companion object {
        val days = listOf(Day01)

        @JvmStatic
        fun main(unused: Array<String>) {
            days.last().main()
        }
    }
}
