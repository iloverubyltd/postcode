pluginManagement {
    repositories {
        mavenCentral()
        jcenter()
        maven { url = uri("https://plugins.gradle.org/m2/") }
        mavenLocal()
    }
}

include(":lib")
