object Day16 : Day(16) {
    override fun main() {
        val bits = input[0].map { it.toString().toInt(16).toString(2).padStart(4).map { c -> c == '1' } }.flatten()
        val packet = Packet(bits)
        println(packet.versionSum())
        println(packet.eval())
    }

    class Packet(input: List<Boolean>) {
        private val version = input.take(3).toInt()
        private val type = input.drop(3).take(3).toInt()
        private val remaining = input.drop(6)
        private var garbage: List<Boolean>? = null
        private val data = if (type != 4) null else {
            val blocks = remaining.chunked(5)
            var out = 0L
            for ((i, b) in blocks.withIndex()) {
                out = out shl 4
                out += b.drop(1).toInt()
                if (!b[0]) {
                    garbage = blocks.drop(i + 1).flatten()
                    break
                }
            }
            out
        }

        private val children = if (type == 4) null else {
            if (!remaining[0]) {
                val out = mutableListOf<Packet>()
                val len = remaining.drop(1).take(15).toInt()
                var rest = remaining.drop(16).take(len)
                while (rest.isNotEmpty()) {
                    out += Packet(rest)
                    rest = out.last().garbage ?: break
                }
                garbage = remaining.drop(16 + len)
                out
            } else {
                val out = mutableListOf<Packet>()
                val len = remaining.drop(1).take(11).toInt()
                var rest = remaining.drop(12)
                for (i in 0 until len) {
                    out += Packet(rest)
                    rest = out.last().garbage ?: break
                }
                garbage = rest
                out
            }
        }

        fun versionSum(): Int = version + (children?.sumBy { it.versionSum() } ?: 0)

        fun eval(): Long = when (type) {
            0 -> children!!.fold(0L) { acc, i -> acc + i.eval() }
            1 -> children!!.fold(1L) { acc, i -> acc * i.eval() }
            2 -> children!!.minOf { it.eval() }
            3 -> children!!.maxOf { it.eval() }
            4 -> data!!
            5 -> if (children!![0].eval() > children[1].eval()) 1L else 0L
            6 -> if (children!![0].eval() < children[1].eval()) 1L else 0L
            7 -> if (children!![0].eval() == children[1].eval()) 1L else 0L
            else -> TODO()
        }

        override fun toString() =
            "Packet[version=$version,type=$type,data=${if (type == 4) data.toString() else children.toString()}]"
    }
}
