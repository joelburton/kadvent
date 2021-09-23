import java.io.File

const val len: Int = 25

private fun List<Long>.valid(sought: Long = this.last()) =
    this.dropLast(1).toSet().run { this.any { sought - it in this } }

fun main() {
    val ints = File("9.txt")
        .readLines()
        .map { it.toLong() }

    val invalid = ints.windowed(len + 1).first { !it.valid() }.last()
    println(invalid)

    var i = 0
    var j = 0
    var sum = ints[i]

    while (sum != invalid) {
        if (sum < invalid) sum += ints[++j]
        else sum = ints[i + 1].also { j = ++i }
    }
    with(ints.slice(i..j)) {
        println(this.maxOrNull()!! + this.minOrNull()!!)
    }
}
