import org.gradle.api.Project

fun Project.repositories() {
//     repositories {
// //        mavenCentral()
// //        jcenter()
// //        mavenLocal()
//     }
}

fun Project.kotlinx(simpleModuleName: String) = "org.jetbrains.kotlinx:kotlinx-$simpleModuleName"
