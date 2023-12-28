[![](https://jitpack.io/v/cn.soldat.gitlab.jacob/jacob-utils-kt.svg)](https://jitpack.io/#cn.soldat.gitlab.jacob/jacob-utils-kt)

> 依赖`build.gradle.kts`
```kotlin
repositories {
    maven(url = "https://jitpack.io")
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("cn.soldat.gitlab.jacob:jacob-utils-kt:VERSION_TAG")
}
```

> 示例
```kotlin
import cn.soldat.jutils.extension.toDateTimeString
import cn.soldat.jutils.extension.toUtc
import java.time.LocalDateTime

fun main(args: Array<String>) {
    println(LocalDateTime.now().toUtc().toDateTimeString())
}
```