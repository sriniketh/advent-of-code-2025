fun main() {
    fun part1(input: List<String>): Int {
        return pickUpRolls(input.map { it.toCharArray() }).size
    }

    fun part2(input: List<String>): Int {
        val inputRolls = input.map { it.toCharArray() }
        var totalRolls = 0
        while (true) {
            val pickedUpRolls = pickUpRolls(inputRolls)
            if (pickedUpRolls.isEmpty()) break
            totalRolls += pickedUpRolls.size
            for ((i, j) in pickedUpRolls) inputRolls[i][j] = '.'
        }
        return totalRolls
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 43)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

private val directions = setOf(
    0 to 1,
    1 to 1,
    1 to 0,
    1 to -1,
    0 to -1,
    -1 to -1,
    -1 to 0,
    -1 to 1
)

private fun pickUpRolls(input: List<CharArray>): List<Pair<Int, Int>> {
    val pickedUp = mutableListOf<Pair<Int, Int>>()
    for (i in input.indices) {
        for (j in input[i].indices) {
            if (input[i][j] == '@') {
                var surroundingRolls = 0
                directions.forEach { (dRow, dCol) ->
                    if (isRoll(input, i + dRow, j + dCol)) ++surroundingRolls
                }
                if (surroundingRolls < 4) {
                    input[i][j] = 'x'
                    pickedUp.add(i to j)
                }
            }
        }
    }
    return pickedUp
}

private fun isRoll(input: List<CharArray>, row: Int, col: Int): Boolean {
    return row >= 0 && row < input.size && col >= 0 && col < input[row].size &&
        (input[row][col] == '@' || input[row][col] == 'x')
}
