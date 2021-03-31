val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val kodein_version = "6.1.0"


plugins {
    application
    kotlin("jvm") version "1.4.21"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.5.0-M1"
}

group = "com.fdcisl.sawaribackend"
version = "0.0.1"

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenLocal()
    jcenter()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-sessions:$ktor_version")
    implementation("io.ktor:ktor-server-host-common:$ktor_version")
    implementation("io.ktor:ktor-gson:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-serialization:$ktor_version")
    implementation("io.ktor:ktor-client-serialization:$ktor_version")
    implementation("io.ktor:ktor-client-serialization-jvm:$ktor_version")

//    // firebase admin sdk
//    implementation("com.google.firebase:firebase-admin:7.1.0")

    // kodein di
    implementation("org.kodein.di:kodein-di-generic-jvm:$kodein_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:0.30.1-eap13")

    // kmongo coroutine based client
    implementation("org.litote.kmongo:kmongo-coroutine:4.1.2")

    // Open API Doc generator
    implementation("com.github.papsign:Ktor-OpenAPI-Generator:-SNAPSHOT")

    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")
