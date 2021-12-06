object Day06 : Day(6) {
    override fun main() {
        val fish = input[0]
            .split(",")
            .toInt()
            .groupingBy { it }
            .eachCount()
            .map { (k, v) -> k to v.toLong() }
            .toMap() // this is needed
            .toMutableMap()

        for (i in 1..256) {
            for (j in -1..8) fish[j] = fish[j + 1] ?: 0L
            fish[6] = (fish[6] ?: 0) + (fish[-1] ?: 0)
            fish[8] = (fish[8] ?: 0) + (fish[-1] ?: 0)
            if (i == 80 || i == 256) println(fish.map { (k, v) -> if (k == -1) 0 else v }.sum())
        }
    }
}