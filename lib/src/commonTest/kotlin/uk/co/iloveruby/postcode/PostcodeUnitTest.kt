package uk.co.iloveruby.postcode

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.serialization.ImplicitReflectionSerializer

@OptIn(ImplicitReflectionSerializer::class)
class PostcodeUnitTest {

    fun <T> testMethod(
        tests: List<FixtureAssertion<T>>,
        method: (Postcode) -> T // KProperty1 or KFunction1
    ) {
        tests.forEach { (base: String, expected: T) ->
            val p = Postcode.newInstance(base)
            val result = method.invoke(p)
            assertEquals(result, expected)
        }
    }
    fun create(raw: String) = Postcode.create(raw)

    @Test
    @JsName("postcodeValid")
    fun `Postcode#valid - should return true for postcodes that look correct`() { // ASYNC
        val (tests) = loadBooleanFixtures("validation.json")
        tests.forEach { (base, expected) ->
            val p = Postcode.newInstance(base)
            assertEquals(p.valid, expected)
        }
    }

    @Test
    @JsName("postcodeNormalisation1")
    fun `postcode normalisation - should correctly normalise postcodes`() { // ASYNC
        val (tests) = loadNullableStringFixtures("normalisation.json")
        testMethod<String?>(method = Postcode::normalise, tests = tests)
    }

    @Test
    @JsName("postcodeNormalisation2")
    fun `postcode normalisation - should return null if invalid postcode`() {
        assertNull(Postcode.create("Definitely bogus").normalise())
    }

    @Test
    @JsName("postcodeValidOutcode1")
    fun `Postcode#validOutcode - should return true for valid outcodes`() { // ASYNC
        val (tests) = loadNullableStringFixtures("outcodes.json")
        tests.forEach { (expected) ->
            assertTrue(Postcode.validOutcode(expected))
        }
    }

    @Test
    @JsName("postcodeValidOutcode2")
    fun `Postcode#validOutcode - should return false for invalid outcode`() {
        val invalidOutcodes = listOf("BOGUS", "Hello there", "12345")
        invalidOutcodes.forEach { code ->
            assertFalse(Postcode.validOutcode(code))
        }
    }

    @Test
    @JsName("incodeParsing1")
    fun `incode parsing - should correctly parse incodes`() { // ASYNC
        val (tests) = loadNullableStringFixtures("incodes.json")
        testMethod<String?>(method = Postcode::incode, tests = tests)
    }

    @Test
    @JsName("incodeParsing2")
    fun `incode parsing - should return null if invalid postcode`() {
        assertNull(Postcode.create("Definitely bogus").incode)
    }

    @Test
    @JsName("outcodeParsing1")
    fun `outcode parsing - should correctly parse outcodes`() { // ASYNC
        val (tests) = loadNullableStringFixtures("outcodes.json")
        testMethod<String?>(method = Postcode::outcode, tests = tests)
    }

    @Test
    @JsName("outcodeParsing2")
    fun `outcode parsing - should return null if invalid postcode`() {
        assertNull(Postcode.create("Definitely bogus").outcode)
    }

    @Test
    @JsName("areaParsing1")
    fun `area parsing - should correctly parse areas`() { // ASYNC
        val (tests) = loadNullableStringFixtures("areas.json")
        testMethod<String?>(method = Postcode::area, tests = tests)
    }

    @Test
    @JsName("areaParsing2")
    fun `area parsing - should return null if invalid postcode`() {
        assertNull(Postcode.create("Definitely bogus").area)
    }

    @Test
    @JsName("districtParsing1")
    fun `district parsing - should correctly parse districts`() { // ASYNC
        val (tests) = loadNullableStringFixtures("districts.json")
        testMethod<String?>(method = Postcode::district, tests = tests)
    }

    @Test
    @JsName("districtParsing2")
    fun `district parsing - should return null if invalid postcode`() {
        assertNull(Postcode.create("Definitely bogus").district)
    }

    @Test
    @JsName("subdistrictParsing1")
    fun `subdistrict parsing - should correctly parse sub-districts`() { // ASYNC
        val (tests) = loadNullableStringFixtures("sub-districts.json")
        testMethod<String?>(method = Postcode::subDistrict, tests = tests)
    }

    @Test
    fun `subDistrict parsing - should return null if invalid postcode`() {
        assertNull(Postcode.create("Definitely bogus").subDistrict)
    }

    @Test
    @JsName("sectorParsing1")
    fun `sector parsing - should correctly parse sectors`() { // ASYNC
        val (tests) = loadNullableStringFixtures("sectors.json")
        testMethod<String?>(method = Postcode::sector, tests = tests)
    }

    @Test
    @JsName("sectorParsing2")
    fun `sector parsing - should return null if invalid postcode`() {
        assertNull(Postcode.create("Definitely bogus").sector)
    }

    @Test
    @JsName("unitParsing1")
    fun `unit parsing - should correctly parse units`() { // ASYNC
        val (tests) = loadNullableStringFixtures("units.json")
        testMethod<String?>(method = Postcode::unit, tests = tests)
    }

    @Test
    @JsName("unitParsing2")
    fun `unit parsing - should return null if invalid postcode`() {
        assertNull(Postcode.create("Definitely bogus").unit)
    }
}
