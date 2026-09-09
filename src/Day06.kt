import java.math.BigInteger

fun main() {
    fun part1(input: List<String>): String {
        val operations = input.last().trim().split("\\s+".toRegex())
        val numbers = input.dropLast(1).map { it.trim().split("\\s+".toRegex()) }

        val answers = Array(operations.size) { BigInteger("-1") }
        for (i in operations.indices) {
            when (operations[i]) {
                "*" -> answers[i] = BigInteger("1")
                "+" -> answers[i] = BigInteger("0")
            }
        }

        for (i in operations.indices) {
            for (j in numbers.indices) {
                val number = BigInteger(numbers[j][i])
                when (operations[i]) {
                    "*" -> answers[i] = answers[i].multiply(number)
                    "+" -> answers[i] = answers[i].add(number)
                }
            }
        }
        return answers.sumOf { it }.toString()
    }

    fun part2(input: List<String>): String {
        val inputOperations = input.last()
        val operations = inputOperations
            .mapIndexedNotNull { index, char -> if (char == '+' || char == '*') index else null }
            .plus(input.maxBy { it.length }.length + 1)
            .zipWithNext()
        val numbers = input.dropLast(1)

        val answers = Array(operations.size) { BigInteger("-1") }
        for (i in operations.indices) {
            when (inputOperations[operations[i].first]) {
                '*' -> answers[i] = BigInteger("1")
                '+' -> answers[i] = BigInteger("0")
            }
        }

        for (i in operations.indices) {
            val (start, end) = operations[i]
            for (j in end - 2 downTo start) {
                val numberString = StringBuilder()
                for (k in numbers.indices) {
                    numberString.append(numbers[k][j])
                }
                val number = BigInteger(numberString.toString().trim())
                when (inputOperations[operations[i].first]) {
                    '*' -> answers[i] = answers[i].multiply(number)
                    '+' -> answers[i] = answers[i].add(number)
                }
            }
        }
        return answers.sumOf { it }.toString()
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == "4277556")
    check(part2(testInput) == "3263827")

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
