import kotlin.math.max
import kotlin.math.min

object Day22 : Day(22) {
    override fun main() {
        val initial = input.map { line ->
            val (a, b) = line.split("~").map { it.split(",").toInt() }
            when {
                a[0] != b[0] -> Brick((min(a[0], b[0])..max(a[0], b[0])).map { listOf(it, a[1], a[2]) })
                a[1] != b[1] -> Brick((min(a[1], b[1])..max(a[1], b[1])).map { listOf(a[0], it, a[2]) })
                a[2] != b[2] -> Brick((min(a[2], b[2])..max(a[2], b[2])).map { listOf(a[0], a[1], it) })
                else -> Brick(listOf(a))
            }
        }

        val now = doUntilSettled(initial) { bricks ->
            val taken = bricks.flatMap { it.positions }.toSet()
            bricks.map {
                if (it.positions.any { pos -> pos[2] <= 1 || (down(pos) in taken && down(pos) !in it.positions) }) it
                else Brick(it.positions.map(::down))
            }
        }
        now.forEach { brick ->
            val below = brick.positions.map(::down).toSet()
            brick.supported = now.filter { it.positions.any { p -> p in below } }.toSet() - brick
        }
        println(now.count { down ->
            !now.any { it.supported.singleOrNull() == down }
        })
    }

    private fun down(pos: List<Int>) = listOf(pos[0], pos[1], pos[2] - 1)

    data class Brick(val positions: List<List<Int>>) {
        companion object{
            private  var counter = 1
        }
        var supported = setOf<Brick>()
        val id = counter++
        override fun toString(): String {
            return id.toString()
        }
    }
}