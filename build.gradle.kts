plugins {
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "ru.xkr1se.Mutator"
    }
}