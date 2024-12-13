import kotlin.math.absoluteValue
import kotlin.math.roundToLong

object Day13 : Day(13) {
    override fun main() {
        val data = input.joinToString("\n")
            .split("\n\n")
            .map { text ->
                val lines = text.split("\n")
                val (aString, bString, goalString) = lines.map { it.split(": ")[1] }
                val (a, b) = listOf(aString, bString).map { line ->
                    val (aa, bb) = line.split(", ").map { it.drop(1) }.toLong()
                    aa to bb
                }
                val (goalX, goalY) = goalString.split(", ").map { it.drop(2) }.toLong()
                val goal = goalX to goalY

                Triple(a, b, goal)
            }

        println(solve(data, 0))
        println(solve(data, 10000000000000) == 87596249540359L)
    }

    private const val EPSILON = 1e-3 // scientifically guesstimated
    private fun solve(data: List<Triple<Pair<Long, Long>, Pair<Long, Long>, Pair<Long, Long>>>, shift: Long): Long {
        // the assumption of 100 presses max doesn't really matter (I hope)
        // unhinged variable names because I wrote them like that on a piece of paper
        return data.map { (a, b, goal) ->
            Triple(a, b, goal.first + shift to goal.second + shift)
        }.sumOf { (point1, point2, goal) ->
            val (a, b) = point1
            val (c, d) = point2
            val (e, f) = goal
            val yImprecise = (((f - d * (e / c.toDouble())) / (c * b.toDouble() / a - d)) * c) / a

            if ((yImprecise - yImprecise.roundToLong()).absoluteValue > EPSILON) {
                return@sumOf 0L
            }
            val y = yImprecise.roundToLong()
            val r1 = (e - y * a) / c
            val r2 = (f - y * b) / d
            if (r1 != r2) {
                throw RuntimeException("wtf")
            }

            // part A to check if my code actually works
//            outer@ for (i in 0..100) {
//                for (j in 0..100) {
//                    if (point1 * i + point2 * j == goal) {
//                        if (i.toLong() != y || j.toDouble() != r1) {
//                            println("EXPECTED: $i, $j - actual: $y, $r1")
//
//                        }
//                        break@outer
//                    }
//                }
//            }

            y * 3 + r1
        }
    }
}

operator fun Pair<Long, Long>.times(other: Int) = first * other to second * other
operator fun Pair<Long, Long>.plus(other: Pair<Long, Long>) = first + other.first to second + other.second