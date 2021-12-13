fun List<String>.toInt(base: Int = 10) = map { it.toInt(base) }
fun List<String>.toPoint() = map { val (a, b) = it.split(",").map { c -> c.toInt() }; a to b }
fun List<String>.toInstruction() = map { it.toInstruction() }

fun String.toInstruction(delim: Char = ' ') = run { val (ins, arg) = split(delim); ins to arg.toInt() }

infix fun <A> Pair<A, A>.to(that: A) = Triple(first, second, that)

typealias Point = Pair<Int, Int>

fun parseToIntMap(input: List<String>): Map<Point, Int> {
    val map = mutableMapOf<Point, Int>()
    for ((y, line) in input.withIndex()) for ((x, char) in line.withIndex())
        map[x to y] = char - '0'
    return map
}
