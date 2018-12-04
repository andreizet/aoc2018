import java.io.File

fun main(args: Array<String>) {
    val inputFile = File("resources/day2_input.txt")
    var fileRows: MutableList<String> = mutableListOf()
    var countForTwo = 0
    var countForTree = 0

    if (!inputFile.exists()) {
        System.err.println("Oops! There no input file !")
        System.exit(1)
    }

    /* Part I:
     * Loop through all file lines and find if there are chars with 2 or 3 occurrences on each line
     */
    for (it in inputFile.bufferedReader().readLines()) {
        fileRows.add(it)
        var foundTwo = false
        var foundTree = false

        for (character in it) {
            val counts = countOccurrences(it, character.toString())

            when (counts) {
                2 -> {
                    if (!foundTwo)
                        countForTwo++
                    foundTwo = true
                }
                3 -> {
                    if (!foundTree)
                        countForTree++
                    foundTree = true
                }
            }

            if (foundTwo && foundTree)
                continue
        }
    }

    val result = countForTwo * countForTree
    println("Final result is: $result")

    /*
     * Part II:
     * Loop rows and compare each row with another.
     * Will be considered only rows with only one different character
     */
    for ((index1, row1) in fileRows.withIndex()) {
        for ((index2, row2) in fileRows.withIndex()) {
            //Ignore already compared elements
            if (index2 <= index1)
                continue

            val count = countDifferentChars(row1, row2)
            if (count == 1) {
                val common = removeDiffChar(row1, row2)
                println("Found one difference between $row1 and $row2. Final result $common")
                System.exit(0)
            }
        }
    }

}

fun countOccurrences(s: String, sub: String): Int = s.split(sub).size - 1

fun countDifferentChars(firstString: String, secondString: String): Int {
    var charsChanged = 0

    for ((index, character) in firstString.withIndex())
        if (character != secondString[index])
            charsChanged++

    return charsChanged
}

fun removeDiffChar(firstString: String, secondString: String): String {
    var charsChanged = 0
    var toReturn = ""

    for ((index, character) in firstString.withIndex()) {
        if (character != secondString[index])
            charsChanged++
        else
            toReturn += character
    }

    return toReturn
}