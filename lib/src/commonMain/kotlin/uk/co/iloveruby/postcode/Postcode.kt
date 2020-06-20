@file:Suppress("unused")

package uk.co.iloveruby.postcode

import kotlin.jvm.JvmStatic

/**
 * Postcode in the United Kingdom
 *
 * @see parse
 *
 */
sealed class Postcode {

    open val raw: String = ""

    /**
     *
     */
    abstract val valid: Boolean

    /**
     * the normalised postcode, e.g. "SW1A 2AA"
     */
    abstract val postcode: String?

    /**
     * the outcode component of the postcode, e.g. the **SW1A** part of "**SW1A** 2AA"
     */
    abstract val outcode: String?

    /**
     * the incode component of the postcode, e.g. the **2AA** part of "SW1A **2AA**"
     */
    abstract val incode: String?

    /**
     * the area component of the postcode, e.g. the **SW** part of "**SW**1A 2AA"
     */
    abstract val area: String?

    /**
     * the district component of the postcode, e.g. **SW1** part of "**SW1**A 2AA"
     */
    abstract val district: String?

    /**
    the subdistrict component of the postcode, e.g. **SW1A** in "**SW1A** 2AA"
     */
    abstract val subDistrict: String?

    /**
     * the sector component of the postcode, e.g. **SW1A 2** in "**SW1A 2**AA"
     */
    abstract val sector: String?

    /**
     * the unit component of the postcode, e.g. **AA** in "SW1A 2**AA**"
     */
    abstract val unit: String?

    /**
     * normalises a postcode
     */
    abstract fun normalise(): String?

    companion object {
        /**
         * @see uk.co.iloveruby.postcode.isValid
         */
        fun isValid() = isValid

        /**
         * @see uk.co.iloveruby.postcode.toNormalised
         */
        fun toNormalised() = toNormalised

        /**
         * @see uk.co.iloveruby.postcode.toOutcode
         */
        fun toOutcode() = toOutcode

        /**
         * @see uk.co.iloveruby.postcode.toIncode
         */
        fun toIncode() = toIncode

        /**
         * @see uk.co.iloveruby.postcode.toArea
         */
        fun toArea() = toArea

        /**
         * @see uk.co.iloveruby.postcode.toSector
         */
        fun toSector() = toSector

        /**
         * @see uk.co.iloveruby.postcode.toUnit
         */
        fun toUnit() = toUnit

        /**
         * @see uk.co.iloveruby.postcode.toDistrict
         */
        fun toDistrict() = toDistrict

        /**
         * @see uk.co.iloveruby.postcode.toSubDistrict
         */
        fun toSubDistrict() = toSubDistrict

        fun validOutcode(outcode: String): Boolean = validOutcodeRegex.containsMatchIn(outcode)

        /**
         * Parses a postcode returning either the [Valid] or [Invalid] [Postcode] subclass.
         *
         * @return an instance of [Postcode.Valid] if the postcode is valid, or the
         * [Postcode.Invalid] object if the postcode is invalid.
         */
        @JvmStatic
        fun parse(raw: String) = when {
            isValid(raw) -> Valid(raw)
            else -> Invalid
        }

        /**
         * Creates an instance of [Postcode.Base].
         *
         * Prefer [parse]
         *
         * @return an instance of [Postcode.Base].
         * @see parse
         */
        @Deprecated(
            message = "Legacy API",
            replaceWith = ReplaceWith(
                """Postcode.parse(raw = "SW1A 9AA")""",
                "uk.co.iloveruby.postcode.Postcode"
            )
        )
        fun create(raw: String): Postcode = Base(raw)
    }

    /**
     * An instance of Valid represents a valid postcode.
     *
     * All member properties are not null.
     */
    class Valid internal constructor(override val raw: String) : Postcode() {
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

    /**
     * A singleton representing an invalid postcode.
     *
     * All member properties are null, with the exception of [valid] and [raw].
     */
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

    /**
     * Postcode.Base
     *
     * This wraps an input postcode String and provides instance methods to
     * validate, normalise or extract postcode data.
     *
     * This API is a bit more cumbersome that it needs to be. You should
     * favour `Postcode.parse()` or a static method depending on the
     * task at hand.
     */
    @Deprecated(message = "Legacy API")
    class Base internal constructor(override val raw: String) : Postcode() {

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
