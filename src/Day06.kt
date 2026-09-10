import java.math.BigInteger

fun main() {
    fun part1(input: List<String>): String {
        val operators = input.last().trim().split(WHITESPACE)
        val numbers = input.dropLast(1).map { it.trim().split(WHITESPACE) }

        val answers = operators.indices.map { i ->
            val op = operators[i][0]
            numbers.fold(initialValue(op)) { acc, row -> acc.applyOperation(op, BigInteger(row[i])) }
        }
        return answers.reduce(BigInteger::add).toString()
    }

    fun part2(input: List<String>): String {
        val operatorRow = input.last()
        val columnRanges = operatorRow
            .mapIndexedNotNull { index, char -> if (char == '+' || char == '*') index else null }
            .plus(input.maxOf { it.length } + 1)
            .zipWithNext()
        val numbers = input.dropLast(1)

        val answers = columnRanges.map { (start, end) ->
            val op = operatorRow[start]
            (end - 2 downTo start).fold(initialValue(op)) { acc, j ->
                val number = BigInteger(numbers.map { it[j] }.joinToString("").trim())
                acc.applyOperation(op, number)
            }
        }
        return answers.reduce(BigInteger::add).toString()
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == "4277556")
    check(part2(testInput) == "3263827")

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}

private val WHITESPACE = Regex("\\s+")

private fun initialValue(op: Char): BigInteger = if (op == '*') BigInteger.ONE else BigInteger.ZERO

private fun BigInteger.applyOperation(op: Char, operand: BigInteger): BigInteger =
    if (op == '*') multiply(operand) else add(operand)
