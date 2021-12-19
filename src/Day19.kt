import java.util.*

object Day19 : Day(19) {
    override fun main() {
        val scanners = input.joinToString("\n").split("\n\n").map { line ->
            var permutations = setOf(
                Scanner(
                    line.split(" ")[2].toInt(),
                    line.split("\n").drop(1).map { it.split(",").map { n -> n.toInt() } }.toSet()
                )
            )
            while (permutations.size != 24) {
                val copy = permutations.toMutableSet()
                for (p in permutations) {
                    copy += Scanner(p.id, p.mines.map { (x, y, z) -> listOf(x, z, -y) }.toSet())
                    copy += Scanner(p.id, p.mines.map { (x, y, z) -> listOf(-z, y, x) }.toSet())
                    copy += Scanner(p.id, p.mines.map { (x, y, z) -> listOf(y, -x, z) }.toSet())
                }
                permutations = copy
            }
            permutations
        }.flatten()
        val done = mutableSetOf(0)
        val knownMines = scanners[0].mines.toMutableSet()
        val locations = mutableSetOf<List<Int>>()
        while (done.size != scanners.size / 24)
            outer@ for (scanner in scanners) {
                if (scanner.id in done) continue
                for ((x1, y1, z1) in knownMines) {
                    for ((x2, y2, z2) in scanner.mines) {
                        val dx = x2 - x1
                        val dy = y2 - y1
                        val dz = z2 - z1
                        val new = scanner.transpose(dx, dy, dz)
                        if ((new.mines intersect knownMines).size >= 12) {
                            knownMines += new.mines
                            done += new.id
                            locations += listOf(dx, dy, dz)
                            continue@outer
                        }
                    }
                }
            }
        println(knownMines.size)
        println(locations.maxOf { first -> locations.maxOf { second -> manhattan(first, second) } })
    }

    class Scanner(val id: Int, val mines: Set<List<Int>>) {
        fun transpose(dx: Int, dy: Int, dz: Int) =
            Scanner(id, mines.map { (x, y, z) -> listOf(x - dx, y - dy, z - dz) }.toSet())

        override fun toString() = "Scanner$id[$mines]"

        override fun hashCode() = Objects.hash(id, mines.hashCode())
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Scanner

            if (id != other.id) return false
            if (mines != other.mines) return false

            return true
        }
    }
}