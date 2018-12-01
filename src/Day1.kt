import java.io.File

fun main(args: Array<String>) {
    val inputFile = File("resources/day1_input.txt")
    var lastFrequency = 0
    var isFistItem = true
    var foundFrequencies: MutableList<Int> = mutableListOf()

    if (!inputFile.exists()) {
        System.err.println("Oops! There no input file !")
        System.exit(1)
    }

    /* Reads file line by line. Each line is converted to Int.
     * If a line has an invalid Int, it gets ignored
     */
    val inputArray: List<Int> = inputFile.bufferedReader().readLines().map {
        try {
            lastFrequency += it.toInt()
            if (!isFistItem)
                foundFrequencies.add(lastFrequency)

            isFistItem = false
            it.toInt()
        } catch (exc: NumberFormatException) {
            System.err.println("Hey, this is not an Int: $it! I'm ignoring it!")
            0
        }
    }

    var frequency: Int = inputArray.sum()

    println("PartOne: Frequency is $frequency")

    while (true) {
        for (it in inputArray) {
            frequency += it
            if (foundFrequencies.contains(frequency)) {
                println("PartTwo: Frequency is $frequency")
                return
            } else {
                foundFrequencies.add(frequency)
            }
        }
    }
}