object Day19 : Day(19) {
    override fun main() {
        val things = input.mapNotNull { line ->
            if (line.startsWith("{")) thingy(line)
            else null
        }
        val rules = input.mapNotNull { line ->
            if (!line.startsWith("{") && line.isNotEmpty()) rule(line)
            else null
        }.toMap()
        println(things.sumBy { thing ->
            var state = "in"
            while (state != "A" && state != "R") {
                val next = rules[state]!!
                state = next.first {
                    it is ConditionalTransition && thing[it.variable]!! in it.range ||
                            it is BasicTransition
                }.target
            }
            if (state == "A") thing.values.sum() else 0
        })

        var metastate = listOf("xmas".map { it to 1..4000 }.toMap() to "in")
        var sum = 0L
        while (metastate.any()) {
            metastate = metastate.flatMap { state ->
                val (thing, cond) = state
                val next = rules[cond] ?: return@flatMap listOf(state)
                val out = mutableListOf<Pair<Map<Char, IntRange>, String>>()
                var remaining = thing
                for (rule in next) {
                    if (rule is BasicTransition) {
                        out += listOf(remaining to rule.target)
                        continue
                    }
                    if (rule !is ConditionalTransition) throw NotImplementedError()
                    val a = remaining.toMutableMap()
                    val b = remaining.toMutableMap()
                    val start = remaining[rule.variable]!!.first
                    val end = remaining[rule.variable]!!.last
                    a[rule.variable] = if (rule.range.first <= start) start..rule.range.last else rule.range.first..end
                    b[rule.variable] =
                        if (rule.range.first <= start) rule.range.last + 1..end else start until rule.range.first
                    remaining = b
                    out += a to rule.target
                }
                out
            }
            sum += metastate.filter { it.second == "A" }
                .sumOf { state -> state.first.values.fold(1L) { acc, i -> acc * i.count() } }
            metastate = metastate.filter { it.second != "A" && it.second != "R" }
        }
        println(sum)
    }

    private fun rule(line: String): Pair<String, List<Transition>> {
        val (foo, bar, _) = line.split("{", "}")
        val baz = bar.split(",")
        return foo to baz.map {
            if (":" !in it) BasicTransition(it) else {
                val (_, a, b) = it.split(":", "<", ">")
                val r = if ("<" in it) 0 until a.toInt() else a.toInt() + 1..4000
                ConditionalTransition(it[0], r, b)
            }
        }
    }

    abstract class Transition(val target: String)
    class BasicTransition(target: String) : Transition(target)
    class ConditionalTransition(val variable: Char, val range: IntRange, target: String) : Transition(target)

    private fun thingy(line: String): Map<Char, Int> = line.removeSurrounding("{", "}").split(",").associate {
        val (foo, bar) = it.split("=")
        foo.single() to bar.toInt()
    }
}