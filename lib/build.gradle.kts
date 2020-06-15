plugins {
    kotlin("multiplatform") version "1.3.72"
    id("maven-publish")
//    kotlin("jvm") version "1.3.72"
}

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations {
            val main by getting {
                kotlinOptions { jvmTarget = "1.8" }
                compileKotlinTask
                output
            }
        }
    }
    js()
    // mingwX64(name = "mingw")
    // macosX64(name = "macos")

    sourceSets {
        val commonMain by getting {

            languageSettings.apply {
                languageVersion = "1.3"
                apiVersion = "1.3"
            }

            dependencies {
                // testImplementation(kotlin("test:1.3.60"))
            }
        }

        val jvmMain by getting {
            dependencies {
                // testImplementation(kotlin("test-junit:1.3.60"))
            }
        }
    }
}

dependencies {
    "commonMainImplementation"(kotlin("stdlib-common"))
    "commonTestImplementation"(kotlin("test:1.3.60"))

    "jvmMainImplementation"(kotlin("stdlib-jdk8"))
    "jvmTestImplementation"(kotlin("test-junit:1.3.60"))
}
