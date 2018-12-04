import java.io.File

fun main(args: Array<String>) {
    val inputFile = File("resources/day3_input.txt")
    val fileRows: MutableList<String> = mutableListOf()
    val allClaims: ArrayList<Pair<Int, Int>> = ArrayList()
    val overlapping = mutableMapOf<Pair<Int, Int>, Int>()
    val allIds = mutableSetOf<Int>()
    val idsToClaims: MutableMap<Int, MutableList<Pair<Int, Int>>> = mutableMapOf()

    if (!inputFile.exists()) {
        System.err.println("Oops! There no input file !")
        System.exit(1)
    }

    /* Part I:
       Computes matrix then counts all intersections
     */
    for (it in inputFile.bufferedReader().readLines()) {
        val splitStr = it.split('#', '@', ',', ':', 'x').map(String::trim)

        val id = splitStr[1].toInt()

        val offsetX = splitStr[2].toInt()
        val offsetY = splitStr[3].toInt()

        val width = splitStr[4].toInt()
        val height = splitStr[5].toInt()

        (0 + offsetX until width + offsetX).flatMap { w ->
            (0 + offsetY until height + offsetY).map { h ->
                val pair = Pair(w, h)
                allClaims.add(pair)
                allIds.add(id)

                var item: MutableList<Pair<Int, Int>> = mutableListOf()
                if (idsToClaims.containsKey(id))
                    item = idsToClaims.getValue(id)
                item.add(pair)

                idsToClaims.put(id, item)
            }
        }

        fileRows.add(it)
    }

    /* Creates grouping source, then counts duplicates for each square.
       In the end, it counts total intersections. > 1 means that current square intersects no other one
     */
    println("Part I's final result is: " + allClaims.map { it }.groupingBy { it }.eachCount().count { it.value > 1 })

    /* Part II:
     * Loops all claims, then removes current claim from allIds if an intersection was found
     */
    idsToClaims.mapKeys { it.key }.forEach {
        val id = it.key
        it.value.forEach { step3 ->
            val found = overlapping.getOrPut(step3) { id }
            if (found != id) {
                allIds.remove(found)
                allIds.remove(id)
            }
        }
    }

    println("Part II's final result is: $allIds")
}