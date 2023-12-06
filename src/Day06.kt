object Day06 : Day(6) {
    override fun main() {
        calculate(input.map { line -> line.split(" +".toRegex()).drop(1).toLong() })
        calculate(input.map { line -> line.replace(" ", "").split(":").drop(1).toLong() })
    }

    private fun calculate(values: List<List<Long>>) {
        println(values[0].zip(values[1]).map { (time, distance) ->
            (0..time).count { it * (time - it) > distance }
        }.fold(1L) { acc, i -> acc * i })
    }
}