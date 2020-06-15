val testVar by extra("testVal")
// import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    val rootTestVar by extra("rootTestVal")
    repositories {
        google()
        jcenter()
        maven(url = "https://jitpack.io")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    }

    dependencies {
    }
}

plugins {
    id("com.diffplug.gradle.spotless") version ("3.26.1")
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://jitpack.io")
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

    // tasks.withType<KotlinCompile> {
    //     kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    // }
}

subprojects {
}
