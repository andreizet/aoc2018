import java.io.File

fun main(args: Array<String>) {
    val inputFile = File("resources/day5_input.txt")

    if (!inputFile.exists()) {
        System.err.println("Oops! There no input file !")
        System.exit(1)
    }

    for (it in inputFile.bufferedReader().readLines()) {
        val initialStr = it
        // Part I:
        val str = performReact(it)
        println("Part I's final result is: " + str.length)

        // Part II:
        val distinctUnits = str.toLowerCase().toCharArray().distinct()

        val shortestPolymer = distinctUnits.map { initialStr.replace(it.toString(), "", true) }.map {
            performReact(it).length
        }
            .min()

        println("Part II's final result is: $shortestPolymer")
    }

}

fun performReact(it: String): String {
    var str = it
    var oldSize = 0
    while (true) {
        oldSize = str.length
        str = findAndRemove(str)
        if (oldSize == str.length)
            break
    }

    return str
}

fun findAndRemove(str: String): String {
    for ((index, char) in str.withIndex()) {
        if (index + 1 > str.length - 1)
            return str

        if (char != str[index + 1] && char.toLowerCase() == str[index + 1].toLowerCase())
            return str.removeRange(index, index + 2)
    }

    return str
}