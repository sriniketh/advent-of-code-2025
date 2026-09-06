fun main() {

    fun part1(input: List<String>): Int {
        var result = 0
        input.forEach { bank ->
            result += maxSubsequence(bank, 2).toInt()
        }
        return result
    }

    fun part2(input: List<String>): Long {
        var result = 0L
        input.forEach { bank ->
            result += maxSubsequence(bank, 12).toLong()
        }
        return result
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 357)
    check(part2(testInput) == 3121910778619L)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

/** Largest k-digit number obtainable by deleting characters from [bank] while keeping order. */
private fun maxSubsequence(bank: String, k: Int): String {
    var toDrop = bank.length - k
    val stack = ArrayDeque<Char>()
    for (char in bank) {
        while (toDrop > 0 && stack.isNotEmpty() && stack.last() < char) {
            stack.removeLast()
            toDrop--
        }
        stack.addLast(char)
    }
    return stack.take(k).joinToString("")
}
