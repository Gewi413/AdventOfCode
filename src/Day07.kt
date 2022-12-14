object Day07 : Day(7) {
    override fun main() {
        var path = mutableListOf(File("Root"))
        input
            .joinToString("\n")
            .split("$ ")
            .drop(1)
            .forEach { command ->
                val args = command.trim().split("\n", " ")
                when (args[0]) {
                    "cd" -> when (args[1]) {
                        "/" -> path = path.take(1).toMutableList()
                        ".." -> path.removeLast()
                        else -> path += path.last().children.first { it.name == args[1] }
                    }

                    "ls" -> {
                        args.drop(1).chunked(2).forEach { (size, name) ->
                            val currfile = path.last()
                            if (size == "dir") currfile.children.add(File(name))
                            else currfile.children.add(File(name, size.toInt()))
                        }
                    }
                }
            }
        println(path.first().countA())
        val needed = path.first().size - 40000000
        println(path.first().allFiles().filter { it.size > needed }.minOf { it.size })
    }

    data class File(val name: String, val content: Int = 0, val children: MutableList<File> = mutableListOf()) {
        val size: Int
            get() = children.sumBy { it.size } + content

        fun countA(): Int = (if (content != 0 || size > 100000) 0 else size) + children.sumBy { it.countA() }

        fun allFiles(): List<File> = children.flatMap { it.allFiles() } + this
    }
}