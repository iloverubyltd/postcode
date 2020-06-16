@file:OptIn(ImplicitReflectionSerializer::class, kotlinx.serialization.UnstableDefault::class)

package uk.co.iloveruby.postcode

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.serializer

private val json = Json(JsonConfiguration.Default)

interface FixtureAssertion<out O> {
    val base: String
    val expected: O

    operator fun component1(): String
    operator fun component2(): O
}

@Serializable
data class NullableStringFixtureAssertion(
    override val base: String,
    override val expected: String?
) : FixtureAssertion<String?>

@Serializable
data class StringFixtureAssertion(override val base: String, override val expected: String) :
    FixtureAssertion<String>

@Serializable
data class BooleanFixtureAssertion(override val base: String, override val expected: Boolean) :
    FixtureAssertion<Boolean>

@Serializable
data class FixtureFile<out O, T : FixtureAssertion<O>>(val tests: List<T>)

expect fun loadFixtureFile(file: String): String

fun loadNullableStringFixtures(file: String): FixtureFile<String?, NullableStringFixtureAssertion> {
    return json.parse<FixtureFile<String?, NullableStringFixtureAssertion>>(
        FixtureFile.serializer(serializer<String?>(), NullableStringFixtureAssertion.serializer()),
        loadFixtureFile(file)
    )
}

@ImplicitReflectionSerializer
fun loadStringFixtures(file: String): FixtureFile<String, StringFixtureAssertion> {
    return json.parse<FixtureFile<String, StringFixtureAssertion>>(
        FixtureFile.serializer(serializer<String>(), StringFixtureAssertion.serializer()),
        loadFixtureFile(file)
    )
}

@ImplicitReflectionSerializer
fun loadBooleanFixtures(file: String): FixtureFile<Boolean, BooleanFixtureAssertion> {
    return json.parse<FixtureFile<Boolean, BooleanFixtureAssertion>>(
        FixtureFile.serializer(serializer<Boolean>(), BooleanFixtureAssertion.serializer()),
        loadFixtureFile(file)
    )
}
