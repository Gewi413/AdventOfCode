@file:Suppress("PackageDirectoryMismatch")

package day4

import java.io.File

fun main() {
    val input = File("challenges/day4").readText()
        .replace("\n\n", ";").replace("\n", " ").replace(";", "\n")
        .split("\n")
    val fields = listOf("byr:", "iyr:", "eyr:", "hgt:", "hcl:", "ecl:", "pid:")
    val filtered = input.filter { line -> fields.all { it in line } }
    println(filtered.size)

    var valid = 0
    pass@ for (pass in filtered) {
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

fun regex() {
    val input = File("challenges/day4").readText()
    val patternA = Regex(
        "(?=(.|\\n(?!\\n))*byr:)" +
                "(?=(.|\\n(?!\\n))*iyr:)" +
                "(?=(.|\\n(?!\\n))*eyr:)" +
                "(?=(.|\\n(?!\\n))*hgt:)" +
                "(?=(.|\\n(?!\\n))*hcl:)" +
                "(?=(.|\\n(?!\\n))*ecl:)" +
                "(?=(.|\\n(?!\\n))*pid:)" +
                "(.|\\n(?!\\n))*"
    )
    val patternB = Regex(
        "(?=(.|\\n(?!\\n))*byr:(200[0-2]|19[2-9]\\d))" +
                "(?=(.|\\n(?!\\n))*iyr:(20(20|1\\d)))" +
                "(?=(.|\\n(?!\\n))*eyr:(20(30|2\\d)))" +
                "(?=(.|\\n(?!\\n))*hgt:((59|6\\d|7[0-6])in|(1[5-8]\\d|19[0-3])cm))" +
                "(?=(.|\\n(?!\\n))*hcl:#[a-f0-9]{6})" +
                "(?=(.|\\n(?!\\n))*ecl:(amb|blu|brn|gry|grn|hzl|oth))" +
                "(?=(.|\\n(?!\\n))*pid:\\d{9}\\D)" +
                "(.|\\n(?!\\n))*"
    )
    println(patternA.findAll(input).count())
    println(patternB.findAll(input).count())
}