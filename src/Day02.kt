object Day02 : Day(2) {
    override fun main() {
        val games = input.map {
            val parts = it.split(": ", "; ")
            val id = parts[0].split(" ").last().toInt()
            id to parts.drop(1).map { game ->
                game.split(", ").associate { cubes ->
                    val (num, col) = cubes.split(" ")
                    col to num.toInt()
                }
            }
        }
        println(games.sumBy { (id, games) ->
            if (games.all { game ->
                    game.getOrElse("red") { 0 } <= 12 &&
                            game.getOrElse("green") { 0 } <= 13 &&
                            game.getOrElse("blue") { 0 } <= 14
                }) id else 0
        })

        val colors = listOf("red", "green", "blue")
        println(games.map { it.second }.sumBy { game ->
            colors.map { color -> game.maxOf { subgame -> subgame.getOrElse(color) { 1 } } }
                .fold(1) { acc, i -> acc * i }
        })
    }
}