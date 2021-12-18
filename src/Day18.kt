object Day18 : Day(18) {
    override fun main() {
        var out: SnailNumber = create(input[0])
        for (line in input.drop(1)) {
            out += create(line)
            out = doUntilSettled(out) { state ->
                doUntilSettled(state) { it.explode(0).first }.split()
            }
        }
        println(out.value)
    }
}

private fun create(input: String) = if (input[0] == '[') PairNumber(input) else LiteralNumber(input)

sealed class SnailNumber {
    operator fun plus(other: SnailNumber) = PairNumber(this, other)

    abstract fun split(): SnailNumber
    abstract fun explode(depth: Int): Pair<SnailNumber, Pair<Int, Int>>

    abstract val value: Int
}

class LiteralNumber(input: String) : SnailNumber() {
    override fun split() =
        if (value > 9) create("[${value / 2},${value - value / 2}]") else this

    override fun explode(depth: Int) = this to (0 to 0)
    fun tryAdd(number: Int): SnailNumber = if (number == 0) this else create("${value + number}")

    override val value = input.toInt()
    override fun toString() = "$value"
}

class PairNumber() : SnailNumber() {
    override fun split(): SnailNumber {
        val new = lChild.split()
        return if (new == lChild) {
            val newR = rChild.split()
            if (newR == rChild) this else {
                rChild = newR
                create(this.toString())
            }
        } else {
            lChild = new
            create(this.toString()) //performance is my passion
        }
    }

    override fun explode(depth: Int): Pair<SnailNumber, Pair<Int, Int>> {
        if (depth >= 4) return create("0") to (lChild.value to rChild.value)

        val (first, overflow) = lChild.explode(depth + 1)
        return if (first != lChild) {
            lChild = first
            rChild = when (rChild) {
                is LiteralNumber -> (rChild as LiteralNumber).tryAdd(overflow.second)
                is PairNumber -> (rChild as PairNumber).tryAdd(overflow.second, false)
            }
            create(this.toString()) to (overflow.first to 0)
        } else {
            val (other, otherOverflow) = rChild.explode(depth + 1)
            if (other != rChild) {
                rChild = other
                lChild = when (lChild) {
                    is LiteralNumber -> (lChild as LiteralNumber).tryAdd(otherOverflow.first)
                    is PairNumber -> (lChild as PairNumber).tryAdd(otherOverflow.first, true)
                }
                create(this.toString()) to (0 to otherOverflow.second)
            } else this to (0 to 0)
        }
    }

    private fun tryAdd(number: Int, side: Boolean): SnailNumber {
        if (side) rChild = when (rChild) {
            is LiteralNumber -> (rChild as LiteralNumber).tryAdd(number)
            is PairNumber -> (rChild as PairNumber).tryAdd(number, side)
        }
        else lChild = when (lChild) {
            is LiteralNumber -> (lChild as LiteralNumber).tryAdd(number)
            is PairNumber -> (lChild as PairNumber).tryAdd(number, side)
        }
        return this
    }

    private lateinit var lChild: SnailNumber
    private lateinit var rChild: SnailNumber
    override val value get() = lChild.value * 3 + rChild.value * 2

    constructor(first: SnailNumber, second: SnailNumber) : this() {
        lChild = first
        rChild = second
    }

    constructor(input: String) : this() {
        var counter = 0
        lChild = create(input.drop(1).takeWhile {
            if (it == '[') counter++ else if (it == ']') counter--
            !(counter == 0 && it == ',')
        })
        rChild = create(input.dropLast(1).takeLastWhile {
            if (it == '[') counter++ else if (it == ']') counter--
            !(counter == 0 && it == ',')
        })
    }

    override fun toString() = "[$lChild,$rChild]"
}