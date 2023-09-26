package cn.soldat.jbutils.file

import java.io.File
import java.text.DecimalFormat

object FileUtils {

    /**
     * 创建文件夹
     */
    fun mkdirs(path: String): Boolean {
        val file = File(path)
        if (file.exists()) return true
        return file.mkdirs()
    }

    /**
     * 创建文件
     */
    fun touch(path: String, fileName: String, suffix: String = ""): File? {
        val file = File(path + fileName + suffix)
        if (file.exists() && file.isFile) return file
        if (!file.exists()) file.createNewFile()
        if (file.isDirectory) return null
        return file
    }

    /**
     * 移动文件
     */
    fun move(file: File, path: String): Boolean {
        val dir = File(path)
        if (!dir.exists()) dir.mkdirs()
        if (!dir.isDirectory) return false
        file.copyTo(file, false)
        file.delete()
        return true
    }

    /**
     * 复制文件
     */
    fun copy(file: File, path: String): Boolean {
        val dir = File(path)
        if (!dir.exists()) dir.mkdirs()
        if (!dir.isDirectory) return false
        file.copyTo(file, false)
        return true
    }

    /**
     * 获取目录下的文件
     */
    fun files(path: String): List<File> {
        val file = File(path)
        if (!file.exists()) throw RuntimeException("$path 不存在")
        if (file.isFile) return listOf(file)
        val files = file.listFiles() ?: return emptyList()
        return files.toList()
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