package cn.soldat.jbutils.extension

import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

const val ZONE_OFFSET_CST = "CST" // 中国标准时间
const val ZONE_OFFSET_CET = "CET" // 欧洲中部时间 Central European Time
const val ZONE_OFFSET_UTC = "UTC" // 时间协调时间
const val ZONE_OFFSET_GMT = "GMT" // 格林威治时间
const val ZONE_OFFSET_GMT_UTC = "GMT/UTC" // 格林威治时间和世界协调时间 不在乎秒的精度可以使用

val LocalDateTime_Min: LocalDateTime
    get() = LocalDateTime.of(1970,1,1,0,0,0)

val LocalDateTime_UTC: LocalDateTime
    get() = LocalDateTime.now(ZoneOffset.UTC)

object LoTimeUtils {

    const val JD_ISO_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"
    /**
     * 将字符串转换为 LocalDateTime 对象
     * @param time 时间戳字符串
     * @param pattern 时间戳字符串的格式，默认：2023-09-26 08:20:31 (yyyy-MM-dd HH:mm:ss)
     */
    fun parse(time: String, pattern: String = JD_ISO_DATE_TIME_PATTERN): LocalDateTime {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(pattern))
    }
}

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

/**
 * 参考 LoTimeUtils.parse(time, pattern)
 * @sample "2023-08-26 08:25:30".toLocalDateTime()
 */
fun String.toLoDateTime(pattern: String = LoTimeUtils.JD_ISO_DATE_TIME_PATTERN): LocalDateTime
    = LocalDateTime.parse(this, DateTimeFormatter.ofPattern(pattern))

/**
 * 根据系统时区转换为 UTC 时间
 */
fun LocalDateTime.utc(): LocalDateTime {
    val zoned = this.atZone(ZoneId.systemDefault())
    val converted = zoned.withZoneSameInstant(ZoneOffset.UTC)
    return converted.toLocalDateTime()
}

/**
 * 北京时间（中国标准时间）减 8 个小时为 UCT 时间
 */
fun LocalDateTime.cst2utc(): LocalDateTime {
    return this.utc(ZONE_OFFSET_CST)
}

/**
 * 将指定时区时间转换为 UTC 时间
 * @param this 要转换成 UTC 的时间
 * @param zone 指定时区
 */
fun LocalDateTime.utc(zone: String): LocalDateTime = when(zone) {
    ZONE_OFFSET_UTC -> this
    ZONE_OFFSET_GMT -> this
    ZONE_OFFSET_CST -> this.minusHours(8)
    ZONE_OFFSET_CET -> this.minusHours(1)
    else -> this.utc() // 获取系统时区转换为 UTC
}