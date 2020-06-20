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

    open val raw: String = ""

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

        fun parse(raw: String): Postcode = when {
            isValid(raw) -> Valid(raw)
            else -> Invalid
        }

        fun create(raw: String): Postcode = Base(raw)
    }

    class Valid(override val raw: String) : Postcode() {
        private val instance: Base = Base(raw)

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

    private class Base(override val raw: String) : Postcode() {

        override val valid: Boolean by lazy { isValid(raw) }
        override val incode: String? by nullUnlessValid { toIncode(raw) }
        override val outcode: String? by nullUnlessValid { toOutcode(raw) }
        override val area: String? by nullUnlessValid { toArea(raw) }
        override val district: String? by nullUnlessValid { toDistrict(raw) }
        override val subDistrict: String? by nullUnlessValid { toSubDistrict(raw) }
        override val sector: String? by nullUnlessValid { toSector(raw) }
        override val unit: String? by nullUnlessValid { toUnit(raw) }

        override val postcode: String?
            get() = TODO("Not yet implemented")

        override fun normalise(): String? = if (valid) "$outcode $incode" else null

        private fun <T> nullUnlessValid(initializer: () -> T): Lazy<T?> = lazy {
            if (valid) initializer() else null
        }
    }
}
