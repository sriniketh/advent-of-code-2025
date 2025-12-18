fun main() {
    fun part1(input: List<String>): Long {
        val ranges = input.first().split(",")
        var answer = 0L
        ranges.map { it.split("-") }
            .forEach { range ->
                (range[0].toLong()..range[1].toLong())
                    .forEach {
                        val number = it.toString()
                        val firstPart = number.take(number.length / 2)
                        val secondPart = number.drop(number.length / 2)
                        if (firstPart == secondPart) {
                            answer += it
                        }
                    }
            }
        return answer
    }

    fun part2(input: List<String>): Long {
        val ranges = input.first().split(",")
        var answer = 0L
        ranges.map { it.split("-") }
            .forEach { range ->
                (range[0].toLong()..range[1].toLong())
                    .forEach {
                        val number = it.toString()
                        if (number.matches("""^(\d+)\1+$""".toRegex())) {
                            answer += it
                        }
                    }
            }
        return answer
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 1227775554L)
    check(part2(testInput) == 4174379265L)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
