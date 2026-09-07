fun main() {
    fun part1(input: List<String>): Long {
        val indexOfSpace = input.indexOfFirst { it.isEmpty() }
        val ranges = pargeRangesFromInput(input, indexOfSpace)
        val ids = input.drop(indexOfSpace + 1).map { it.toLong() }
        val mergedRanges = mergeRanges(ranges)

        var freshIds = 0L
        ids.forEach { id ->
            if (findInRanges(mergedRanges, id)) {
                ++freshIds
            }
        }

        return freshIds
    }

    fun part2(input: List<String>): Long {
        val indexOfSpace = input.indexOfFirst { it.isEmpty() }
        val ranges = pargeRangesFromInput(input, indexOfSpace)
        val mergedRanges = mergeRanges(ranges)

        var freshIds = 0L
        mergedRanges.forEach { (start, end) ->
            freshIds += (end - start + 1)
        }
        return freshIds
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 3L)
    check(part2(testInput) == 14L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

private fun pargeRangesFromInput(
    input: List<String>,
    indexOfSpace: Int
): List<Pair<Long, Long>> =
    input.take(indexOfSpace).map { range -> range.split("-") }.map { it[0].toLong() to it[1].toLong() }.sortedBy { it.first }

private fun mergeRanges(ranges: List<Pair<Long, Long>>): MutableList<Pair<Long, Long>> {
    val mergedRanges = mutableListOf<Pair<Long, Long>>()
    ranges.forEach { (start, end) ->
        if (mergedRanges.isNotEmpty()) {
            val (prevStart, prevEnd) = mergedRanges.last()
            if (start in prevStart..prevEnd) {
                mergedRanges.removeLast()
                mergedRanges.add(prevStart to maxOf(end, prevEnd))
            } else {
                mergedRanges.add(start to end)
            }
        } else {
            mergedRanges.add(start to end)
        }
    }
    return mergedRanges
}

private fun findInRanges(ranges: List<Pair<Long, Long>>, value: Long): Boolean {
    var start = 0
    var end = ranges.size - 1
    while (start <= end) {
        val mid = start + (end - start) / 2
        val (rangeStart, rangeEnd) = ranges[mid]
        if (value in rangeStart..rangeEnd) return true
        else if (value < rangeStart) end = mid - 1
        else start = mid + 1
    }
    return false
}
