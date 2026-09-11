fun main() {
    fun part1(input: List<String>): Long {
        val startIndex = input[0].indexOfFirst { it == 'S' }
        if (startIndex == -1) return -1L

        val inputArray = input.map { it.toCharArray() }
        inputArray[0][startIndex] = '|'

        var beams = 0L
        for (i in 1..<inputArray.size) {
            for (j in inputArray[i].indices) {
                if (inputArray[i - 1][j] == '|') {
                    if (inputArray[i][j] == '^') {
                        val (left, right) = neighbors(j, inputArray[i].size)
                        var didSplit = false
                        if (left != null && inputArray[i][left] != '|') {
                            inputArray[i][left] = '|'
                            didSplit = true
                        }
                        if (right != null && inputArray[i][right] != '|') {
                            inputArray[i][right] = '|'
                            didSplit = true
                        }
                        if (didSplit) ++beams
                    } else {
                        inputArray[i][j] = '|'
                    }
                }
            }
        }
        return beams
    }

    fun part2(input: List<String>): Long {
        val startIndex = input[0].indexOfFirst { it == 'S' }
        if (startIndex == -1) return -1L

        val inputArray = input.map { it.toCharArray() }
        inputArray[0][startIndex] = '|'
        val cache = Array(inputArray.size) { LongArray(inputArray[0].size) { -1L } }

        return traverse(inputArray, 0, startIndex, cache)
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 21L)
    check(part2(testInput) == 40L)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}

private fun traverse(input: List<CharArray>, level: Int, index: Int, cache: Array<LongArray>): Long {
    if (level == input.size - 1 && input[level][index] == '.') return 1L

    if (cache[level][index] != -1L) return cache[level][index]

    val timelines = if (input[level][index] == '.') {
        traverse(input, level + 1, index, cache)
    } else {
        val (left, right) = neighbors(index, input[level].size)
        val timelinesRight = right?.let { traverse(input, level + 1, it, cache) } ?: 0L
        val timelinesLeft = left?.let { traverse(input, level + 1, it, cache) } ?: 0L
        timelinesLeft + timelinesRight
    }
    cache[level][index] = timelines
    return timelines
}

private fun neighbors(index: Int, size: Int): Pair<Int?, Int?> =
    (if (index - 1 >= 0) index - 1 else null) to (if (index + 1 < size) index + 1 else null)
