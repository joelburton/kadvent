//data class StateMachine(
//    var acc: Int = 0,
//    var ip: Int = 0,
//    val lines: List<Line>
//) {
//    fun runToLoop(): Int {
//        val ipSeen = mutableSetOf<Int>()
//        while (true) {
//            if (ip in ipSeen) return acc
//            ipSeen.add(ip)
//            when (lines[ip].opcode) {
//                "acc" -> acc += lines[ip++].operand
//                "jmp" -> ip += lines[ip].operand
//                "nop" -> ip += 1
//                else -> throw IllegalStateException("oh foo")
//            }
//        }
//    }
//}


import java.io.File

/** Single instruction for state machine. */
data class Instruction(val opcode: String, val operand: Int)

/** Marker exception raised during state machine runs. */
class Answer(val result: Int) : Throwable()

val lines = File("8.txt")
    .readLines()
    .map { it.split(" ") }
    .map { (opcode, operand) -> Instruction(opcode, operand.toInt()) }

/** Run state machine, throwing result when loop detected. */

fun toLoop(acc: Int = 0, ip: Int = 0, ipSeen: Set<Int> = setOf()) {
    if (ip in ipSeen) throw Answer(acc)
    val (opcode, operand) = lines[ip]
    return when (opcode) {
        "acc" -> toLoop(acc + operand, ip + 1, ipSeen + ip)
        "jmp" -> toLoop(acc, ip + operand, ipSeen + ip)
        "nop" -> toLoop(acc, ip + 1, ipSeen + ip)
        else -> throw IllegalStateException()
    }
}

/** Run state machine to after final line, throwing accum when we reach there.
 *
 * This can branch exactly one time: a single jmp/nop can be switched.
 */

fun toEnd(
    acc: Int = 0,
    ip: Int = 0,
    ipSeen: Set<Int> = setOf(),
    fixed: Boolean = false
) {
    if (ip in ipSeen) return
    if (ip > lines.lastIndex) throw Answer(acc)
    val (opcode, operand) = lines[ip]
    when (opcode) {
        "acc" -> toEnd(acc + operand, ip + 1, ipSeen + ip, fixed)
        "jmp" -> {
            toEnd(acc, ip + operand, ipSeen + ip, fixed)
            if (!fixed) toEnd(acc, ip + 1, ipSeen + ip, true)
        }
        "nop" -> {
            toEnd(acc, ip + 1, ipSeen + ip, fixed)
            if (!fixed) toEnd(acc, ip + operand, ipSeen + ip, true)
        }
        else -> throw IllegalStateException()
    }
}

fun main() {
    try { toLoop() } catch (exc: Answer) { println(exc.result) }
    try { toEnd() } catch (exc: Answer) { println(exc.result) }
}
