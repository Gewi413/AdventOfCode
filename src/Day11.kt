object Day11 : Day(11) {
    override fun main() {
        val (monkeys, monkeysB) = input.joinToString("\n").split("\n\n").map { line ->
            val lines = line.split("\n").map { it.trim() }
            val items = lines[1].drop(16).split(", ").map { it.toLong() }
            val (operation, second) = lines[2].drop(21).split(" ")
            val lambda =
                if (second == "old") { num: Long -> num * num } // this is highly cursed because the { doesn't belong to the if
                else when (operation) {
                    "+" -> { num: Long -> num + second.toLong() }
                    else -> { num: Long -> num * second.toLong() }
                }
            val div = lines[3].split(" ").last().toLong()
            val (a, b) = lines.takeLast(2).map { it.last() - '0' }
            Monkey(items.toMutableList(), lambda, div, a to b) to
                    Monkey(items.toMutableList(), lambda, div, a to b)
        }.unzip()

        repeat(20) {
            for (monkey in monkeys) {
                for (item in monkey.items) {
                    monkey.inspections++
                    val new = monkey.worried(item) / 3
                    if (new % monkey.divisor == 0L) monkeys[monkey.next.first].items += new
                    else monkeys[monkey.next.second].items += new
                }
                monkey.items.clear()
            }
        }
        val (a, b) = monkeys.sortedBy { it.inspections }.map { it.inspections.toLong() }.takeLast(2)
        println(a * b)

        val lcm = monkeysB.fold(1L) { acc, i -> acc * i.divisor } // who cares to actually lcm them, this is good enough
        repeat(10000) {
            for (monkey in monkeysB) {
                for (item in monkey.items) {
                    monkey.inspections++
                    val new = monkey.worried(item) % lcm
                    if (new % monkey.divisor == 0L) monkeysB[monkey.next.first].items += new
                    else monkeysB[monkey.next.second].items += new
                }
                monkey.items.clear()
            }
        }
        val (c, d) = monkeysB.sortedBy { it.inspections }.map { it.inspections.toLong() }.takeLast(2)
        println(c * d)
    }

    class Monkey(
        val items: MutableList<Long>,
        val worried: (Long) -> Long,
        val divisor: Long,
        val next: Pair<Int, Int>,
        var inspections: Int = 0
    )
}