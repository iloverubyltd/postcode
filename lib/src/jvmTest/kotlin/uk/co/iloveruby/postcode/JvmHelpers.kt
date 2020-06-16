package uk.co.iloveruby.postcode

import java.io.BufferedReader
import java.io.InputStreamReader

@Throws(IllegalStateException::class)
actual fun loadFixtureFile(file: String): String {
    val inputStream = PostcodeUnitTest::class.java
        .getResourceAsStream("/data/$file")
    checkNotNull(inputStream, { "Specified fixture file ($file) does not exist." })

    return StringBuilder().apply {
        var line: String?
        BufferedReader(InputStreamReader(inputStream, Charsets.UTF_8)).use { reader ->
            while (reader.readLine().also { line = it } != null) {
                append(line)
            }
        }
    }.toString()
}
