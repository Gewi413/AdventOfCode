object Day03 : Day(3) {
    override fun main() {
        println(input[0]
            .indices
            .map { bit -> input.map { it[bit] == '1' }.count { it } * 2 > input.size }
            .joinToString("") { if (it) "1" else "0" }
            .toInt(2)
            .let { it * (it xor ((1 shl input[0].length) - 1)) }
        )
        println(solve() * solve(true))
    }

    private fun solve(flip: Boolean = false): Int {
        var copy = input
        for (i in input[0].indices) {
            val keep = if ((copy.count { it[i] == '1' } * 2 >= copy.size) xor flip) '1' else '0'
            copy = copy.filter { copy.size == 1 || it[i] == keep }
        }
        return copy[0].toInt(2)
    }
}