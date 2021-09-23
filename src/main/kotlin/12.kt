import java.io.File
import kotlin.math.abs

/** Parse line of input into <String, Int> cmd/amount. */
fun String.parse() = Regex("(.)(\\d+)").matchEntire(this)!!.destructured
    .let { (cmd, amt) -> Pair(cmd, amt.toInt()) }

/** Cartesian point. */
data class YX(val y: Int, val x: Int) {
    operator fun times(distance: Int) = YX(y * distance, x * distance)
    operator fun plus(that: YX) = YX(y + that.y, x + that.x)
    infix fun to(that: YX) = abs(x - that.x) + abs(y - that.y)

    companion object {
        val origin = YX(0, 0)
    }
}

/** Cartesian direction & rotation. */
enum class Dir(val yx: YX) {
    NORTH(YX(+1, 0)),
    EAST(YX(0, +1)),
    SOUTH(YX(-1, 0)),
    WEST(YX(0, -1));

    /** Get direction from current dir + rotation°. */
    fun next(deg: Int) = values()[(4 + ordinal + (deg / 90)) % 4]
}

/** part1: recursively process commands & return new location. */
fun move(lines: List<String>, yx: YX, dir: Dir): YX =
    if (lines.isEmpty()) {
        yx  // base case: return movement made
    } else {
        lines.first().parse().let { (cmd, amt) ->
            when (cmd) {
                "F" -> move(lines.drop(1), yx + (dir.yx * amt), dir)
                "N" -> move(lines.drop(1), yx + (Dir.NORTH.yx * amt), dir)
                "E" -> move(lines.drop(1), yx + (Dir.EAST.yx * amt), dir)
                "S" -> move(lines.drop(1), yx + (Dir.SOUTH.yx * amt), dir)
                "W" -> move(lines.drop(1), yx + (Dir.WEST.yx * amt), dir)
                "R" -> move(lines.drop(1), yx, dir.next(amt))
                "L" -> move(lines.drop(1), yx, dir.next(-amt))
                else -> throw RuntimeException()
            }
        }
    }

/** Return new yx coord from rotation deg (use negative rot for rotate-left. */
fun rotateWaypoint(way: YX, rot: Int) =
    when ((360 + rot) % 360) {
        0 -> way
        90 -> YX(-way.x, way.y)
        180 -> YX(-way.y, -way.x)
        270 -> YX(way.x, -way.y)
        else -> throw RuntimeException()
    }

/** part2: recursively process commands & return new location. */
fun move2(lines: List<String>, yx: YX, way: YX): YX =
    if (lines.isEmpty()) {
        yx  // base case: return movement made
    } else {
        lines.first().parse().let { (cmd, amt) ->
            when (cmd) {
                "F" -> move2(lines.drop(1), yx + (way * amt), way)
                "N" -> move2(lines.drop(1), yx, way + (Dir.NORTH.yx * amt))
                "E" -> move2(lines.drop(1), yx, way + (Dir.EAST.yx * amt))
                "S" -> move2(lines.drop(1), yx, way + (Dir.SOUTH.yx * amt))
                "W" -> move2(lines.drop(1), yx, way + (Dir.WEST.yx * amt))
                "R" -> move2(lines.drop(1), yx, rotateWaypoint(way, amt))
                "L" -> move2(lines.drop(1), yx, rotateWaypoint(way, -amt))
                else -> throw RuntimeException()
            }
        }
    }

fun main() {
    val lines = ArrayDeque(File("12.txt").readLines())
    println(YX.origin to move(lines, YX.origin, Dir.EAST))
    println(YX.origin to move2(lines, YX.origin, YX(1, 10)))
}