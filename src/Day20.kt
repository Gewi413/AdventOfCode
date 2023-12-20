object Day20 : Day(20) {
    override fun main() {
        val modules = input.map { line ->
            val (name, out) = line.split(" -> ")
            val outs = out.split(", ")
            val type = name.first()
            val id = name.drop(1)
            when (type) {
                '%' -> FlipFlop(id, outs)
                '&' -> Conjunction(id, outs)
                else -> Module(name, outs)
            }
        }.associateBy { it.name }.toMutableMap()

        modules.values.forEach { mod ->
            if (mod is Conjunction)
                modules.values.filter { it.outputs.any { target -> target == mod.name } }.forEach {
                    mod.receive(it.name, false)
                }
        }

        var on = 0L
        var off = 0L
        repeat(1000) {
            val queue = mutableListOf(Triple("button", "broadcaster", false))
            while (queue.isNotEmpty()) {
                val (from, to, state) = queue.first()
                //println("$from -$state-> $to")
                queue.removeFirst()
                if (state) on++ else off++
                queue += modules[to]?.receive(from, state) ?: continue
            }
            //println(modules.values.joinToString("\n"))
        }
        // 80349780..
        println(on * off)
        modules.values.forEach {
            if (it is FlipFlop) it.state = false
            else if (it is Conjunction) it.inputs.replaceAll { _, _ -> false }
        }

        modules["rx"] = Output("rx", emptyList())
        var i = 0
        modules.values.single { it.outputs.singleOrNull() == "rx" }
        try {
            while (true) {
                val queue = mutableListOf(Triple("button", "broadcaster", false))
                while (queue.isNotEmpty()) {
                    val (from, to, state) = queue.first()
                    queue.removeFirst()
                    if (state) on++ else off++
                    queue += modules[to]?.receive(from, state) ?: continue
                }
                i++
            }
        } catch (_: Throwable) {
        }
        println(i)
    }

    open class Module(val name: String, val outputs: List<String>) {
        open fun receive(from: String, input: Boolean): List<Triple<String, String, Boolean>> {
            return notifyAll(input)
        }

        protected fun notifyAll(state: Boolean): List<Triple<String, String, Boolean>> =
            outputs.map { Triple(name, it, state) }

        override fun toString(): String {
            return "Module(name='$name', outputs=$outputs)"
        }
    }

    class FlipFlop(name: String, outputs: List<String>) : Module(name, outputs) {
        var state: Boolean = false
        override fun receive(from: String, input: Boolean): List<Triple<String, String, Boolean>> {
            if (input) return emptyList()
            state = !state
            return notifyAll(state)
        }

        override fun toString(): String {
            return "FlipFlop(name='$name', outputs=$outputs, state=$state)"
        }
    }

    class Conjunction(name: String, outputs: List<String>) : Module(name, outputs) {
        val inputs = mutableMapOf<String, Boolean>()
        override fun receive(from: String, input: Boolean): List<Triple<String, String, Boolean>> {
            inputs[from] = input
            return notifyAll(!inputs.values.all { it })
        }

        override fun toString(): String {
            return "Conjunction(name=$name, outputs=$outputs, state=$inputs)"
        }
    }

    class Output(name: String, outputs: List<String>) : Module(name, outputs) {
        override fun receive(from: String, input: Boolean): List<Triple<String, String, Boolean>> {
            if (!input) throw Throwable("wheee")
            return emptyList()
        }
    }
}