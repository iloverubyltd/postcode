package uk.co.iloveruby.postcode.support

/**
 * Uses this string as a format string and returns a string obtained by substituting the specified arguments,
 * using the default locale.
 */
expect inline fun String.format(vararg args: Any?): String

/**
 * Uses the provided [format] as a format string and returns a string obtained by substituting the specified arguments,
 * using the default locale.
 */
expect inline fun String.Companion.format(format: String, vararg args: Any?): String
