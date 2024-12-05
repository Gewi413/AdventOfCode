object Day05 : Day(5) {
    override fun main() {
        val (order, pages) = input.joinToString("\n").split("\n\n")
        val rules = order.split("\n").map {
            val (a, b) = it.split("|").toInt()
            a to b
        }

        val all = pages
            .split("\n")
            .map { it.split(",").toInt() }
        val valids = all
            .filter { line ->
                line.withIndex().all { (i, prev) ->
                    line.drop(i + 1).all { next ->
                        next to prev !in rules
                    }
                }
            }

        println(valids.sumOf { it[it.size / 2] })

        println(
            all
                .filter { it !in valids }
                .map {
                    it.sortedWith(Comparator { a, b ->
                        when {
                            a to b in rules -> -1
                            b to a in rules -> 1
                            else -> 0
                        }
                    })
                }
                .sumOf { it[it.size / 2] }
        )
    }
}