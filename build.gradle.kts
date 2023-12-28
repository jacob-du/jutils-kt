plugins {
    kotlin("jvm") version "1.8.21"
    `maven-publish`
}

group = "cn.soldat"

repositories {
    maven(url = "https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    // okhttp
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    // Json Web Token
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-gson:0.11.5")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "cn.soldat"
            artifactId = "jutils-kt"
//            version = "1.0.5"

            from(components["java"])
        }
    }
}