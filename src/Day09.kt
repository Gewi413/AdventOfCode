object Day09 : Day(9) {
    override fun main() {
        val disk = mutableListOf<Data>()
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

            repeat(count) {
                disk.add(
                    Data(id)
                )
            }
        }

        while (disk.any { it.id == -1 }) {
            val move = disk.removeLast()
            if (move.id == -1) {
                continue
            }
            disk[disk.indexOfFirst { it.id == -1 }] = move
        }

        println(disk.withIndex().sumOf { (i, x) -> i * x.id.toLong() })


        for (i in input.single().length / 2 downTo 0) {
            val move = diskB.indexOfFirst { it.id == i }
            val free = diskB.indexOfFirst { it.id == -1 && it.size >= diskB[move].size }
            if (free == -1 || free > move) {
                continue
            }

            diskB.add(free + 1, DataB(-1, diskB[free].size - diskB[move].size))
            diskB[free] = diskB[move + 1]
            diskB[move + 1] = DataB(-1, diskB[move + 1].size)
        }

        println(
            diskB
                .flatMap { file -> (0 until file.size).map { Data(file.id) } }
                .withIndex()
                .sumOf { (i, x) ->
                    if (x.id == -1) {
                        0
                    } else {
                        i * x.id.toLong()
                    }
                })
    }

    data class Data(val id: Int)
    data class DataB(val id: Int, val size: Int)
}