plugins {
    kotlin("jvm") version "2.2.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.google.ortools:ortools-java:9.9.3963")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(23)
}