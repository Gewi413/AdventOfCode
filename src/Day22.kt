object Day22 : Day(22) {
    override fun main() {
        val prices = input.toLong()
            .map {
                val prices = mutableListOf(it)
                for (i in 1..2000) {
                    prices += iterate(prices.last())
                }
                prices
            }

        println(prices.sumOf { it.last() })
        var best = 0L
        for (i in -9..9L) {
            for (j in -9..9L) {
                for (k in -9..9L) {
                    for (l in -9..9L) { // big O(fuck)
                        if (
                            i + j !in -9..9 ||
                            j + k !in -9..9 ||
                            k + l !in -9..9 ||
                            i + j + k !in -9..9 ||
                            j + k + l !in -9..9 ||
                            i + j + k + l !in -9..9
                                ) {
                            continue
                        }
                        var res = 0L
                        for (monkey in prices) {
                            for (m in 4 until monkey.size) {
                                val a = monkey[m - 3] % 10 - monkey[m - 4] % 10
                                val b = monkey[m - 2] % 10 - monkey[m - 3] % 10
                                val c = monkey[m - 1] % 10 - monkey[m - 2] % 10
                                val d = monkey[m - 0] % 10 - monkey[m - 1] % 10
                                if (a == i && b == j && c == k && d == l) {
                                    res += monkey[m] % 10
                                    break
                                }
                            }
                        }
                        if (res > best) {
                            println("currently best: $i, $j, $k, $l with $res")
                            best = res
                        }
                    }
                }
            }
        }
        println(best)
    }

    private fun mixAndShuf(secret: Long, bits: Int): Long =
        (secret xor (if (bits > 0) secret shl bits else secret shr -bits)) % 16777216

    private fun iterate(secret: Long): Long {
        var temp = secret
        temp = mixAndShuf(temp, 6)
        temp = mixAndShuf(temp, -5)
        return mixAndShuf(temp, 11)
    }
}
