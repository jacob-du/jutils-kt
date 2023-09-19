package cn.soldat.jbutils.extension

import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

// 扩展 Date 函数
fun Date.toLocalDateTime(): LocalDateTime = (this.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
fun Date.toLocalDate(): LocalDate = (this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
fun Date.toLocalTime(): LocalTime = (this.toInstant().atZone(ZoneId.systemDefault()).toLocalTime())
fun Date.utcToLocalDateTime(): String {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8)
    return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.time)
}
fun Date.utcToLocalDate(): String{
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8)
    return SimpleDateFormat("yyyy-MM-dd").format(calendar.time)
}
fun Date.utcToLocalTime(): String{
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8)
    return SimpleDateFormat("HH:mm:ss").format(calendar.time)
}

fun Date.personalization(): String {
    val date = Calendar.getInstance().also { it.time = this }
    val today = Calendar.getInstance().apply { time = Date() }
    today.set(Calendar.HOUR_OF_DAY, 0)
    today.set(Calendar.MINUTE, 0)
    // 判断是不是今天
    if (date.after(today)) {
        return "今天 " + SimpleDateFormat("HH:mm").format(this)
    }
    // 判断是不是昨天
    if (date.after(today.apply { add(Calendar.DATE, -1) })) {
        return "昨天 " + SimpleDateFormat("HH:mm").format(this)
    }
    // 判断是不是前天
    if (date.after(today.apply { add(Calendar.DATE, -2) })) {
        return "前天 " + SimpleDateFormat("HH:mm").format(this)
    }
    // 判断是不是今年
    if (date.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
        return SimpleDateFormat("MM-dd HH:mm").format(this)
    }
    return SimpleDateFormat("yyyy-MM-dd HH:mm").format(this)
}

fun Date.personalDate(): String {
    val date = Calendar.getInstance().also { it.time = this }
    val today = Calendar.getInstance()
    if (
        today.get(Calendar.YEAR) == date.get(Calendar.YEAR)
        && today.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR)
    ) return "今天"

    if (
        today.get(Calendar.YEAR) == date.get(Calendar.YEAR)
        && today.get(Calendar.DAY_OF_YEAR) -1 == date.get(Calendar.DAY_OF_YEAR)
    ) return "昨天"

    if (
        today.get(Calendar.YEAR) == date.get(Calendar.YEAR)
        && today.get(Calendar.DAY_OF_YEAR) -2 == date.get(Calendar.DAY_OF_YEAR)
    ) return "前天"

    // 判断是不是昨天
//    if (date.after(today.apply { add(Calendar.DATE, -1) })) return "昨天"
    // 判断是不是前天
//    if (date.after(today.apply { add(Calendar.DATE, -2) })) return "前天"
    // 判断是不是今年
    if (date.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
        return SimpleDateFormat("MM-dd").format(this)
    }
    return SimpleDateFormat("yyyy-MM-dd").format(this)
}