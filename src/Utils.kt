fun List<String>.toInt(base: Int = 10) = map { it.toInt(base) }
fun List<String>.toPoint() = map { val (a, b) = it.split(",").map { c -> c.toInt() }; a to b }
fun List<String>.toInstruction() = map { it.toInstruction() }
fun<T> List<T>.eachCount() = groupingBy { it }.eachCount()

fun String.toInstruction(delim: Char = ' ') = run { val (ins, arg) = split(delim); ins to arg.toInt() }

fun List<Boolean>.toInt() = mapIndexed { i, v -> if (v) 1 shl (size - 1 - i) else 0 }.sum()

infix fun <A> Pair<A, A>.to(that: A) = Triple(first, second, that)

typealias Point = Pair<Int, Int>

fun Point.neighbors() = let { val (x, y) = this; listOf(x + 1 to y, x - 1 to y, x to y + 1, x to y - 1) }

fun List<String>.toIntMap(): Map<Point, Int> {
    val map = mutableMapOf<Point, Int>()
    for ((y, line) in withIndex()) for ((x, char) in line.withIndex())
        map[x to y] = char - '0'
    return map
}

fun Map<Point,Int>.print(markings: List<Point> = listOf()) {
    val width = maxOf { it.key.first } + 1
    val height = maxOf { it.key.second } + 1
    for (y in 0..width) {
        for (x in 0..height) {
            val c = this[x to y]
            if (x to y in markings) print("\u001b[32m$c\u001b[0m")
            else print("$c")
        }
        println()
    }
}
