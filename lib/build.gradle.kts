plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.3.72"
    `maven-publish`
    id("org.jetbrains.dokka") version "0.10.1"
}

val kotlinx_coroutines_version = "1.3.7"
val kotlinx_io_version = "0.1.16"
val kotlinx_serialization_version = "0.20.0"
val junit5_version = "5.6.0"
val kotest_version = "4.0.7"

kotlin {
    jvm {
        withJava()

        compilations {
            val main by getting {
                kotlinOptions {
                    jvmTarget = "1.8"
                }
            }

            val test by getting {
                kotlinOptions {
                    jvmTarget = "1.8"
                }
            }
        }
    }
    js()
    js {
        nodejs {
            compilations {
                val main by getting {
                    kotlinOptions {
                        moduleKind = "commonjs"
                    }
                }
                val test by getting {
                    kotlinOptions {
                        moduleKind = "commonjs"
                    }
                }
            }

            runTask {
            }

            testTask {
                useNodeJs()
            }
        }
    }
    // mingwX64(name = "mingw")
    // macosX64(name = "macos")

    sourceSets {
        all {
            languageSettings.apply {
                languageVersion = "1.3"
                apiVersion = "1.3"
                useExperimentalAnnotation("kotlin.RequiresOptIn")
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation("io.kotest:kotest-core:$kotest_version")
                implementation("io.kotest:kotest-assertions-core:$kotest_version")
                // implementation("io.kotest:kotest-runner-console:$kotest_version")
                implementation(kotlinx("coroutines-core-common:$kotlinx_coroutines_version"))
                implementation(kotlinx("serialization-runtime-common:$kotlinx_serialization_version"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation("io.kotest:kotest-runner-junit5:$kotest_version")
                implementation("io.kotest:kotest-assertions-core-jvm:$kotest_version")
                implementation("io.kotest:kotest-property-jvm:$kotest_version")
                implementation(kotlinx("coroutines-core:$kotlinx_coroutines_version"))
                implementation(kotlinx("serialization-runtime:$kotlinx_serialization_version"))
            }

            tasks {
                named<Test>("jvmTest") {
                    useJUnitPlatform()
                }
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(npm("printj", "1.2.2"))
                implementation("io.kotest:kotest-assertions-core-js:$kotest_version")
                implementation("io.kotest:kotest-property-js:$kotest_version")
                implementation(kotlinx("coroutines-core-js:$kotlinx_coroutines_version"))
                implementation(kotlinx("serialization-runtime-js:$kotlinx_serialization_version"))
            }
        }
    }
}

tasks.dokka {
    outputFormat = "html"
    outputDirectory = "$buildDir/dokka"
}

dependencies {
}
