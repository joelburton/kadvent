import java.io.File

fun main() {
    val adapts = File("10.txt").readLines().map { it.toInt() }.sorted()

    adapts
        .asSequence().zipWithNext()
        .map { it.second - it.first }.groupingBy { it }.eachCount()
        .let { ((it[1] ?: 1) + 1) * ((it[3] ?: 1) + 1) }
        .apply(::println)

    val run: MutableMap<Int, Long> = mutableMapOf(0 to 1L)

    adapts.forEach { a -> run[a] = (1..3).sumOf { run[a - it] ?: 0 } }

    println(run[adapts.last()])
}
