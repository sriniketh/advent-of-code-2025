import kotlin.collections.indices

fun main() {
    fun part1(input: List<String>): Int {
        return pickUpRolls(input.map { it.toCharArray() })
    }

    fun part2(input: List<String>): Int {
        val inputRolls = input.map { it.toCharArray() }
        var totalRolls = 0
        var pickedUpRolls = -1
        while (pickedUpRolls != 0) {
            pickedUpRolls = pickUpRolls(inputRolls)
            totalRolls += pickedUpRolls
            for (i in inputRolls.indices) {
                for (j in inputRolls[i].indices) {
                    if (inputRolls[i][j] == 'x') inputRolls[i][j] = '.'
                }
            }
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

private fun pickUpRolls(input: List<CharArray>): Int {
    var rolls = 0
    for (i in input.indices) {
        for (j in input[i].indices) {
            if (input[i][j] == '@') {
                var surroundingRolls = 0
                directions.forEach { (x, y) ->
                    if (isRoll(input, i + x, j + y)) ++surroundingRolls
                }
                if (surroundingRolls < 4) {
                    input[i][j] = 'x'
                    ++rolls
                }
            }
        }
    }
    return rolls
}

private fun isRoll(input: List<CharArray>, x: Int, y: Int): Boolean {
    return x >= 0 && x < input.size && y >= 0 && y < input[x].size && (input[x][y] == '@' || input[x][y] == 'x')
}
