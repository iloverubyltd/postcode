package uk.co.iloveruby.postcode

import kotlin.text.RegexOption.IGNORE_CASE

typealias Parser = (String) -> String?

// interface Parser {
//   (postcode: String): String?
// }

private val SPACE_REGEX = Regex("""\s+""") // gi

/**
 * Drop all spaces and uppercase
 */
private val sanitize: (String) -> String = { s -> s.replace(SPACE_REGEX, "").toUpperCase() }
private val matchOn: (String, Regex) -> MatchResult? = { s, regex ->
    regex.find(sanitize(s), 0)
}

private val incodeRegex = Regex("""\d[a-z]{2}$""", IGNORE_CASE)

/**
 * Returns a normalised postcode String (i.e. uppercased and properly spaced)
 *
 * Returns null if invalid postcode
 */
val toNormalised: Parser = fun(postcode): String? {
    val outcode = toOutcode(postcode) ?: return null
    val incode = toIncode(postcode) ?: return null
    return "$outcode $incode"
}

/**
 * Returns a correctly formatted outcode given a postcode
 *
 * Returns null if invalid postcode
 */
val toOutcode: Parser = fun(postcode): String? {
    if (!isValid(postcode)) return null
    return sanitize(postcode).replace(incodeRegex, "")
}

/**
 * Returns a correctly formatted incode given a postcode
 *
 * Returns null if invalid postcode
 */
val toIncode: Parser = fun(postcode): String? {
    if (!isValid(postcode)) return null
    val match = matchOn(postcode, incodeRegex)
    return match?.value
}

private val AREA_REGEX = Regex("""^[a-z]{1,2}""", IGNORE_CASE)

/**
 * Returns a correctly formatted area given a postcode
 *
 * Returns null if invalid postcode
 */
val toArea: Parser = fun(postcode): String? {
    if (!isValid(postcode)) return null
    val match = matchOn(postcode, AREA_REGEX)
    return match?.value
}

/**
 * Returns a correctly formatted sector given a postcode
 *
 * Returns null if invalid postcode
 */
val toSector: Parser = fun(postcode): String? {
    val outcode = toOutcode(postcode) ?: return null
    val incode = toIncode(postcode) ?: return null
    return "$outcode ${incode[0]}"
}

private val UNIT_REGEX = Regex("""[a-z]{2}$""", IGNORE_CASE)

/**
 * Returns a correctly formatted unit given a postcode
 *
 * Returns null if invalid postcode
 */
val toUnit: Parser = fun(postcode): String? {
    if (!isValid(postcode)) return null
    val match = matchOn(postcode, UNIT_REGEX)
    return match?.value
}

private val DISTRICT_SPLIT_REGEX = Regex("""^([a-z]{1,2}\d)([a-z])$""", IGNORE_CASE)

/**
 * Returns a correctly formatted district given a postcode
 *
 * Returns null if invalid postcode
 *
 * @example
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
 * Returns a correctly formatted subdistrict given a postcode
 *
 * Returns null if no subdistrict is available on valid postcode
 * Returns null if invalid postcode
 *
 * @example
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
