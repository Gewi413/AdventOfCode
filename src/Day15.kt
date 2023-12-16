object Day15 : Day(15) {
    override fun main() {
        val lenses = input.single().split(",")
        println(lenses.sumBy(::hash))
        val state = mutableMapOf<Int, MutableList<Pair<String, Int>>>()
        for (l in lenses) {
            val key = l.split("=", "-").first()
            val hash = hash(key)
            if ('-' in l) {
                val box = state[hash] ?: continue
                box.removeAll { it.first == key }
                continue
            }
            val value = l.split("=").last().toInt()
            val box = state.computeIfAbsent(hash) { mutableListOf() }
            val i = box.indexOfFirst { it.first == key }
            val next = key to value
            if (i == -1) box += next
            else box[i] = next
        }
        println(state.entries.sumBy { (k, v) ->
            (k + 1) * v.mapIndexed { i, (_, value) -> (i + 1) * value }.sumBy { it }
        })
    }

    private fun hash(line: String): Int {
        var state = 0
        for (c in line) {
            state += c.toInt()
            state *= 17
            state %= 256
        }
        return state
    }

}