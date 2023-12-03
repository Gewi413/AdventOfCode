object Day03 : Day(3) {
    override fun main() {
        val gears = mutableMapOf<Point, Int>()
        var total = 0
        var totalGears = 0L
        for ((y, line) in input.withIndex()) {
            var curr = ""
            var next = false
            var gearPos: Point? = null
            for ((x, char) in line.withIndex()) {
                if (char.isDigit()) {
                    curr += char
                    for (neighbor in (x to y).neighbors(true)) {
                        val neighborChar = input.getOrElse(neighbor.second) { "." }.getOrElse(neighbor.first) { '.' }
                        if (neighborChar == '*') gearPos = neighbor
                        if (!neighborChar.isDigit() && neighborChar != '.') next = true
                    }
                } else {
                    if (next) total += curr.toInt()
                    if (gearPos != null)
                        if (gearPos in gears) totalGears += curr.toInt() * gears[gearPos]!!
                        else gears[gearPos] = curr.toInt()
                    gearPos = null
                    curr = ""
                    next = false
                }
            }
            if (next) total += curr.toInt()
            if (gearPos != null)
                if (gearPos in gears) totalGears += curr.toInt() * gears[gearPos]!!
                else gears[gearPos] = curr.toInt()
        }
        println(total)
        println(totalGears)
    }
}
