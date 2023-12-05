object Day05 : Day(5) {
    override fun main() {
        val steps = input.joinToString("\n").split("\n\n")
        val seeds = steps.first().split(" ").drop(1).toLong()
        var state = seeds
        for (next in steps.drop(1)) {
            val mappings = next.split("\n").drop(1).map {
                val (dest, source, len) = it.split(" ").toLong()
                source until (source + len) to dest
            }
            state = state.map { seed ->
                val new = mappings.firstOrNull { seed in it.first }
                if (new == null) seed
                else new.second + seed - new.first.first
            }
        }
        println(state.minOrNull())
    }
}