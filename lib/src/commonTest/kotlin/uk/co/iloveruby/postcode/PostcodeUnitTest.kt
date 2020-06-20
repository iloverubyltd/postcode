package uk.co.iloveruby.postcode

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import kotlinx.serialization.ImplicitReflectionSerializer
import uk.co.iloveruby.postcode.support.fromFixtureFile

@ImplicitReflectionSerializer
class PostcodeUnitTest : FunSpec({

    test("Postcode#valid should correctly validate postcode") {
        fromFixtureFile<String, Boolean>("validation.json").forAll { (base: String, expected: Boolean) ->
            Postcode.create(raw = base).valid shouldBe expected
        }
    }

    test("Postcode#normalise should correctly normalise Postcodes") {
        fromFixtureFile<String, String?>("normalisation.json").forAll { (base: String, expected: String?) ->
            Postcode.create(raw = base).normalise() shouldBe expected
        }
    }

    test("Postcode#normalise should return null if invalid postcode") {
        Postcode.create("Definitely bogus").normalise() shouldBe null
    }

    test("Postcode.Companion#validOutcode should return true for valid outcodes") {
        fromFixtureFile<String, String?>("outcodes.json").forAll { (base: String, unused: String?) ->
            Postcode.validOutcode(base) shouldBe true
        }
    }

    test("Postcode.Companion#validOutcode should return false for invalid outcode") {
        forAll(
            row("BOGUS"),
            row("Hello there"),
            row("12345")
        ) { code ->
            Postcode.validOutcode(code) shouldBe false
        }
    }

    test("Postcode#outcode should correctly parse outcode") {
        fromFixtureFile<String, String?>("outcodes.json").forAll { (base: String, expected: String?) ->
            Postcode.create(raw = base).outcode shouldBe expected
        }
    }

    test("Postcode#outcode should return null if invalid postcode") {
        Postcode.create("Definitely bogus").outcode shouldBe null
    }

    test("Postcode#incode should correctly parse incode") {
        fromFixtureFile<String, String?>("incodes.json").forAll { (base: String, expected: String?) ->
            Postcode.create(raw = base).incode shouldBe expected
        }
    }

    test("Postcode#incode should return null if invalid postcode") {
        Postcode.create("Definitely bogus").incode shouldBe null
    }

    test("Postcode#area should correctly parse areas") {
        fromFixtureFile<String, String?>("areas.json").forAll { (base: String, expected: String?) ->
            Postcode.create(raw = base).area shouldBe expected
        }
    }

    test("Postcode#area should return null if invalid postcode") {
        Postcode.create("Definitely bogus").area shouldBe null
    }

    test("Postcode#district should correctly parse districts") {
        fromFixtureFile<String, String?>("districts.json").forAll { (base: String, expected: String?) ->
            Postcode.create(raw = base).district shouldBe expected
        }
    }

    test("Postcode#district should return null if invalid postcode") {
        Postcode.create("Definitely bogus").district shouldBe null
    }

    test("Postcode#subDistrict should correctly parse sub-districts") {
        fromFixtureFile<String, String?>("sub-districts.json").forAll { (base: String, expected: String?) ->
            Postcode.create(raw = base).subDistrict shouldBe expected
        }
    }

    test("Postcode#subDistrict should return null if invalid postcode") {
        Postcode.create("Definitely bogus").subDistrict shouldBe null
    }

    test("Postcode#sector should correctly parse sectors") {
        fromFixtureFile<String, String?>("sectors.json").forAll { (base: String, expected: String?) ->
            Postcode.create(raw = base).sector shouldBe expected
        }
    }

    test("Postcode#sector should return null if invalid postcode") {
        Postcode.create("Definitely bogus").sector shouldBe null
    }

    test("Postcode#unit should correctly parse units") {
        fromFixtureFile<String, String?>("units.json").forAll { (base: String, expected: String?) ->
            Postcode.create(raw = base).unit shouldBe expected
        }
    }

    test("Postcode#unit should return null if invalid postcode") {
        Postcode.create("Definitely bogus").unit shouldBe null
    }
})
