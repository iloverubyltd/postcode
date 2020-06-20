@file:Suppress("NOTHING_TO_INLINE", "unused")

package uk.co.iloveruby.postcode.support

actual inline fun String.format(vararg args: Any?): String = java.lang.String.format(this, *args)

actual inline fun String.Companion.format(format: String, vararg args: Any?): String = java.lang.String.format(format, *args)
