object Day11 : Day(11) {
    override fun main() {
        val monkeys = input.joinToString("\n").split("\n\n").map { line ->
            val lines = line.split("\n").map { it.trim() }
            val id = lines[0].split(" ", ":")[1].toInt()
            val items = lines[1].drop(16).split(", ").toInt()
            val (first, operation, second) = lines[2].drop(17).split(" ")
            val lambda = parseExpr(first, operation, second)
            val div = lines[3].split(" ").last().toInt()
            val (a, b) = lines.takeLast(2).map { it.last() - '0' }
            Monkey(id, items.toMutableList(), lambda, div, a to b)
        }

        val lcm = monkeys.fold(1)

        repeat(20) {
            for (monkey in monkeys) {
                for (item in monkey.items) {
                    monkey.inspections++
                    val new = monkey.worried(item) / 3
                    if (new % monkey.divisor == 0) monkeys[monkey.next.first].items += new
                    else monkeys[monkey.next.second].items += new
                }
                monkey.items.clear()
            }
        }
        val (a, b) = monkeys.sortedBy { it.inspections }.map { it.inspections }.takeLast(2)
        println(a * b)
    }
}

class Monkey(
    val id: Int,
    val items: MutableList<Int>,
    val worried: (Int) -> Int,
    val divisor: Int,
    val next: Pair<Int, Int>
) {
    var inspections = 0
}

// it just wouldnt let me do it in the same function
fun parseExpr(first: String, operation: String, second: String): (Int) -> Int =
    if (first == "old")
        if (second == "old") when (operation) {
            "+" -> { num: Int -> num + num }
            "*" -> { num: Int -> num * num }
            else -> { num: Int -> num }
        } else when (operation) {
            "+" -> { num: Int -> num + second.toInt() }
            "*" -> { num: Int -> num * second.toInt() }
            else -> { num: Int -> num }
        }
    else if (second == "old") when (operation) {
        "+" -> { num: Int -> first.toInt() + num }
        "*" -> { num: Int -> first.toInt() * num }
        else -> { num: Int -> num }
    } else when (operation) {
        "+" -> { _: Int -> first.toInt() + second.toInt() }
        "*" -> { _: Int -> second.toInt() * second.toInt() }
        else -> { num: Int -> num }
    }