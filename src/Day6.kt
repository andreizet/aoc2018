import java.io.File

fun main(args: Array<String>) {
    val inputFile = File("resources/day6_input.txt")

    if (!inputFile.exists()) {
        System.err.println("Oops! There no input file !")
        System.exit(1)
    }

    val positions: MutableList<Pair<Int, Int>> = mutableListOf()
    val grid: MutableList<Pair<Int, Int>>
    var sum = 0

    //Add items inside a list of Pair<Int, Int>
    for (it in inputFile.bufferedReader().readLines()) {
        val spl = it.split(",").map(String::trim)
        positions.add(Pair(spl[0].toInt(), spl[1].toInt()))
    }

    //Compute matrix margins
    val left = positions.minBy { it.first }?.first
    val top = positions.minBy { it.second }?.second
    val right = positions.maxBy { it.first }?.first
    val bottom = positions.maxBy { it.second }?.second

    //Cross matrix from start to end
    grid = left!!.rangeTo(right!!).flatMap { i ->
        top!!.rangeTo(bottom!!).map { j ->
            //Add to sum
            if (dist(i, j, positions) < 10000)
                sum += 1
            //Compute Manhattan Distance then store it to grid variable
      manhattan(i, j, positions)
        }
    }.toMutableList()

    //Group items, then get the one with the most values
    val res = grid.groupBy { it }.maxBy { it.value.size }?.value?.size

    println("Part I's final result is: $res")
    println("Part II's final result is: $sum")
}

fun manhattan(i: Int, j: Int, allPositions: MutableList<Pair<Int, Int>>): Pair<Int, Int> {
    val distance = allPositions.map {
        it to Math.abs(it.first - i) + Math.abs(it.second - j)
    }

    val minDistance = distance.minBy { it.second }
    val count = distance.count { it.second == minDistance!!.second }

    if (count > 1)
        return Pair(0, 0)
    else
        return minDistance!!.first
}

fun dist(i: Int, j: Int, allPositions: MutableList<Pair<Int, Int>>): Int {
    var toReturn: Int = 0
    allPositions.map {
        toReturn += Math.abs(it.first - i) + Math.abs(it.second - j)
    }

    return toReturn
}