import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private fun part1(records: List<Record>, isPart2: Boolean = false): Int {
    val guardIdRegex = Regex("""#(\d+)""")
    var currentGuardId = -1
    val allGuards = mutableSetOf<Int>()
    val days = mutableMapOf<String, SleepTimes>()

    for (record in records) {
        guardIdRegex.find(record.action)?.groupValues?.drop(1)?.let {
            currentGuardId = it.first().toInt()
            allGuards.add(currentGuardId)
        }
        if (record.action.contains("asleep") || record.action.contains("wake")) {
            val stringDate = "${record.date.monthValue}:${record.date.dayOfMonth}"
            days.putIfAbsent(stringDate, SleepTimes(currentGuardId, mutableListOf()))
            days[stringDate]!!.sleeps.add(record.date.minute)
        }
    }

    val intervalsByGuard: Map<Int, List<IntRange>> = allGuards.associateWith { guardId ->
        days.values
            .asSequence()
            .filter { it.guardId == guardId }
            .flatMap { it.sleeps.toIntervals().asSequence() }
            .toList()
    }
    val totalSleepByGuard: Map<Int, Int> = intervalsByGuard.mapValues { (_, intervals) ->
        intervals.sumOf { it.last - it.first + 1 }
    }
    val maxSleepGuard = totalSleepByGuard.maxByOrNull { it.value }!!.key
    val maxGuardMinute = minuteFrequency(intervalsByGuard[maxSleepGuard].orEmpty())
        .maxByOrNull { it.value }?.key ?: 0

    val bestMinutePerGuard: List<Triple<Int, Int, Int>> = intervalsByGuard.mapNotNull { (guardId, intervals) ->
        if (intervals.isEmpty()) null
        else {
            val (min, cnt) = minuteFrequency(intervals).maxByOrNull { it.value }!!
            Triple(guardId, min, cnt)
        }
    }
    val (bestGuard, bestMinute) = bestMinutePerGuard.maxByOrNull { it.third } ?: Triple(0, 0, 0)

    return if (isPart2) bestGuard * bestMinute else maxSleepGuard * maxGuardMinute
}

data class SleepTimes(val guardId: Int, val sleeps: MutableList<Int>)
data class Record(val date: LocalDateTime, val action: String)

private fun List<Int>.toIntervals(): List<IntRange> {
    return this.chunked(2).map { (start, end) -> start until end }
}

private fun minuteFrequency(intervals: List<IntRange>): Map<Int, Int> {
    val freq = IntArray(60)
    for (interval in intervals) {
        for (m in interval) {
            if (m in 0..59) freq[m]++
        }
    }
    return buildMap {
        for (m in 0..59) if (freq[m] > 0) put(m, freq[m])
    }
}

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

//    val input = sortRecords(readInput("Day04"))
//    check(part1(input) == 39584)
//    check(part1(input, true) == 55053)
}
 