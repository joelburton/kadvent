import java.io.File

typealias Seats = List<List<Char>>
typealias Seat = Pair<Int, Int>

fun Seats.debug() =
    println(this.joinToString("\n") { it.joinToString("") })

fun main() {
    val seats = File("11.txt").readLines().map { it.toList() }
//    seats.debug()

    val deltas = listOf(
        Pair(-1, -1),
        Pair(-1, 0),
        Pair(-1, +1),
        Pair(0, -1),
        Pair(0, +1),
        Pair(+1, -1),
        Pair(+1, 0),
        Pair(+1, +1),
    )

    fun numOccupiedNeighbors(seats: Seats, y: Int, x: Int) =
        deltas
            .map { (dy, dx) -> Pair(y + dy, x + dx) }
            .filter { (y, x) -> y in seats.indices && x in seats[0].indices }
            .count { (y, x) -> seats[y][x] == '#' }

    fun canSee(seats: Seats, ay: Int, ax: Int, dy: Int, dx: Int): Char {
        var y = ay + dy
        var x = ax + dx
        while (y in seats.indices && x in seats[0].indices) {
            if (seats[y][x] != '.') return seats[y][x]
            y += dy
            x += dx
        }
        return '.'
    }

    fun numOccupiedVisible(seats: Seats, y: Int, x: Int) =
        deltas
            .map { (dy, dx) -> canSee(seats, y, x, dy, dx) }
            .count { it == '#' }

    fun conway(seat: Char, count: Int) =
        when {
            seat == 'L' && count == 0 -> '#'
            seat == '#' && count >= 4 -> 'L'
            else -> seat
        }

    fun conway5(seat: Char, count: Int) =
        when {
            seat == 'L' && count == 0 -> '#'
            seat == '#' && count >= 5 -> 'L'
            else -> seat
        }

    var step1Seats = seats

    while (true) {
        val newSeats = step1Seats
            .mapIndexed { y, row ->
                row.mapIndexed { x, seat ->
                    conway(seat, numOccupiedNeighbors(step1Seats, y, x))
                }
            }
//        println()
//        newSeats.debug()
        if (newSeats == step1Seats) break
        step1Seats = newSeats
    }

    println(step1Seats.flatten().count { it == '#' })

    var step2Seats = seats

    while (true) {
        val newSeats = step2Seats
            .mapIndexed { y, row ->
                row.mapIndexed { x, seat ->
                    conway5(seat, numOccupiedVisible(step2Seats, y, x))
                }
            }
        println()
        newSeats.debug()
        if (newSeats == step2Seats) break
        step2Seats = newSeats
    }

    println(step2Seats.flatten().count { it == '#' })
}