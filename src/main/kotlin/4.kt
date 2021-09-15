import java.io.File

data class Passport(val fields: Map<String, String>) {
    companion object {
        val required = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

        fun fromString(s: String) = s
            .split(Regex("\\s+"))
            .associate { it.split(":").let { (k, v) -> Pair(k, v) } }
            .let { Passport(it) }
    }

    fun isValid() = fields.keys.containsAll(required)

    private fun byr(d: String) = d.toIntOrNull() in 1920..2002
    private fun iyr(d: String) = d.toIntOrNull() in 2010..2020
    private fun eyr(d: String) = d.toIntOrNull() in 2020..2030
    private fun hgt(d: String) = when {
        d.endsWith("cm") -> d.removeSuffix("cm").toIntOrNull() in 150..193
        d.endsWith("in") -> d.removeSuffix("in").toIntOrNull() in 59..76
        else -> false
    }
    private fun hcl(d: String) = Regex("#[0-9a-f]{6}").matches(d)
    private fun ecl(d: String) = d in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    private fun pid(d:String) = Regex("\\d{9}").matches(d)

    fun isValid2() =
        fields["pid"]?.let { pid(it) } == true &&
        fields["ecl"]?.let { ecl(it) } == true &&
        fields["hcl"]?.let { hcl(it) } == true &&
        fields["byr"]?.let { byr(it) } == true &&
        fields["iyr"]?.let { iyr(it) } == true &&
        fields["eyr"]?.let { eyr(it) } == true &&
        fields["hgt"]?.let { hgt(it) } == true
}

fun main() {
    val passports = File("4.txt")
        .readText()
        .trim()
        .split("\n\n")
        .also { println(it) }

    println(passports.count { Passport.fromString(it).isValid() })
    println(passports.count { Passport.fromString(it).isValid2() })
}