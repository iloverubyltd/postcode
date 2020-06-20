@file:Suppress("NOTHING_TO_INLINE", "unused")

package uk.co.iloveruby.postcode.support

interface PrintF {
    fun sprintf(format: String, vararg args: Any?): String
    fun vsprintf(format: String, args: Array<Any?>): String
}

@JsModule("printj")
@JsNonModule()
external val PRINTF: dynamic // PrintF

// @JsModule("printj.sprintf") @JsNonModule
// external val sprintf: (format: String, args: Any?) -> String

/**
 * Uses this string as a format string and returns a string obtained by substituting the specified arguments,
 * using the default locale.
 */
actual inline fun String.format(vararg args: Any?): String = PRINTF.sprintf(this, args)

/**
 * Uses the provided [format] as a format string and returns a string obtained by substituting the specified arguments,
 * using the default locale.
 */
actual inline fun String.Companion.format(
    format: String,
    vararg args: Any?
): String = PRINTF.sprintf(format, args)
