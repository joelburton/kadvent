import java.io.File

data class Row(val chars: String) {
    fun isTree(x: Int) = chars[x % chars.length] == '#'
}

fun List<Row>.countTree(right: Int) : Long {
    var x = 0
    return count { it.isTree(x).also { x += right } }.toLong()
}

fun main() {
    val rows = File("3.txt").readLines().map { Row(it) }

    val a = rows.countTree(1)
    val b = rows.countTree(3)
    val c = rows.countTree(5)
    val d = rows.countTree(7)
    val e = rows.slice(0..rows.lastIndex step 2).countTree(1)

    println("$a $b $c $d $e")
    println(a * b * c * d * e)
}