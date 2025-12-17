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

    fun part2(input: List<String>): Int {
        //TODO
        return -1
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 1227775554L)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
