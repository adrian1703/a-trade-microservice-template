plugins {
    kotlin("jvm") version "2.2.10"
    kotlin("plugin.allopen") version "2.2.10"
}

repositories {
    mavenCentral()
    mavenLocal()
}


dependencies {
    implementation("adrian.kuhn:a-trade-microservice-runtime-api:0.0.1")
    implementation("jakarta.ws.rs:jakarta.ws.rs-api:3.1.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
}

group = "adrian.kuhn"
version = "0.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
    jvmArgs("--add-opens", "java.base/java.lang=ALL-UNNAMED")
}
allOpen {
    annotation("jakarta.ws.rs.Path")
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("jakarta.persistence.Entity")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
        javaParameters = true
    }
}