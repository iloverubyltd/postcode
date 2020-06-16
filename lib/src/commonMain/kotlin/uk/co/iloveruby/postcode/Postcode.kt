@file:Suppress("unused")

package uk.co.iloveruby.postcode

/**
 * Postcode
 *
 * This wraps an input postcode String and provides instance methods to
 * validate, normalise or extract postcode data.
 *
 * This API is a bit more cumbersome that it needs to be. You should
 * favour `Postcode.parse()` or a static method depending on the
 * task at hand.
 */
sealed class Postcode {

    abstract val valid: Boolean
    abstract val postcode: String?
    abstract val incode: String?
    abstract val outcode: String?
    abstract val area: String?
    abstract val district: String?
    abstract val subDistrict: String?
    abstract val sector: String?
    abstract val unit: String?
    abstract fun normalise(): String?

    companion object {
        fun isValid() = isValid
        fun toNormalised() = toNormalised
        fun toOutcode() = toOutcode
        fun toIncode() = toIncode
        fun toArea() = toArea
        fun toSector() = toSector
        fun toUnit() = toUnit
        fun toDistrict() = toDistrict
        fun toSubDistrict() = toSubDistrict

        fun validOutcode(outcode: String): Boolean = outcode.matches(validOutcodeRegex)

        fun parse(postcode: String): Postcode = when {
            isValid(postcode) -> Valid(postcode)
            else -> Invalid
        }

        fun newInstance(postcode: String): Postcode = Base(postcode)
    }

    class Valid(postcode: String) : Postcode() {
        private val instance: Base = Base(postcode)

        override val valid: Boolean
            get() = instance.valid

        override val postcode: String
            get() = instance.normalise()!!

        override val incode: String
            get() = instance.incode!!

        override val outcode: String
            get() = instance.outcode!!

        override val area: String
            get() = instance.area!!

        override val district: String
            get() = instance.district!!

        override val subDistrict: String
            get() = instance.subDistrict!!

        override val sector: String
            get() = instance.sector!!

        override val unit: String
            get() = instance.unit!!

        override fun normalise(): String? = instance.normalise()
    }

    object Invalid : Postcode() {
        override val valid: Boolean
            get() = false

        override val postcode: String?
            get() = null

        override val incode: String?
            get() = null

        override val outcode: String?
            get() = null

        override val area: String?
            get() = null

        override val district: String?
            get() = null

        override val subDistrict: String?
            get() = null

        override val sector: String?
            get() = null

        override val unit: String?
            get() = null

        override fun normalise(): String? = null
    }

    private class Base(override val postcode: String) : Postcode() {
        private val _raw: String = postcode
        private val _valid: Boolean = isValid(postcode)

        // All parse methods should return null if invalid
        private var _incode: String? = null
        private var _outcode: String? = null
        private var _area: String? = null
        private var _unit: String? = null
        private var _district: String? = null
        private var _subDistrict: String? = null
        private var _sector: String? = null

        override val valid: Boolean
            get() = _valid

        override val incode: String?
            get() {
                if (_incode == null) _incode = toIncode(_raw)
                return _incode
            }

        override val outcode: String?
            get() {
                if (_outcode == null) _outcode = toOutcode(_raw)
                return _outcode
            }

        override val area: String?
            get() {
                if (_area == null) _area = toArea(_raw)
                return _area
            }

        override val district: String?
            get() {
                if (_district == null) _district = toDistrict(_raw)
                return _district
            }

        override val subDistrict: String?
            get() {
                if (_subDistrict == null) _subDistrict = toSubDistrict(_raw)
                return _subDistrict
            }

        override val sector: String?
            get() {
                if (_sector == null) _sector = toSector(_raw)
                return _sector
            }

        override val unit: String?
            get() {
                if (_unit == null) _unit = toUnit(_raw)
                return _unit
            }

        override fun normalise(): String? = "$outcode $incode"
    }
}
