object Day11 : Day(11) {
    override fun main() {
        val energy = input.toIntMap().toMutableMap()
        var flashes = 0
        for (i in 1..10000) {
            for (x in input[0].indices) for (y in input.indices)
                flash(energy, x to y)
            for (x in input[0].indices) for (y in input.indices)
                if ((energy[x to y] ?: 0) > 9) energy[x to y] = 0
            val new = energy.values.count { it == 0 }
            flashes += new
            if (new == 100) {
                println(i)
                break
            }
            if (i == 100) println(flashes)
        }
    }

    private fun flash(octopus: MutableMap<Point, Int>, pos: Point) {
        if (octopus[pos] != null) octopus[pos] = octopus[pos]!! + 1
        if (octopus[pos] == 10) {
            val (x, y) = pos
            for (dx in -1..1) for (dy in -1..1)
                flash(octopus, x + dx to y + dy)
        }
    }
}