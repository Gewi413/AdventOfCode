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
        spaces.forEach { Region.add(it) }
        println(Region.spaces)
        println(Region.area())
    }

    data class Region(val op: Boolean, val x: IntRange, val y: IntRange, val z: IntRange) {
        fun area() = x.toList().size.toLong() * y.toList().size * z.toList().size

        fun split(other: Region) {
            spaces.remove(this)
            spaces += listOf(this).flatMap { box ->
                if (other.x.first in box.x && box.area() != 1L) if (other.x.last in box.x) listOf(
                    Region(true, box.x.first until other.x.first, box.y, box.z),
                    Region(true, other.x.first..other.x.last, box.y, box.z),
                    Region(true, other.x.last + 1..box.x.last, box.y, box.z)
                ) else listOf(
                    Region(true, box.x.first until other.x.first, box.y, box.z),
                    Region(true, other.x.first..box.x.last, box.y, box.z)
                ) else if (other.x.last in box.x) listOf(
                    Region(true, box.x.first..other.x.last, box.y, box.z),
                    Region(true, other.x.last + 1..box.x.last, box.y, box.z)
                ) else listOf(box)
            }.flatMap { box ->
                if (other.y.first in box.y && box.area() != 1L) if (other.y.last in box.y) listOf(
                    Region(true, box.x, box.y.first until other.y.first, box.z),
                    Region(true, box.x, other.y.first..other.y.last, box.z),
                    Region(true, box.x, other.y.last + 1..box.y.last, box.z)
                ) else listOf(
                    Region(true, box.x, box.y.first until other.y.first, box.z),
                    Region(true, box.x, other.y.first..box.y.last, box.z)
                ) else if (other.x.last in box.y) listOf(
                    Region(true, box.x, box.y.first..other.y.last, box.z),
                    Region(true, box.x, other.y.last + 1..box.y.last, box.z)
                ) else listOf(box)
            }.flatMap { box ->
                if (other.z.first in box.z && box.area() != 1L) if (other.z.last in box.z) listOf(
                    Region(true, box.x, box.y, box.z.first until other.z.first),
                    Region(true, box.x, box.y, other.z.first..other.z.last),
                    Region(true, box.x, box.y, other.z.last + 1..box.z.last)
                ) else listOf(
                    Region(true, box.x, box.y, box.z.first until other.z.first),
                    Region(true, box.x, box.y, other.z.first..box.z.last)
                ) else if (other.x.last in box.z) listOf(
                    Region(true, box.x, box.y, box.z.first..other.z.last),
                    Region(true, box.x, box.y, other.z.last + 1..box.z.last)
                ) else listOf(box)
            }.filter {
                it.area() > 0
            }
        }

        override fun toString() = "Region(x=$x, y=$y${if (z == 0..0) "" else ", z=$z"})"

        companion object {
            val spaces = mutableSetOf<Region>()

            fun add(r: Region) {
                spaces.toList().forEach { it.split(r) }
                if (r.op) spaces += r
            }

            fun area() = spaces.sumOf { it.area() }
        }
    }
}

fun <T> T.l() = this.also { println(it) }