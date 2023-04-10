val ktormVersion: String by project
val datetimeVersion: String by project
val exposedVersion: String by project

plugins {
    kotlin("jvm")
    id("io.ktor.plugin")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.5.0")

//    implementation("org.ktorm:ktorm-core:$ktormVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
    api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
}