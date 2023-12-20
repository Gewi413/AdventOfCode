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
            if(state == "A") thing.values.sum() else 0
        })
    }

    private fun rule(line: String): Pair<String, List<Transition>> {
        val (foo, bar, _) = line.split("{", "}")
        val baz = bar.split(",")
        return foo to baz.map {
            if (":" !in it) BasicTransition(it) else {
                val (_, a, b) = it.split(":", "<", ">")
                val r = if ("<" in it) 0 until a.toInt() else a.toInt() + 1..1000000
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