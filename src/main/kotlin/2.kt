import java.io.File

val LINE_REGEX = Regex("""(\d+)-(\d+) (.): (.*)""")

data class Entry(val a: Int, val b: Int, val letter: Char, val pwd: String) {
    companion object {
        fun parse(line: String): Entry =
            LINE_REGEX.matchEntire(line)!!
                .destructured
                .let { (a, b, letter, pwd) -> Entry(a.toInt(), b.toInt(), letter.single(), pwd) }
    }

    fun validatePart1() = pwd.count { it == letter } in a..b

    fun validatePart2() = (pwd[a - 1] == letter) xor (pwd[b - 1] == letter)
}

fun main() {
    val passwords = File("2.txt").readLines().map(Entry::parse)
    println(passwords.count { it.validatePart1() })
    println(passwords.count { it.validatePart2() })
}