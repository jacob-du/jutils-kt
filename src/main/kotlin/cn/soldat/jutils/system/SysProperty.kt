package cn.soldat.jutils.system

object SysProperty {
    val OS_NAME: String = System.getProperty("os.name")             // 操作系统名称       Mac OS X
    val OS_VERSION: String = System.getProperty("os.version")       // 操作系统版本       10.16
    val TEMP_DIR: String = System.getProperty("java.io.tmpdir")     // 默认的临时文件目录  /var/folders/7_/jdpy4d1n6hn_nf5srff2_bvr0000gn/T/
    val SEPARATOR: String = System.getProperty("file.separator")    // 文件分隔符         /
    val USER_NAME: String = System.getProperty("user.name")         // 用户名            dujinbo
    val USER_HOME: String = System.getProperty("user.home")         // 用户主目录         /Users/dujinbo
    val USER_DIR: String = System.getProperty("user.dir")           // 用户当前的工作目录  /Users/dujinbo/CodeLab/SourceCode/JetBrains/IntelliJ/DocumentServer
}