import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.3.72"))
        classpath("com.android.tools.build:gradle:4.0.0")
    }
}

plugins {
    id("com.diffplug.gradle.spotless") version ("3.26.1")
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url = "https://maven.google.com")
        maven(url = "https://plugins.gradle.org/m2/")
    }

    apply(plugin = "com.diffplug.gradle.spotless")

    spotless {
        kotlin {
            target(fileTree(".") {
                include("**/*.kt")
                exclude("**/.gradle/**")
                exclude("**/build/**")
            })

            // see https://github.com/shyiko/ktlint#standard-rules
            ktlint()
        }

        kotlinGradle {
            target(
                "*.gradle.kts", "**/*.gradle.kts"
            )

            ktlint()
        }

        freshmark {
            target("README.md")
        }
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }
    }
}

subprojects {
}
