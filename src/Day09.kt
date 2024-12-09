object Day09 : Day(9) {
    override fun main() {
        val diskB = mutableListOf<DataB>()

        input.single().forEachIndexed { i, x ->
            val count = x.toString().toInt()
            val id =
                if (i % 2 == 0) {
                    i / 2
                } else {
                    -1
                }

            diskB.add(DataB(id, count))
        }
        val disk = convert(diskB).toMutableList()

        var next = 0
        while (next in disk.indices) {
            while (next in disk.indices && disk[next] != -1) {
                next++
            }
            if (next !in disk.indices) {
                break
            }
            val move = disk.removeLast()
            if (move == -1) {
                continue
            }

            disk[next] = move
        }

        println(checksum(disk))

        for (i in input.single().length / 2 downTo 0) {
            val move = diskB.indexOfFirst { it.id == i }
            var free = -1
            for (j in diskB.indices) {
                if (diskB[j].id == -1 && diskB[j].size >= diskB[move].size) {
                    free = j
                    break // goto anyone?
                }
                if (j > move) {
                    break
                }
            }
            if (free == -1 || free > move) {
                continue
            }
            if (diskB[free].size == diskB[move].size) {
                diskB[free] = DataB(diskB[move].id, diskB[free].size)
                diskB[move] = DataB(-1, diskB[free].size)
                continue
            }
            diskB.add(free + 1, DataB(-1, diskB[free].size - diskB[move].size))
            diskB[free] = diskB[move + 1]
            diskB[move + 1] = DataB(-1, diskB[move + 1].size)
        }

        println(checksum(convert(diskB)))
        println(6398096697992)
    }

    data class Data(val id: Int)
    data class DataB(val id: Int, val size: Int)

    private fun convert(disk: List<DataB>): List<Int> {
        return disk.flatMap { file -> (0 until file.size).map { file.id } }
    }

    private fun checksum(disk: List<Int>): Long {
        return disk.withIndex().sumOf { (i, x) ->
            if (x == -1) {
                0
            } else {
                i * x.toLong()
            }
        }
    }
}