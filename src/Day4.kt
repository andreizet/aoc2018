import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun main(args: Array<String>) {
    val inputFile = File("resources/day4_input.txt")
    val guards: MutableList<Guard> = mutableListOf()
    var sortedGuards: MutableList<Guard> = mutableListOf()
    val df = SimpleDateFormat("yyyy-MM-dd hh:mm")
    val guardsTotalSleep = mutableMapOf<Int, Long>()
    val guardToSleeps = mutableMapOf<Int, List<Int>>()

    if (!inputFile.exists()) {
        System.err.println("Oops! There no input file !")
        System.exit(1)
    }

    /* Part I:
       Parse and store all guard items
     */
    for (it in inputFile.bufferedReader().readLines()) {
        val splitStr = it.split('[', ']', '#').map(String::trim)

        var id = -1
        if (it.contains("#"))
            id = splitStr[3].split(' ').map(String::trim)[0].toInt()

        guards.add(Guard(df.parse(splitStr[1]), id, splitStr[2]))
    }

    val cmp = compareBy<Guard> { it.mDate }
    guards.sortedWith(cmp).forEach {
        sortedGuards.add(it)
        it.mDate
    }

    /*
    * Sort all guards, then store total time slept for each one, both with a List<Int> containing each minute slept
    * */
    var guardID = -1
    var asleepAt = Date()
    sortedGuards.map {

        if (it.mID > 0) {
            guardID = it.mID
            asleepAt = Date()
        }

        if (it.mAction == "falls asleep")
            asleepAt = it.mDate

        if (it.mAction == "wakes up") {
            val diff = it.mDate.time - asleepAt.time
            val seconds = diff / 1000
            var minutes = seconds / 60

            if (guardsTotalSleep.containsKey(guardID))
                minutes += guardsTotalSleep.getValue(guardID)

            guardsTotalSleep[guardID] = minutes

            val asleepMin = asleepAt.minutes
            val wakeMin = it.mDate.minutes
            val sleepMins = (asleepMin until wakeMin).toList()
            guardToSleeps.merge(guardID, sleepMins) { a, b -> a + b }
        }
    }

    //Find guard with max time asleep, then get the minute asleep with the most occurrences
    val minute = guardToSleeps.maxBy { it.value.size }!!
        .run {
            key * value.groupBy { it }.maxBy { it.value.size }?.key!!
        }

    println("Part I's final result is: $minute")

    //Loop all guards, compute the number of occurrences per minute, then get the maximum
    val mostAsleept = guardToSleeps.flatMap { entry ->
        entry.value.map { minute ->
            entry.key to minute
        }
    }
        .groupBy { it }.maxBy { it.value.size }?.key!!
        .run { first * second }

    println("Part I's final result is: $mostAsleept")
}

class Guard(date: Date, guardID: Int, action: String) {
    val mDate: Date = date
    val mID: Int = guardID
    val mAction: String = action
}