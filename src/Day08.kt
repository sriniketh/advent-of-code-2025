import java.util.PriorityQueue
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    fun part1(input: List<String>, numShortest: Int): Long {
        val pq = connections(input)

        val disjointSet = DisjointSet(input)
        repeat(numShortest) {
            pq.poll()?.let { (input1, input2, _) ->
                disjointSet.union(input1, input2)
            }
        }

        return disjointSet.find3LargestSizes().fold(1L) { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Long {
        val pq = connections(input)

        val disjointSet = DisjointSet(input)
        while (true) {
            pq.poll()?.let { (input1, input2, _) ->
                disjointSet.union(input1, input2)
                if (disjointSet.isOneTree()) {
                    return input1.split(",").first().toLong() * input2.split(",").first().toLong()
                }
            } ?: run { return -1L }
        }
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput, 10) == 40L)
    check(part2(testInput) == 25272L)

    val input = readInput("Day08")
    part1(input, 1000).println()
    part2(input).println()
}

private fun connections(input: List<String>): PriorityQueue<Connection> {
    val pq = PriorityQueue<Connection>(compareBy { it.distance })
    for (i in input.indices) {
        for (j in i + 1..<input.size) {
            pq.add(Connection(input[i], input[j], calcDistance(input[i], input[j])))
        }
    }
    return pq
}

private data class Connection(
    val junction1: String,
    val junction2: String,
    val distance: Double
)

private fun calcDistance(input1: String, input2: String): Double {
    val (x1, y1, z1) = input1.split(",").map { it.toInt() }
    val (x2, y2, z2) = input2.split(",").map { it.toInt() }
    return sqrt((x1 - x2).toDouble().pow(2) + (y1 - y2).toDouble().pow(2) + (z1 - z2).toDouble().pow(2))
}

private class DisjointSet(private val input: List<String>) {
    private val parent: MutableMap<String, String> = mutableMapOf<String, String>().apply {
        input.forEach { this[it] = it }
    }
    private val size: MutableMap<String, Int> = mutableMapOf<String, Int>().apply {
        input.forEach { this[it] = 1 }
    }
    private var isOneTree: Boolean = false

    private fun find(x: String): String {
        if (parent[x] != x) parent[x] = find(parent.getValue(x))
        return parent.getValue(x)
    }

    fun union(x: String, y: String) {
        val parentX = find(x)
        val parentY = find(y)
        if (parentX != parentY) {
            if (size.getValue(parentX) >= size.getValue(parentY)) {
                parent[parentY] = parentX
                size[parentX] = size.getValue(parentX) + size.getValue(parentY)
                if (size[parentX] == input.size) isOneTree = true
            } else {
                parent[parentX] = parentY
                size[parentY] = size.getValue(parentY) + size.getValue(parentX)
                if (size[parentY] == input.size) isOneTree = true
            }
        }
    }

    fun find3LargestSizes(): List<Int> {
        // cannot just take values here since we may include a subtree that's already been absorbed by another larger one
        // so we need to ensure find(it) == it before considering that subtree
        return size.keys.filter { find(it) == it }
            .map { size.getValue(it) }
            .sortedDescending()
            .take(3)
    }

    fun isOneTree(): Boolean = isOneTree
}
