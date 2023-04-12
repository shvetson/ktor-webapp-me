val ktormVersion: String by project
val datetimeVersion: String by project
val exposedVersion: String by project
val kotlinVersionSerialization: String by project

plugins {
    kotlin("jvm")
    id("io.ktor.plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinVersionSerialization")

//    implementation("org.ktorm:ktorm-core:$ktormVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
    api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    implementation("org.jetbrains.exposed:exposed-java-time:0.40.1")
}