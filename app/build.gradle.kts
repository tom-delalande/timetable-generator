plugins {
    id("org.jetbrains.kotlin.jvm") version "2.2.10"
    kotlin("plugin.serialization") version "2.2.10"
    application
}

application {
    mainClass.set("app.MainKt")
    applicationName = "app"
}

tasks.distZip {
    enabled = false
}

tasks.distTar {
    archiveFileName.set("app-bundle.${archiveExtension.get()}")
}

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

val ktorVersion = "3.1.2"
dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-server-html-builder:$ktorVersion")
    implementation("io.ktor:ktor-server-websockets:$ktorVersion")
    implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
    implementation("org.apache.commons:commons-csv:1.14.0")
    implementation("com.stripe:stripe-java:29.0.0")

    implementation("org.slf4j:slf4j-api:1.7.7")
    implementation("ch.qos.logback:logback-classic:1.5.13")
    implementation("ch.qos.logback:logback-core:1.5.13")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.5")
    implementation("com.logtail:logback-logtail:0.3.3")


    // Data
    implementation("org.flywaydb:flyway-core:11.3.4")
    implementation("p6spy:p6spy:3.9.1")
    implementation("org.xerial:sqlite-jdbc:3.49.1.0")
    implementation("org.postgresql:postgresql:42.7.5")
}
