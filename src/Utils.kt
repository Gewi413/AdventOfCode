fun List<String>.toInt(base: Int = 10) = map { it.toInt(base) }
fun String.toInstruction() = run { val (ins, arg) = split(' '); ins to arg.toInt() }
infix fun <A> Pair<A, A>.to(that: A) = Triple(first, second, that)
typealias Point = Pair<Int, Int>