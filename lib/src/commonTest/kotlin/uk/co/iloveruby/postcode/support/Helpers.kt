package uk.co.iloveruby.postcode.support

import io.kotest.data.Row2
import io.kotest.data.row
import kotlin.reflect.KClass
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.serializer

@ImplicitReflectionSerializer
suspend inline fun <A : CharSequence, reified B : Any?> fromFixtureFile(filePath: String): List<Row2<A, B>> =
    buildHelper<A, B>(filePath).buildArguments().toList()

@ImplicitReflectionSerializer
inline fun <A : CharSequence, reified B : Any?> buildHelper(filePath: String): FixtureFileHelper<A, B> =
    FixtureFileHelper(filePath, B::class)

@Suppress("UNCHECKED_CAST")
@ImplicitReflectionSerializer
class FixtureFileHelper<out A : CharSequence, B : Any?>(
    private val fileName: String,
    private val type: KClass<*>
) {

    private val jsonConfig = JsonConfiguration.Stable.copy(prettyPrint = true)
    private val json = Json(jsonConfig)

    suspend fun buildArguments(): Sequence<Row2<A, B>> {
        // TODO: investigate kotlinx.serialization Streaming Parser uk.co.iloveruby.postcode.support

        val (tests) = parseFixtureFile(fileName)
        val seq: Sequence<Row2<A, B>> = tests.asSequence()
            .map { (base, expected) -> row(base as A, expected as B) }

        return seq
    }

    private suspend fun parseFixtureFile(file: String): FixtureFile<*, *> {
        return when (type) {
            Boolean::class -> {
                json.parse<FixtureFile<Boolean, BooleanFixtureAssertion>>(
                    FixtureFile.serializer(
                        serializer<Boolean>(),
                        BooleanFixtureAssertion.serializer()
                    ),
                    loadFixtureFile(file)
                )
            }
            // String::class -> {
            //     json.parse<FixtureFile<String, StringFixtureAssertion>>(
            //         FixtureFile.serializer(
            //             serializer<String>(),
            //             StringFixtureAssertion.serializer()
            //         ),
            //         loadFixtureFile(file)
            //     )
            // }
            String::class -> {
                json.parse<FixtureFile<String?, NullableStringFixtureAssertion>>(
                    FixtureFile.serializer(
                        serializer<String?>(),
                        NullableStringFixtureAssertion.serializer()
                    ),
                    loadFixtureFile(file)
                )
            }
            else -> throw IllegalStateException("OH NO")
        }
    }
}

expect suspend fun loadFixtureFile(file: String): String
