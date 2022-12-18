object Day18 : Day(18) {
    override fun main() {
        val cubes = input.map { it.split(",").toInt().let { (a, b, c) -> Triple(a, b, c) } }.toSet()
        val sides = listOf(
            Triple(1, 0, 0), Triple(0, 1, 0), Triple(0, 0, 1),
            Triple(-1, 0, 0), Triple(0, -1, 0), Triple(0, 0, -1),
        )
        println(cubes.sumBy { cube ->
            sides.count { cube + it !in cubes }
        })

        var count = 0
        val done = mutableSetOf<Pair<Triple<Int, Int, Int>, Triple<Int, Int, Int>>>()

        doUntilSettled(setOf(Triple(0, 0, 0))) { initial ->
            initial + initial.flatMap { cube ->
                sides.mapNotNull { side ->
                    val test = cube + side
                    if (test in initial) return@mapNotNull null
                    if (test.toList().any { it !in -1..22 }) return@mapNotNull null
                    if (test in cubes) {
                        if (cube to side !in done) count++
                        done += cube to side
                        return@mapNotNull null
                    }
                    test
                }
            }
        }
        println(count)
    }

    private operator fun Triple<Int, Int, Int>.plus(other: Triple<Int, Int, Int>) =
        Triple(first + other.first, second + other.second, third + other.third)
}