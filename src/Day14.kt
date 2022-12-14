object Day14 : Day(14) {
    override fun main() {
        val walls = input.flatMap { line ->
            line.split(" -> ")
                .toPoint()
                .zipWithNext()
                .flatMap { (a, b) ->
                    (a.first range b.first).flatMap { x ->
                        (a.second range b.second).map { y -> x to y } // lets pretend its a rectangle
                    }
                }
        }.toSet()

        println(partA(walls))
        println(partB(walls))
    }

    private fun partA(initial: Set<Point>) = doUntilSettled(initial) { walls ->
        var pos = 500 to 0
        val attempts = listOf(0 to 1, -1 to 1, 1 to 1)
        while (true) {
            if (pos.second > 1000) return@doUntilSettled walls
            pos += attempts.firstOrNull { pos + it !in walls } ?: break
        }
        walls + pos
    }.size - initial.size

    private fun partB(walls: Set<Point>): Int {
        val floor = walls.maxOf { it.second } + 2
        val sands = mutableSetOf<Point>()
        var workingCopy = setOf(500 to 0)
        for (i in 0 until floor) {
            sands += workingCopy
            workingCopy = workingCopy.flatMap { (x, y) ->
                (-1..1).map { x + it to y + 1 }
            }.filter { it !in walls }.toSet()
        }
        return sands.size
    }

    private infix fun Int.range(to: Int) = if (to >= this) this..to else to..this
}