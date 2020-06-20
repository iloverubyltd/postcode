package uk.co.iloveruby.postcode.support

import java.io.BufferedReader
import java.io.InputStreamReader
import kotlinx.serialization.ImplicitReflectionSerializer
import uk.co.iloveruby.postcode.PostcodeUnitTest

@ImplicitReflectionSerializer
@Throws(IllegalStateException::class)
actual suspend fun loadFixtureFile(file: String): String {
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
