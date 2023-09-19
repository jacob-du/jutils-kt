package cn.soldat.jbutils.file

import java.io.File
import java.text.DecimalFormat

object FileUtils {

    fun mkdirs(path: String): Boolean {
        val file = File(path)
        if (file.exists()) return true
        return file.mkdirs()
    }

    fun touch(path: String, fileName: String, suffix: String = ""): File? {
        val file = File(path + fileName + suffix)
        if (file.exists() && file.isFile) return file
        if (!file.exists()) file.createNewFile()
        if (file.isDirectory) return null
        return file
    }
}

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