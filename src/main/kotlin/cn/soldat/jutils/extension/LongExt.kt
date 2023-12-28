package cn.soldat.jutils.extension

import java.text.DecimalFormat

/**
 * 文件：将 Long 类型转换为可读的大小
 */
fun Long.toReadableSize(): String{
    if(this == 0L) return "0B"
    var ms = this / 1024.0
    var n = 1
    while (ms > 1024){
        ms /= 1024
        ++n
    }
    val suffix = arrayOf('K','M','G','T','P','E','Z','Y')
    if(ms == 1024.0) return "1${suffix[n - 1]}B"
    val result = DecimalFormat("#.##").format(ms)
    return "${result}${suffix[n - 1]}B"
}