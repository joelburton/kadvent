import java.io.File

val ROW_RE = Regex("""(.+?) bags contain (.+)\.""")
val SPEC_RE = Regex(""" ?(\d+) (.+?) bags?""")

data class BagRule(val parent: String, val cost: Int, val child: String)

fun main() {
//    val rules: MutableMap<String, MutableSet<String>> = mutableMapOf()
    val rules = File("7a.txt")
        .readLines()
        .filterNot { it.contains(" no other ") }
        .flatMap { row ->
            val (color, specs) = ROW_RE.matchEntire(row)!!.destructured
            specs.split(",").map { spec ->
                val (count, other) = SPEC_RE.matchEntire(spec)!!.destructured
                BagRule(color, count.toInt(), other)
            }
        }

    println(rules)

//    val seen = mutableSetOf<String>()
//    var curr = rules["shiny gold"]!!
//    val toVisit: MutableList<String> = curr.toMutableList()
//    var count = 0
//    while (toVisit.isNotEmpty()) {
//        for (child in curr) {
//            if (child in seen) continue
//            count += 1
//            seen.add(child)
//            println(child)
//            toVisit.add(child)
//        }
//        val last = toVisit.removeLast()
//        println(last)
//        curr = rules.getOrDefault(last, mutableSetOf())
//    }
//    println(count)

//    fun recur(
//        color: String
//    ): Int? {
//        if (color == "shiny gold") return 0
//        val children = rules[color]!!
//        if (children.isEmpty()) return null
//
//        return (1 + rules[color]!!.sumOf { (cc, _) -> recur(rules, cc) })
//    }

//    fun recur2(
//        color: String
//    ): Int {
//        return (1 + rules[color]!!.sumOf { (cc, count) -> count * recur2(rules, cc) })
//    }
//
//    println(recur2( "shiny gold") - 1)
}
//
//fun recur(
//    rules: MutableMap<String, MutableSet<Pair<String, Int>>>,
//    color: String
//): Int {
//    return (1 + rules.getOrDefault(color, mutableSetOf())
//        .sumOf { (col, count) -> count * recur(rules, col) })
//}
//
