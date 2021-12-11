fun List<String>.toInt(base: Int = 10) = map { it.toInt(base) }
fun String.toInstruction() = run { val (ins, arg) = split(' '); ins to arg.toInt() }
infix fun <A> Pair<A, A>.to(that: A) = Triple(first, second, that)
typealias Point = Pair<Int, Int>
fun parseToIntMap(input: List<String>): Map<Point, Int> {
    val map = mutableMapOf<Point, Int>()
    for ((y, line) in input.withIndex()) for ((x, char) in line.withIndex())
        map[x to y] = char - '0'
    return map
}