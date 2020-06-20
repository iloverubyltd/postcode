package uk.co.iloveruby.postcode

import kotlin.text.RegexOption.IGNORE_CASE

typealias Validator = (String) -> Boolean

// interface Validator {
//   (input: String): Boolean
// }

internal val validOutcodeRegex = Regex("""^[a-z]{1,2}\d[a-z\d]?""", IGNORE_CASE)
private val VALIDATION_REGEX = Regex("""^[a-z]{1,2}\d[a-z\d]?\s*\d[a-z]{2}$""", IGNORE_CASE)

/**
 * Detects a "valid" postcode
 * - Starts and ends on a non-space character
 * - Any length of intervening space is allowed
 * - Must conform to one of following schemas:
 *  - AA1A 1AA
 *  - A1A 1AA
 *  - A1 1AA
 *  - A99 9AA
 *  - AA9 9AA
 *  - AA99 9AA
 */
val isValid: Validator = { postcode ->
    postcode.matches(VALIDATION_REGEX)
}
