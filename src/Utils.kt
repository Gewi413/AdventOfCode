import kotlin.math.abs

fun List<String>.toInt(base: Int = 10) = map { it.toInt(base) }
fun List<String>.toPoint() = map { val (a, b) = it.split(",").map { c -> c.toInt() }; a to b }
fun List<String>.toInstruction() = map { it.toInstruction() }
fun <T> List<T>.eachCount() = groupingBy { it }.eachCount()

fun String.toInstruction(delim: Char = ' ') = run { val (ins, arg) = split(delim); ins to arg.toInt() }

fun List<Boolean>.toInt() = mapIndexed { i, v -> if (v) 1 shl (size - 1 - i) else 0 }.sum()
fun <T : Comparable<T>> Iterable<T>.minMax() = minOrNull()!! to maxOrNull()!!

infix fun <A> Pair<A, A>.to(that: A) = Triple(first, second, that)

typealias Point = Pair<Int, Int>

fun Point.neighbors() = let { val (x, y) = this; listOf(x + 1 to y, x - 1 to y, x to y + 1, x to y - 1) }

fun manhattan(p1: List<Int>, p2: List<Int>) = p1.zip(p2).sumBy { (a, b) -> abs(a - b) }

fun List<String>.toIntMap(): Map<Point, Int> {
    val map = mutableMapOf<Point, Int>()
    for ((y, line) in withIndex()) for ((x, char) in line.withIndex())
        map[x to y] = char - '0'
    return map
}

fun Map<Point, Int>.print(markings: Set<Point> = setOf()) {
    val (minX, maxX) = keys.map { it.first }.minMax()
    val (minY, maxY) = keys.map { it.second }.minMax()
    for (y in minY..maxY) {
        for (x in minX..maxX) {
            val c = this[x to y] ?: 0
            if (x to y in markings) print("\u001b[32m$c\u001b[0m")
            else print("$c")
        }
        println()
    }
}

fun <T> doUntilSettled(initial: T, function: (T) -> T): T {
    var old: T? = null
    var result = function(initial)
    while (result != old) {
        old = result
        result = function(old)
    }
    return old!!
}
