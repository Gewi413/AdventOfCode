object Day13 : Day(13) {
    override fun main() {
        val packets = input.mapNotNull { if (it.isEmpty()) null else Packet(it) }
        println(packets.chunked(2).mapIndexed { i, (a, b) ->
            if (a == b) println("$a, $b")
            if (a < b) i + 1 else 0
        }.sum())

        val dividers = listOf(Packet("[[2]]"), Packet("[[6]]"))
        val (a, b) = (packets + dividers).sorted()
            .mapIndexedNotNull { i, packet -> if (packet in dividers) i + 1 else null }
        println(a * b)
    }

    sealed class Packet : Comparable<Packet> {
        abstract override operator fun compareTo(other: Packet): Int

        companion object {
            operator fun invoke(input: String) = if (input.first() == '[') ListPacket(input) else IntPacket(input)
        }

        class IntPacket(input: String) : Packet() {
            private val value: Int
            val list get() = Packet("[$value]")

            init {
                value = input.toInt()
            }

            override fun compareTo(other: Packet): Int = when (other) {
                is IntPacket -> value.compareTo(other.value)
                is ListPacket -> list.compareTo(other)
            }

            override fun toString(): String = value.toString()
        }

        class ListPacket(input: String) : Packet() {
            private val children: List<Packet>

            init {
                var counter = 0
                var curr = input.drop(1).dropLast(1)
                val childs = mutableListOf<Packet>()
                while (curr.isNotEmpty()) {
                    val next = curr.takeWhile {
                        if (it == '[') counter++ else if (it == ']') counter--
                        !(counter == 0 && it == ',')
                    }
                    curr = curr.drop(next.length)
                    if (curr.startsWith(',')) curr = curr.drop(1)
                    childs += Packet(next)
                }
                children = childs
            }

            override fun compareTo(other: Packet): Int = when (other) {
                is IntPacket -> compareTo(other.list)
                is ListPacket -> {
                    children
                        .zip(other.children)
                        .firstOrNull { (a, b) -> a.compareTo(b) != 0 }
                        ?.let { (a, b) -> a.compareTo(b) } ?: children.size.compareTo(other.children.size)
                }
            }

            override fun toString(): String =
                children.joinToString(",", "[", "]")
        }
    }
}