plugins {
    `java-library`
    kotlin("jvm") version "2.1.20"
}

group = "ru.vood.kotkin.rules"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test"))

    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")

}

tasks.test {
    useJUnitPlatform()
}

// Опционально: настройка компиляции Java
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

kotlin {
    jvmToolchain(21)
}

// Конфигурация исходных директорий
sourceSets {
    main {
        java {
            setSrcDirs(listOf("src/main/kotlin", "src/main/java"))
        }
        kotlin {
            setSrcDirs(listOf("src/main/kotlin", "src/main/java"))
        }
    }
    test {
        java {
            setSrcDirs(listOf("src/test/kotlin", "src/test/java"))
        }
        kotlin {
            setSrcDirs(listOf("src/test/kotlin", "src/test/java"))
        }
    }
}