object Day03 : Day(3) {
    override fun main() {
        val nums = input.toInt(2)
        val length = input[0].length
        println(input[0].indices
            .sumBy { bit -> if (nums.sumBy { it and (1 shl bit) } * 2 > nums.size shl bit) 1 shl bit else 0 }
            .let { it * (it xor (1 shl length) - 1) })

        println(solve(nums, length - 1) * solve(nums, length - 1, true))
    }

    //1082324, 1353024
    private fun solve(nums: List<Int>, index: Int = 0, flip: Boolean = false): Int {
        val keep = (nums.sumBy { it and (1 shl index) } * 2 >= nums.size shl index) xor flip
        return if (nums.size <= 1) nums.first() else solve(
            nums.filter { it and (1 shl index) != 0 == keep },
            index - 1, flip
        )
    }
}