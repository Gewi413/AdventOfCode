object Day01 : Day(1) {
    override fun main() {
        println(input.sumBy {
            val nums = it.filter { n -> n.isDigit() }
            (nums.first() - '0') * 10 + nums.last().toInt() - 0x30
        })

        val numstrings = "\\d|one|two|three|four|five|six|seven|eight|nine"
        val words = numstrings.split("|")
        val reg = "^.*?($numstrings).*($numstrings).*?$|($numstrings)".toRegex()
        println(input.sumBy {
            val groups = reg.find(it)?.groups?.drop(1).orEmpty().mapNotNull { num -> num?.value }
            val nums = groups.map { num ->
                if (num in words) {
                    words.indexOf(num).toChar() + '0'.toInt()
                } else
                    num.single()
            }
            (nums.first() - '0') * 10 + nums.last().toInt() - 0x30
        })
    }
}