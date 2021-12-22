object Day22 : Day(22) {
    override fun main() {
        val spaces = input.map { line ->
            val l = line.split(" x=", ",y=", ",z=")
            val (x, y, z) = l.drop(1).map { val (a, b) = it.split("..").toInt(); a..b }
            Region(l[0] == "on", x, y, z)
        }
        val on = mutableSetOf<Triple<Int, Int, Int>>()
        val space = (-50..50).toSet()
        spaces.forEach { (op, rx, ry, rz) ->
            val sy = space intersect ry
            val sz = space intersect rz
            for (x in space intersect rx)
                for (y in sy)
                    for (z in sz)
                        if (op) on += x to y to z else on -= x to y to z
        }
        println(on.size)
    }

    data class Region(val op: Boolean, val x: IntRange, val y: IntRange, val z: IntRange)
}