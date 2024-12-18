import java.util.*
import kotlin.math.pow

object Day17 : Day(17) {
    override fun main() {
        val regs = input.first().split(": ").last().toLong()
        val intcode = input.last().split(": ").last().split(",").toInt()
        val out = execute(intcode, regs)
        println(out.joinToString(","))

        val rng = Random()
        var curr: Long
        forever@ while (true) { // let's go gambling
            curr = 0
            outer@ while (true) {
                val score = score(intcode, curr)
                if (score == 0.0) {
                    break
                }

                for (tosses in 0..4) {
                    var i = 0
                    while (i++ < (20.0.pow(tosses + 1))) {
                        val flips = (0..tosses).map { rng.nextInt() % 50 }
                        var guess = curr
                        for (flip in flips) {
                            guess = guess xor (1L shl flip)
                        }
                        val newScore = score(intcode, guess)
                        if (newScore < score) {
                            curr = guess
                            continue@outer
                        }
                    }

                }
                continue@forever
            }
            break
        }


        for(i in curr - 1000000..curr) { // idk
            if(score(intcode, i) == 0.0) {
                println("$i")
                break
            }
        }
    }


    private fun score(intcode: List<Int>, initialA: Long): Double {
        val res = execute(intcode, initialA)
        if (res.size != intcode.size) {
            return 42.0
        }
        return intcode.zip(res).fold(0.0) { acc, (a, b) ->
            acc * 0.99 + when (a xor b) {
                1, 2, 4 -> 1
                3, 5, 6 -> 2
                7 -> 3
                else -> 0
            }
        }
    }

    private fun execute(intcode: List<Int>, initialA: Long): List<Int> {
        var a = initialA
        var b = 0L
        var c = 0L
        var ip = 0
        val out = IntArray(intcode.size + 2)
        var curr = 0
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
                    if (curr !in out.indices) {
                        break
                    }
                    out[curr] = (combo % 8).toInt()
                    curr++
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
        return out.take(curr)
    }
}