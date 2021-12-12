object Day12 : Day(12) {
    override fun main() {
        val nodes = mutableMapOf<String, MutableList<String>>()
        input.forEach {
            val (a, b) = it.split("-")
            nodes.getOrPut(a, ::mutableListOf) += b
            nodes.getOrPut(b, ::mutableListOf) += a
        }
        println(explore(nodes, "start"))
        println(explore(nodes, "start", true))
    }

    private fun explore(
        map: Map<String, List<String>>, node: String, allowDouble: Boolean = false, already: Set<String> = setOf()
    ): Int = if (node == "end") 1 else map[node]!!.mapNotNull {
        if (it == "start" || (it in already && it[0].isLowerCase() && !allowDouble)) null
        else explore(map, it, allowDouble && (it[0].isUpperCase() || it !in already), already + node)
    }.sum()
}