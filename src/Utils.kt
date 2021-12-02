fun List<String>.toInt() = map { it.toInt() }
fun String.toInstruction() = run { val (ins, arg) = split(' '); ins to arg.toInt() }
infix fun <A> Pair<A, A>.to(that: A) = Triple(first, second, that)