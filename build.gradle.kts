plugins {
    kotlin("jvm") version "1.8.22"
    `maven-publish`
}

group = "cn.soldat"
//version = "0.0.1"

repositories {
    maven(url = "https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    // okhttp
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "cn.soldat"
            artifactId = "jacob-utils"
//            version = "1.0.5"

            from(components["java"])
        }
    }
}