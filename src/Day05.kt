object Day05 : Day(5) {
    override fun main() {
        val steps = input.joinToString("\n").split("\n\n")
        val seeds = steps.first().split(" ").drop(1).toLong()
        val allMappings = steps.drop(1).map { line ->
            line.split("\n").drop(1).map {
                val (dest, source, len) = it.split(" ").toLong()
                source until (source + len) to dest
            }.sortedBy { it.first.first }
        }

        fun transform(seed: Long): Long {
            var out = seed
            allMappings.forEach { mappings ->
                val new = mappings.firstOrNull { out in it.first }
                if (new != null) out = new.second + out - new.first.first
            }
            return out
        }

        println(seeds.minOf { transform(it) })

        println(seeds.chunked(2).parallelStream().mapToLong { (a, b) -> (a until a + b).minOf { transform(it) } }.min().asLong)
    }
}
