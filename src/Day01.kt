object Day01 : Day(1) {
    override fun main() {
        val elves = input
            .joinToString("\n")
            .split("\n\n")
            .map { it.split("\n").toInt().sum() }
            .sortedDescending()
        println(elves[0])
        println(elves.take(3).sum())
    }
}