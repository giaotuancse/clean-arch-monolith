package com.tuangiao.monolith

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.HashMap
import java.util.PriorityQueue

@RequiresApi(Build.VERSION_CODES.N)
fun main() {
    val sol = Solution()
    var input: IntArray = intArrayOf(2, 4, 7, 3,1,5)
    var matrix: Array<IntArray> = arrayOf(intArrayOf(1, 2), intArrayOf(1, 3), intArrayOf(2, 4), intArrayOf(3, 5),intArrayOf(4,6),intArrayOf(5, 6) )
    var result = sol.convertInputToWeightMap(matrix, input)
    result.values.forEach {
        for (value in it) {
            print("$value , ")
        }
        println()
    }
    //sol.findShortestPath(6, result, 1, 5)
    println(sol.isCyclic(6, result).toString())
//  input.forEach({print("$it,")})
}

class Solution() {

    @RequiresApi(Build.VERSION_CODES.N)
    fun findShortestPath(n:Int, graph: HashMap<Int, MutableList<Edge>>, start: Int, end: Int) {
        val queue = PriorityQueue<Edge>(compareBy { it.distance })
        val visited  = BooleanArray(n){false}
        val distance = IntArray(n){Int.MAX_VALUE}
        val leading = IntArray(n){-1}

        queue.add(Edge(start,0))
        with (queue) {
            while (isNotEmpty()) {
                val node = queue.poll()
                // if node is the end -> end loop
                if (node.id == end) {
                    var totalDistance = 0
                    var index = end
                    while (index != -1) {
                        totalDistance+= distance[index]
                        print("${leading[index]} ->")
                        index = leading[index]
                    }
                    println()
                    break
                }
                // if node is alr visited and finalized , skip
                if (visited[node.id]) continue
                // mark the node as visisted
                visited[node.id] = true
                graph[node.id]?.forEachIndexed{ index, edge ->
                    if (distance[node.id] + edge.distance < distance[edge.id]) {
                        distance[edge.id] = distance[node.id] + edge.distance
                        leading[edge.id] = node.id
                        queue.add(edge)
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun isCyclic(n: Int, graph: HashMap<Int, MutableList<Edge>>) : Boolean {
        val visited  = BooleanArray(n){false}
        // loop through the list of node
        for (key in graph.keys) {
            // check the adjacency node
            if (cyclicHelper(key, visited, graph)) return true
        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun cyclicHelper(nodeId: Int, visited: BooleanArray, graph:  HashMap<Int, MutableList<Edge>>) : Boolean {
        val edges = graph.getOrDefault(nodeId, mutableListOf())
        for (edge in edges) {
            if (!visited[edge.id]) {
                if (cyclicHelper(edge.id, visited, graph)) return true
            } else if (edge.id != nodeId) return true
        }
        return false
    }

    fun convertInputToWeightMap(edges: Array<IntArray>, succProb: IntArray): HashMap<Int, MutableList<Edge>> {
        val adj = HashMap<Int, MutableList<Edge>>().apply {
            for ((i, distance) in edges.withIndex()) {
                val edge = Edge(distance[1], succProb[i])
                this[distance[0]] = getOrDefault(distance[0], mutableListOf()).apply { add(edge) }
            }
        }
        return adj
    }

    data class Edge(val id: Int, val distance: Int)

}