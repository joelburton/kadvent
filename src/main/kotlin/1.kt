import java.io.File

fun Set<Int>.findPairOfSum(sum: Int): Pair<Int, Int>? {
    val complements = associateBy { sum - it }
    return firstNotNullOfOrNull { n -> complements[n]?.let { Pair(n, it) } }
}

fun Set<Int>.findTripleOfSum(sum: Int): Triple<Int, Int, Int>? =
    firstNotNullOfOrNull { n ->
        findPairOfSum(sum - n)?.let { Triple(n, it.first, it.second) }
    }

fun main() {
    val nums = File("1.txt").readLines().map { it.toInt() }.toSet()
    val sum = 2020

    // part 1
    val pair = nums.findPairOfSum(sum)
    println(pair?.let { it.first * it.second })

    // part 2
    val triple = nums.findTripleOfSum(sum)
    println(triple?.let { it.first * it.second * it.third })
}
