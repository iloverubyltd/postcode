@file:OptIn(ImplicitReflectionSerializer::class, UnstableDefault::class)

package uk.co.iloveruby.postcode.support

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UnstableDefault

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
