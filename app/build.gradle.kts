plugins {
    id("java")
    id ("org.sonarqube") version "6.2.0.5505"
    id("jacoco")
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.25.3")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

sonar {
    properties {
        property("sonar.projectKey", "vvvilkha_java-project-78")
        property("sonar.organization", "vvvilkha")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

jacoco {
        toolVersion = "0.8.10"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true) // обязательно для Sonar
        html.required.set(false)
        csv.required.set(false)
    }
}

