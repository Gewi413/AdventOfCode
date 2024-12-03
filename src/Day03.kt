object Day03 : Day(3) {
    override fun main() {
        // match groups start at 1 - 0 is full match
        val matches = Regex("mul\\((\\d+),(\\d+)\\)|(do(n't)?\\(\\))").findAll(input.joinToString("\n"))
        var countA = 0L
        var countB = 0L
        var enabled = true
        for (match in matches) {
            if (match.groups[3] != null) {
                enabled = match.groups[4] == null
                continue
            }
            println(match.groups)
            val product = match.groups[1]!!.value.toInt() * match.groups[2]!!.value.toInt() // trust me bro
            countA += product
            if (enabled) {
                countB += product
            }
        }
        println(countA)
        println(countB)
    }
}