object Day03 : Day(3) {
    override fun main() {
        val gears = mutableMapOf<Point, Int>()
        var total = 0
        var totalGears = 0L
        for ((y, line) in input.withIndex()) {
            var curr = ""
            var next = false
            val gearsPos = mutableSetOf<Point>()
            for ((x, char) in line.withIndex()) {
                if (char.isDigit()) {
                    curr += char
                    for (neighbor in (x to y).neighbors(true)) {
                        val neighborChar = input.getOrElse(neighbor.second) { "." }.getOrElse(neighbor.first) { '.' }
                        if (neighborChar == '*') gearsPos += neighbor
                        if (!neighborChar.isDigit() && neighborChar != '.') next = true
                    }
                } else {
                    if (next) total += curr.toInt()
                    gearsPos.forEach {
                        if (it in gears) {
                            totalGears += curr.toInt() * gears[it]!!
                        } else gears[it] = curr.toInt()
                    }
                    gearsPos.clear()
                    curr = ""
                    next = false
                }
            }
            if (next) total += curr.toInt()
            gearsPos.forEach {
                if (it in gears) {
                    totalGears += curr.toInt() * gears[it]!!
                } else gears[it] = curr.toInt()
            }
        }
        println(total)
        println(totalGears)
    }
}
