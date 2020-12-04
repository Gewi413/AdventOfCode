import java.io.File

fun main() {
    val input = File("challenges/day4").readText()
        .replace("\n\n", ";").replace("\n", " ").replace(";", "\n")
        .split("\n")
    val fields = listOf("byr:", "iyr:", "eyr:", "hgt:", "hcl:", "ecl:", "pid:")
    val filtered = input.filter { line -> fields.all { it in line } }
    println(filtered.size)

    var valid = 0
    pass@for (pass in filtered)  {
        for (field in pass.split(" ")) {
            val (key, value) = field.split(":")
            if (!when (key) {
                    "byr" -> value.toInt() in 1920..2002
                    "iyr" -> value.toInt() in 2010..2020
                    "eyr" -> value.toInt() in 2020..2030
                    "hgt" -> value.endsWith("cm") && value.dropLast(2).toInt() in 150..193 ||
                            value.endsWith("in") && value.dropLast(2).toInt() in 59..76
                    "hcl" -> value[0] == '#' && value.drop(1).toInt(16) <= 16777215
                    "ecl" -> value in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
                    "pid" -> value.toIntOrNull() != null && value.length == 9
                    "cid" -> true
                    else -> false
                }
            ) continue@pass
        }
        valid++
    }
    println(valid)
}