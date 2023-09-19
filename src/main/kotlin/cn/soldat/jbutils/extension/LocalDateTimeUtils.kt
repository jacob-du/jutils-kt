package cn.soldat.jbutils.extension

import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*


// 扩展 LocalDate LocalTime LocalDateTime 函数
fun LocalDateTime.toDate(): Date = Date.from(this.atZone(ZoneId.systemDefault()).toInstant())
fun LocalDate.toDateString(): String = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(this)
fun LocalTime.toTimeString(): String = DateTimeFormatter.ofPattern("HH:mm:ss").format(this)
//fun LocalDateTime.toDateTimeString(): String = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(this)
fun LocalDateTime.toDateTimeString(withSecond: Boolean = true, separator: Boolean = false): String{
    if (separator) return DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(this)

    return if (withSecond) DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(this)
    else DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(this)
}
fun LocalDateTime.toDateString(): String = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(this)
fun LocalDateTime.toTimeString(): String = DateTimeFormatter.ofPattern("HH:mm:ss").format(this)
fun LocalDateTime.toZone(from: ZoneId, to: ZoneId): LocalDateTime {
    val zonedTime = this.atZone(from)
    val converted = zonedTime.withZoneSameInstant(to)
    return converted.toLocalDateTime()
}
fun LocalDateTime.toZone(zoneId: ZoneId): LocalDateTime {
    return this.toZone(ZoneId.systemDefault(), zoneId)
}
fun LocalDateTime.toUtc(): LocalDateTime = this.toZone(ZoneOffset.UTC)
fun LocalDateTime.toUtc(from: ZoneId): LocalDateTime = this.toZone(from, ZoneOffset.UTC)