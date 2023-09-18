plugins {
    kotlin("jvm") version "1.8.22"
    `maven-publish`
}

group = "cn.soldat"
//version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
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