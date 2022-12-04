object Day04 : Day(4) {
    override fun main() {
        val ranges = input.map { line ->
            line
                .split(",", "-")
                .map { it.toInt() }
                .let { (a1, a2, b1, b2) -> a1..a2 to b1..b2 }
        }
        println(ranges.count { (a, b) ->
            (a.first in b && a.last in b) || (b.first in a && b.last in a)
        })
        println(ranges.count { (a, b) ->
            a.first in b || a.last in b || b.first in a || b.last in a
        })
    }
}