import java.io.File

/** For people in group, return union of questions. */
fun peopleQuestionsAny(people: List<String>) =
    people
        .flatMap { it.toList() }
        .distinct()

/** For people in group, return intersection of questions. */
fun peopleQuestionsAll(people: List<String>) =
    people
        .map { person -> person.toList().toSet() }
        .reduce { acc, p -> acc.intersect(p) }

fun main() {
    val groups = File("6.txt")
        .readText()
        .trim()
        .split("\n\n")
        .map { it.split("\n") }

    groups
        .sumOf { peopleQuestionsAny(it).size }
        .also { println(it) }

    groups
        .sumOf { peopleQuestionsAll(it).size }
        .also { println(it) }
}
