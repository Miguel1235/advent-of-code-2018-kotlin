import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private fun part1(records: List<Record>, isPart2: Boolean = false): Int {
    val guardIdRegex = Regex("""#(\d+)""")
    var currentGuardId = -1
    val allGuards = mutableSetOf<Int>()
    val days = mutableMapOf<String, SleepTimes>()

    for(record in records) {
        guardIdRegex.find(record.action)?.groupValues?.drop(1)?.let {
            currentGuardId = it.first().toInt()
            allGuards.add(currentGuardId)
        }
        if(record.action.contains("asleep") || record.action.contains("wake")) {
            val stringDate = "${record.date.monthValue}:${record.date.dayOfMonth}"
            days.putIfAbsent(stringDate, SleepTimes(currentGuardId, mutableListOf()))
            days[stringDate]!!.sleeps.add(record.date.minute)
        }
    }

    val sleeps = allGuards.map { guardId ->
        val allSleeps = days.values.filter { it.guardId == guardId }.map { it.sleeps }.flatten().chunked(2).sumOf { it.last() - it.first() }
        Pair(guardId, allSleeps)
    }
    val id = sleeps.sortedBy { it.second }.reversed().first().first
    val schedules = days.values.filter { it.guardId == id }.map { it.sleeps }.flatten().chunked(2).map { Pair(it.first(), it.last()-1) }
    val minutesMostAsleep = mutableMapOf<Int, Int>()

    for(minute in 0..59) {
        for(day in schedules) {
            if(day.first <= minute && minute <= day.second) {
                minutesMostAsleep.putIfAbsent(minute, 0)
                minutesMostAsleep[minute] = minutesMostAsleep[minute]!! + 1
            }
        }
    }

    val mostMin = mutableListOf<Triple<Int, Int,Int>>()
    for(guardId in allGuards) {
        val schedules = days.values.filter { it.guardId == guardId }.map { it.sleeps }.flatten().chunked(2).map { Pair(it.first(), it.last()-1) }
        val minutesMostAsleep = mutableMapOf<Int, Int>()
        for(minute in 0..59) {
            for(day in schedules) {
                if(day.first <= minute && minute <= day.second) {
                    minutesMostAsleep.putIfAbsent(minute, 0)
                    minutesMostAsleep[minute] = minutesMostAsleep[minute]!! + 1
                }
            }
        }
        if(minutesMostAsleep.isEmpty()) continue
        val r = minutesMostAsleep.entries.sortedBy { it.value }.reversed().take(1).first()
        mostMin.add(Triple(guardId, r.key,r.value))
    }
    val (idS, time) = mostMin.sortedBy { it.third }.reversed().take(1).first()
    val result = minutesMostAsleep.entries.sortedBy { it.value }.reversed().take(1).first().key
    return if(isPart2) idS*time else result*id
}

data class SleepTimes(val guardId: Int, val sleeps: MutableList<Int>)
data class Record(val date: LocalDateTime , val action: String)

private fun sortRecords(input: List<String>): List<Record> {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    return input.map {
        val (stringDate, action) = it.split("]")

        val date = LocalDateTime.parse(stringDate.split("[")[1], formatter)
        Record(date, action)
    }.sortedBy { it.date }
}

fun main() {
    val testInput = sortRecords(readInput("Day04_test"))
    check(part1(testInput) == 240)
    check(part1(testInput, true) == 4455)

    val input = sortRecords(readInput("Day04"))
    check(part1(input) == 39584)
    check(part1(input, true) == 55053)

}
 