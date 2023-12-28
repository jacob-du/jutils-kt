package cn.soldat.jutils.system

import java.util.*

/**
 * 封装项目属性配置文件实现类似 springboot 项目中 application.yml 的功能
 * 使用方式：val value = MyProperties.getString(key = "xxxxx")
 */
object MyProperties {

    private class PropertyFileNotFound(msg: String): Exception(msg)

    /**
     * 配置文件的文件夹名称 config
     */
    private const val PROJ_CONFIG_DIR = "config"

    /**
     * 配置文件默认名称
     */
    private const val PROJ_CONFIG_FILE = "/config/proj.properties"

    /**
     * 配置文件名称前缀 proj-xxx
     */
    private const val PROJ_CONFIG_PREFIX = "proj"

    /**
     * 配置文件后缀 proj-xxx.properties
     */
    private const val PROJ_CONFIG_SUFFIX = "properties"

    /**
     * 默认配置文件中配置文件属性：实际使用的配置文件 dev、prod、test ......
     */
    private const val PROJ_ENV_CONFIG_KEY = "proj.env.profile"

    private val defConfig = Properties() // 默认配置文件 判断配置文件加载项
    private val proConfig = Properties() // 项目实际配置文件
    private var configFile: String = PROJ_CONFIG_FILE

    init {
        try {
            defConfig.load(MyProperties::class.java.getResourceAsStream(PROJ_CONFIG_FILE))
            defConfig.getProperty(PROJ_ENV_CONFIG_KEY)?.let { env ->
                configFile = "/$PROJ_CONFIG_DIR/$PROJ_CONFIG_PREFIX-$env.$PROJ_CONFIG_SUFFIX"
                val ras = MyProperties::class.java.getResourceAsStream(configFile)
                    ?: throw PropertyFileNotFound("[prop|error]: 配置文件(${configFile})不存在")
                proConfig.load(ras)
            } ?: proConfig.load(MyProperties::class.java.getResourceAsStream(PROJ_CONFIG_FILE))
        } catch (e: NullPointerException) {
            error("[prop|error]: 配置文件(${PROJ_CONFIG_FILE})不存在")
        } catch (e: PropertyFileNotFound) {
            error(e.localizedMessage)
        }
    }

    /**
     * 获取属性的 String 值
     */
    fun getString(key: String): String {
        return proConfig.getProperty(key) ?: throw RuntimeException("[prop|error]: 配置文件($configFile)中无法找到此属性（$key）")
    }

    /**
     * 获取属性的 Int 值
     */
    fun getInt(key: String): Int {
        try {
            val value = proConfig.getProperty(key) ?: throw RuntimeException("[prop|error]: 配置文件($configFile)中无法找到此属性（$key）")
            return value.toInt()
        } catch (e: ClassCastException) {
            e.printStackTrace()
            throw RuntimeException("[prop|error]: 配置文件($configFile)中属性($key)的值不属于Int类型")
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            throw RuntimeException("[prop|error]: 配置文件($configFile)中属性($key)的值不属于Int类型")
        }
    }

    /**
     * 获取属性的 Long 值
     */
    fun getLong(key: String): Long {
        try {
            val value = proConfig.getProperty(key) ?: throw RuntimeException("[prop|error]: 配置文件($configFile)中无法找到此属性（$key）")
            return value.toLong()
        } catch (e: ClassCastException) {
            e.printStackTrace()
            throw RuntimeException("[prop|error]: 配置文件($configFile)中属性($key)的值不属于Long类型")
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            throw RuntimeException("[prop|error]: 配置文件($configFile)中属性($key)的值不属于Long类型")
        }
    }

    /**
     * 获取属性的 Float 值
     */
    fun getFloat(key: String): Float {
        try {
            val value = proConfig.getProperty(key) ?: throw RuntimeException("[prop|error]: 配置文件($configFile)中无法找到此属性（$key）")
            return value.toFloat()
        } catch (e: ClassCastException) {
            e.printStackTrace()
            throw RuntimeException("[prop|error]: 配置文件($configFile)中属性($key)的值不属于Float类型")
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            throw RuntimeException("[prop|error]: 配置文件($configFile)中属性($key)的值不属于Float类型")
        }
    }

    /**
     * 获取属性的 Double 值
     */
    fun getDouble(key: String): Double {
        try {
            val value = proConfig.getProperty(key) ?: throw RuntimeException("[prop|error]: 配置文件($configFile)中无法找到此属性（$key）")
            return value.toDouble()
        } catch (e: ClassCastException) {
            e.printStackTrace()
            throw RuntimeException("[prop|error]: 配置文件($configFile)中属性($key)的值不属于Double类型")
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            throw RuntimeException("[prop|error]: 配置文件($configFile)中属性($key)的值不属于Double类型")
        }
    }

    /**
     * 获取属性的 Char 值
     */
    fun getChar(key: String): Char {
        try {
            val value = proConfig.getProperty(key) ?: throw RuntimeException("[prop|error]: 配置文件($configFile)中无法找到此属性（$key）")
            return value.toCharArray()[0]
        } catch (e: ClassCastException) {
            e.printStackTrace()
            throw RuntimeException("[prop|error]: 配置文件($configFile)中属性($key)的值不属于Char类型")
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            throw RuntimeException("[prop|error]: 配置文件($configFile)中属性($key)的值不属于Char类型")
        }
    }

    /**
     * 获取属性的 Boolean 值
     */
    fun getBoolean(key: String): Boolean {
        try {
            val value = proConfig.getProperty(key) ?: throw RuntimeException("[prop|error]: 配置文件($configFile)中无法找到此属性（$key）")
            return value.toBooleanStrict()
        } catch (e: ClassCastException) {
            e.printStackTrace()
            throw RuntimeException("[prop|error]: 配置文件($configFile)中属性($key)的值不属于Boolean类型")
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            throw RuntimeException("[prop|error]: 配置文件($configFile)中属性($key)的值不属于Boolean类型")
        }
    }
}