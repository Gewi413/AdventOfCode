import java.io.File

abstract class Day(id: Int) {

    val input = File("challenges/day$id.txt").readLines()
    abstract fun main()

    companion object {
        private val days = (1..25)
            .mapNotNull {
                try {
                    val name = it.toString().padStart(2, '0')
                    val clazz = Class.forName("Day$name")
                    clazz.declaredFields[0].get(null) as Day
                } catch (ignored: Exception) {
                    null
                }
            }

        @JvmStatic
        fun main(unused: Array<String>) {
            days.last().main()
        }
    }
}
