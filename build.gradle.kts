import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties

plugins {
    id("java")
    id("maven-publish")
    id("com.gradleup.shadow") version("9.0.0-beta8")
    id("checkstyle")
}

group = "me.chaoticva"

afterEvaluate {
    version = SimpleDateFormat("yy.M.d-HHmmss").format(Date())
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.12.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
    testImplementation("org.mockito:mockito-core:5.4.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.4.0")
}

checkstyle {
    toolVersion = "10.21.2"
    configFile = file("${rootProject.projectDir}/config/checkstyle/checkstyle.xml")
}

tasks {
    shadowJar {
        archiveBaseName = name
        archiveClassifier = ""
        archiveVersion = version.toString()
    }

    test {
        useJUnitPlatform()
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier = "sources"
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            artifact(sourcesJar.get())
            artifact(tasks.shadowJar)
        }
    }

    repositories {
        maven {
            name = "repsy"
            url = uri("https://repo.repsy.io/mvn/${System.getenv("REPSY_USERNAME")}/releases")
            credentials {
                username = System.getenv("REPSY_USERNAME")
                password = System.getenv("REPSY_PASSWORD")
            }
        }
    }
}