fun main() {

    fun part1(input: List<String>): Int {
        var result = 0
        input.forEach { bank ->
            var max1 = bank[bank.length - 1]
            var max2 = bank[bank.length - 2]
            for (i in bank.length - 3 downTo 0) {
                if (bank[i].code >= max2.code) {
                    if (max2.code >= max1.code) max1 = max2
                    max2 = bank[i]
                }
            }
            result += "$max2$max1".toInt()
        }
        return result
    }

    fun part2(input: List<String>): Long {
        var result = 0L
        input.forEach { bank ->
            var max = bank.takeLast(12)
            for (i in bank.length - 13 downTo 0) {
                if (bank[i].code >= max[0].code) {
                    max = recalculateMax(max, bank[i])
                }
            }
            result += max.toLong()
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

private fun recalculateMax(input: String, value: Char): String {
    val max = StringBuilder(input)
    var current: Char = value
    for (i in max.indices) {
        if (current.code >= max[i].code) {
            max[i] = current.also { current = max[i] }
        } else {
            break
        }
    }
    return max.toString()
}
