object Day19 : Day(19) {
    override fun main() {
        val startStates = input.mapIndexed { i, line ->
            val (oreOre, clayOre, obsidianOre, obsidianClay, geodeClay, geodeObsidian) = line.split(" ")
                .mapNotNull { it.toIntOrNull() }
            State(Blueprint(i + 1, oreOre, clayOre, obsidianOre, obsidianClay, geodeClay, geodeObsidian))
        }
        println(startStates.map { it.process() })
        println(State.thing)
    }

    private operator fun <E> List<E>.component6(): E = get(5)
    private operator fun <E> List<E>.component7(): E = get(6)

    var done = 0

    data class State(
        val blueprint: Blueprint, val time: Int = 0,
        val oreBots: Int = 1, val ore: Int = 0,
        val clayBots: Int = 0, val clay: Int = 0,
        val obsidianBots: Int = 0, val obsidian: Int = 0,
        val geodeBots: Int = 0, val geodes: Int = 0,
    ) {
        companion object {
            private val cache = mutableMapOf<State, State>()
            val thing = MutableList(24) { 0 }
        }

        fun process(): State {
            thing[time]++
            if (this in cache) return cache[this]!!
            val processed = copy(
                time = time + 1,
                ore = ore + oreBots,
                clay = clay + clayBots,
                obsidian = obsidian + obsidianBots,
                geodes = geodes + geodeBots
            )
            val possibilities = mutableListOf(processed)
            if (oreBots < blueprint.maxOre && ore > blueprint.oreOre) possibilities +=
                processed.copy(ore = processed.ore - blueprint.oreOre, oreBots = oreBots + 1)
            if (clayBots < blueprint.obsidianClay && ore > blueprint.clayOre) possibilities +=
                processed.copy(ore = processed.ore - blueprint.clayOre, clayBots = clayBots + 1)
            if (obsidianBots < blueprint.geodeObsidian && ore > blueprint.obsidianOre && clay > blueprint.obsidianClay)
                possibilities += processed.copy(
                    ore = processed.ore - blueprint.obsidianOre,
                    clay = processed.clay - blueprint.obsidianClay,
                    obsidianBots = obsidianBots + 1
                )
            if (ore > blueprint.geodeOre && obsidian > blueprint.geodeObsidian)
                possibilities.clear()
                possibilities += processed.copy(
                    ore = processed.ore - blueprint.geodeOre,
                    obsidian = processed.obsidian - blueprint.geodeObsidian,
                    geodeBots = geodeBots + 1
                )
            if (time < 5) println("processing with $time (${possibilities.size} paths)")
            done++
            if (done % 10000000 == 0) println("processed $done")
            val out =
                if (time == 23) possibilities.maxByOrNull { it.geodes }!!
                else possibilities.map { it.process() }.maxByOrNull { it.geodes }!!
            cache[this] = out
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