import java.io.File

fun toSeatCode(s: String) = s
    .reversed()
    .map {
        when (it) {
            'B', 'R' -> 1
            'F', 'L' -> 0
            else -> throw Error("Invalid direction")
        }
    }
    .reduceIndexed { idx, accum, v -> accum + (v shl idx) }

fun main() {
    val seats = File("5.txt")
        .readLines()
        .map { toSeatCode(it) }
        .toSet()

    seats
        .maxOrNull()
        .also { println(it) }

    (0..1024)
        .dropWhile { it !in seats }
        .find { it !in seats }
        .also { println(it) }
}