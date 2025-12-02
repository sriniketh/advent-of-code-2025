fun main() {
    fun part1(input: List<String>): Int {
        var current = 50
        var password = 0
        input.forEach { line ->
            val rotation = line.take(1)
            val distance = line.drop(1).toInt()
            current = if (rotation == "L") {
                (current - distance).mod(100)
            } else {
                (current + distance).mod(100)
            }
            if (current == 0) ++password
        }
        return password
    }

    fun part2(input: List<String>): Int {
        var current = 50
        var password = 0
        input.forEach { line ->
            val rotation = line.take(1)
            val distance = line.drop(1).toInt()
            val change = if (rotation == "L") -1 else 1
            repeat(distance) {
                current = (current + change).mod(100)
                if (current == 0) ++password
            }
        }
        return password
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
