package uk.co.iloveruby.postcode.support

import kotlin.js.Promise
import kotlinx.coroutines.asDeferred

actual suspend fun loadFixtureFile(file: String): String {
    return loadInternal(file).asDeferred().await()
}

interface FS {
    fun readFile(filename: String, encoding: String, callback_function: (Throwable?, Buffer) -> Unit)
}

@JsModule("fs") @JsNonModule
external val fs: dynamic

@JsModule("path") @JsNonModule
external val path: dynamic

@JsNonModule
external class Buffer

val projectRootPath = path.join(js("__dirname"), "..", "..", "..", "..", "..", "lib") as String
val projectBuildPath = path.join(projectRootPath, "build") as String
val fixturePath = path.join(projectBuildPath, "processedResources", "js", "test", "data") as String

@Suppress("UnsafeCastFromDynamic")
fun loadInternal(file: String): Promise<String> {
    val fullFilePath = path.join(fixturePath, file) as String
    console.log(fullFilePath)

    return Promise<String> { resolve, reject ->
        fs.readFile(fullFilePath, "utf8") { err, data ->
            if (err != null) {
                reject(err)
            }

            try {
                resolve(data.toString())
            } catch (e: Error) {
                reject(e)
            }
        }
    }
}
