import org.gradle.internal.configuration.problems.taskPathFrom

/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.10.2/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    id("java")
    id("war")
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.0"
    id("checkstyle")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.junit)
    implementation(libs.guava)
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.eclipse.persistence:org.eclipse.persistence.jpa:3.0.2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") {
        exclude("org.hibernate", "hibernate-core")
        exclude("org.hibernate.common", "common-annotations")
    }
    implementation("org.springframework:spring-context:6.1.14")
    implementation("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-security")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

checkstyle {
    toolVersion = "10.20.1"
    configFile = file("config/checkstyle/checkstyle.xml")
}

