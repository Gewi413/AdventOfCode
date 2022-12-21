object Day19 : Day(19) {
    override fun main() {
        val startStates = input.mapIndexed { i, line ->
            val (oreOre, clayOre, obsidianOre, obsidianClay, geodeClay, geodeObsidian) = line.split(" ")
                .mapNotNull { it.toIntOrNull() }
            State(blueprint = Blueprint(i + 1, oreOre, clayOre, obsidianOre, obsidianClay, geodeClay, geodeObsidian))
        }
        /*var sum = 0
        for (state in startStates) {
            sum += state.process(24).geodes * state.blueprint.id
            State.cache.clear()
            println(state.blueprint.id)
        }
        println(sum)*/
        var product = 11 * 13
        product *= startStates[2].process(32).geodes
        println(product) // 11 * 13
    }

    private operator fun <E> List<E>.component6(): E = get(5)
    private operator fun <E> List<E>.component7(): E = get(6)

    var done = 0

    data class State(
        val time: Int = 0,
        val oreBots: Int = 1, val ore: Int = 0,
        val clayBots: Int = 0, val clay: Int = 0,
        val obsidianBots: Int = 0, val obsidian: Int = 0,
        val geodeBots: Int = 0, val geodes: Int = 0, val blueprint: Blueprint,
    ) {
        companion object {
            val cache = mutableMapOf<State, State>()
            var max = 0
        }

        fun process(length: Int): State {
            if (this in cache) return cache[this]!!
            val processed = copy(
                time = time + 1,
                ore = ore + oreBots,
                clay = clay + clayBots,
                obsidian = obsidian + obsidianBots,
                geodes = geodes + geodeBots
            )
            val possibilities = mutableListOf(processed)
            if (oreBots < blueprint.maxOre && ore >= blueprint.oreOre) possibilities +=
                processed.copy(ore = processed.ore - blueprint.oreOre, oreBots = oreBots + 1)
            if (clayBots < blueprint.obsidianClay && ore >= blueprint.clayOre) possibilities +=
                processed.copy(ore = processed.ore - blueprint.clayOre, clayBots = clayBots + 1)
            if (obsidianBots < blueprint.geodeObsidian && ore >= blueprint.obsidianOre && clay >= blueprint.obsidianClay)
                possibilities += processed.copy(
                    ore = processed.ore - blueprint.obsidianOre,
                    clay = processed.clay - blueprint.obsidianClay,
                    obsidianBots = obsidianBots + 1
                )
            if (ore >= blueprint.geodeOre && obsidian >= blueprint.geodeObsidian) {
                if (possibilities.size == 4 ||
                    (oreBots < blueprint.maxOre && clayBots < blueprint.obsidianClay && obsidianBots < blueprint.geodeObsidian)
                ) possibilities.removeFirst()
                possibilities += processed.copy(
                    ore = processed.ore - blueprint.geodeOre,
                    obsidian = processed.obsidian - blueprint.geodeObsidian,
                    geodeBots = geodeBots + 1
                )
            }
            done++
            if (done % 10000000 == 0) {
                println("processed $done")
                //cache.clear()
            }
            val out =
                if (time == length - 1) possibilities.maxByOrNull { it.geodes }!!
                else possibilities.reversed().map { it.process(length) }.maxByOrNull { it.geodes }!!
            cache[this] = out
            if(out.geodes > max) {
                max = out.geodes
                println(max)
            }
            return out
        }
    }

    data class Blueprint(
        val id: Int,
        val oreOre: Int,
        val clayOre: Int,
        val obsidianOre: Int, val obsidianClay: Int,
        val geodeOre: Int, val geodeObsidian: Int,
    ) {
        val maxOre = listOf(oreOre, clayOre, obsidianOre, geodeOre).maxOrNull()!! //love this deprecation
    }
}