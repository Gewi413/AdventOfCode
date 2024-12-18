object Day17 : Day(17) {
    override fun main() {
        val regs = input.first().split(": ").last().toLong()
        val intcode = input.last().split(": ").last().split(",").toInt()
        val out = execute(intcode, regs)
        println(out.joinToString(","))

    }

    private fun execute(intcode: List<Int>, initialA: Long): List<Int> {
        var a = initialA
        var b = 0L
        var c = 0L
        var ip = 0
        val out = mutableListOf<Int>()
        while (ip < intcode.size - 1) {
            val ins = intcode[ip]
            val op = intcode[ip + 1]
            val combo = when (op) {
                in 0..3 -> op.toLong()
                4 -> a
                5 -> b
                6 -> c
                else -> throw RuntimeException("invalid operand: $op")
            }

            when (ins) {
                0 -> {
                    //println("a shr $combo")
                    a = a shr combo.toInt()
                }

                1 -> {
                    //println("b xor $op")
                    b = b xor op.toLong()
                }

                2 -> {
                    //println("b = *$op % 8")
                    b = combo % 8
                }

                3 -> {
                    //println("jnz")
                    if (a != 0L) {
                        ip = op
                        continue
                    }
                }

                4 -> {
                    //println("b xor c")
                    b = b xor c
                }

                5 -> {
                    //println("sout *$op")
                    out += (combo % 8).toInt()
                }

                6 -> {
                    //println("b = a shr $op")
                    b = a shr combo.toInt()
                }

                7 -> {
                    //println("c = a shr $op")
                    c = a shr combo.toInt()
                }
            }
            ip += 2
        }
        return out
    }
}