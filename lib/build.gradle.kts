plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.3.72"
    `maven-publish`
}

val kotlinx_coroutines_version = "1.3.7"
val kotlinx_io_version = "0.1.16"
val kotlinx_serialization_version = "0.20.0"
val junit5_version = "5.6.0"

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
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                // implementation(kotlinx("io:$kotlinx_io_version"))
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
                implementation(kotlin("test"))
                implementation(kotlin("test-junit5"))
                implementation("org.junit.jupiter:junit-jupiter-api:$junit5_version")
                implementation("org.junit.jupiter:junit-jupiter-engine:$junit5_version")
                // implementation(kotlinx("io-jvm:$kotlinx_io_version"))
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
                implementation(kotlin("test-js"))
                implementation(kotlinx("serialization-runtime-js:$kotlinx_serialization_version"))
            }
        }
    }
}

dependencies {
}
