import kotlin.math.abs

object Day15 : Day(15) {
    override fun main() {
        val targetY = 2000000
        val beacons = mutableSetOf<Int>()
        val conditions = mutableListOf(
            "from z3 import *", // skateTACO
            "x = Int('x')",
            "y = Int('y')",
            "eq = [x >= 0, x <= 4000000, y >= 0, y <= 4000000]",
            "abs = lambda x: If(x >= 0, x, -x)",
        )
        val pos = input.flatMap { line ->
            val (_, sensX, sensY, _, beaconX, beaconY) = line.split("x=", ", y=", ":").map { it.toIntOrNull() }
            val disX = abs(sensX!! - beaconX!!)
            val disY = abs(sensY!! - beaconY!!)
            val diffY = abs(sensY - targetY)
            val scanRadius = disX + disY
            val range = scanRadius - diffY
            if (beaconY == targetY) beacons += beaconX
            conditions += "eq.append(abs(x - $sensX) + abs(y - $sensY) > ${disX + disY})"
            sensX - range..sensX + range
        }.toSet()
        println((pos - beacons).size)

        conditions += "solve(eq)"
        val proc = ProcessBuilder("python", "-c", conditions.joinToString("\n"))
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()
        val (_,x,_,y,_) = proc.inputStream.bufferedReader().readText().split(" = ", ", ", "]").map { it.toLongOrNull() }
        println(x!! * 4000000 + y!!)
    }

    private operator fun <T> List<T>.component6(): T = get(5)
}

