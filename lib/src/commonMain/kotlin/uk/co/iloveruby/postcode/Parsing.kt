package uk.co.iloveruby.postcode

import kotlin.text.RegexOption.IGNORE_CASE

typealias Parser = (String) -> String?

// interface Parser {
//   (postcode: String): String?
// }

private val SPACE_REGEX = Regex("""\s+""") // gi

/**
 * Drop all spaces and uppercase
 *
 * @suppress
 */
private val sanitize: (String) -> String = { s -> s.replace(SPACE_REGEX, "").toUpperCase() }
private val matchOn: (String, Regex) -> MatchResult? = { s, regex ->
    regex.find(sanitize(s), 0)
}

private val incodeRegex = Regex("""\d[a-z]{2}$""", IGNORE_CASE)

/**
 * Returns a normalised copy of a postcode String.
 *
 * The postcode is normalised as follows:
 *
 * - alphabetical characters are converted to uppercase
 * - separator spacing is normalised, with a single space inserted [incode] and [outcode]
 * - extra whitespace is removed, including leading and trailing spaces
 *
 * @param[postcode] a raw (unparsed) postcode.
 * @return a normalised postcode String if the postcode is valid, or null if the postcode is invalid.
 */
val toNormalised: Parser = fun(postcode): String? {
    val outcode = toOutcode(postcode) ?: return null
    val incode = toIncode(postcode) ?: return null
    return "$outcode $incode"
}

/**
 * Returns a correctly formatted outcode for a given postcode.
 *
 * @param[postcode] a raw (unparsed) postcode.
 * @return a correctly formatted outcode if the postcode is valid, or null if the postcode is invalid.
 **/
val toOutcode: Parser = fun(postcode): String? {
    if (!isValid(postcode)) return null
    return sanitize(postcode).replace(incodeRegex, "")
}

/**
 * Returns a correctly formatted incode for a given postcode.
 *
 * @param[postcode] a raw (unparsed) postcode.
 * @return a correctly formatted incode if the postcode is valid, or null if the postcode is invalid.
 */
val toIncode: Parser = fun(postcode): String? {
    if (!isValid(postcode)) return null
    val match = matchOn(postcode, incodeRegex)
    return match?.value
}

private val AREA_REGEX = Regex("""^[a-z]{1,2}""", IGNORE_CASE)

/**
 * Returns a correctly formatted area for a given postcode.
 *
 * @param[postcode] a raw (unparsed) postcode.
 * @return a correctly formatted area if the postcode is valid, or null if the postcode is invalid.
 */
val toArea: Parser = fun(postcode): String? {
    if (!isValid(postcode)) return null
    val match = matchOn(postcode, AREA_REGEX)
    return match?.value
}

/**
 * Returns a correctly formatted sector for a given postcode.
 *
 * @param[postcode] a raw (unparsed) postcode.
 * @return a correctly formatted sector if the postcode is valid, or null if the postcode is invalid.
 */
val toSector: Parser = fun(postcode): String? {
    val outcode = toOutcode(postcode) ?: return null
    val incode = toIncode(postcode) ?: return null
    return "$outcode ${incode[0]}"
}

private val UNIT_REGEX = Regex("""[a-z]{2}$""", IGNORE_CASE)

/**
 * Returns a correctly formatted unit for a given postcode.
 *
 * @param[postcode] a raw (unparsed) postcode.
 * @return a correctly formatted unit if the postcode is valid,, or null if the postcode is invalid.
 */
val toUnit: Parser = fun(postcode): String? {
    if (!isValid(postcode)) return null
    val match = matchOn(postcode, UNIT_REGEX)
    return match?.value
}

private val DISTRICT_SPLIT_REGEX = Regex("""^([a-z]{1,2}\d)([a-z])$""", IGNORE_CASE)

/**
 * Returns a correctly formatted district for a given postcode.
 *
 * @param[postcode] a raw (unparsed) postcode.
 * @return a correctly formatted district if the postcode is valid, or null if the raw postcode is
 * invalid.
 * @sample
 *
 * ```
 * toDistrict("AA9 9AA") // => "AA9"
 * toDistrict("A9A 9AA") // => "A9"
 * ```
 */
val toDistrict: Parser = fun(postcode): String? {
    val outcode = toOutcode(postcode) ?: return null
    val match = DISTRICT_SPLIT_REGEX.find(outcode, 0) ?: return outcode
    return match.groupValues[1]
}

/**
 * Returns a correctly formatted subdistrict for a given postcode.
 *
 * @param[postcode] a raw (unparsed) postcode.
 * @return a correctly formatted subdistrict, or null if the postcode is valid but no subdistrict is
 * available, or if the postcode is invalid.
 * @sample
 *
 * ```
 * toSubDistrict("AA9A 9AA") // => "AA9A"
 * toSubDistrict("A9A 9AA") // => "A9A"
 * toSubDistrict("AA9 9AA") // => null
 * toSubDistrict("A9 9AA") // => null
 * ```
 */
val toSubDistrict: Parser = fun(postcode): String? {
    val outcode = toOutcode(postcode) ?: return null
    DISTRICT_SPLIT_REGEX.find(outcode, 0) ?: return null
    return outcode
}
