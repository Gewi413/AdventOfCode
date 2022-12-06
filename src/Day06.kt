object Day06 : Day(6) {
    override fun main() {
        println(run(4))
        println(run(14))
    }

    private fun run(length: Int) = input[0].windowed(length).indexOfFirst { it.toSet().size == length } + length
}