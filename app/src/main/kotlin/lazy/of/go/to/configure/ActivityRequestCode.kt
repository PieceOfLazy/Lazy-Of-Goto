package lazy.of.go.to.configure

/**
 * @author piece.of.lazy
 */
enum class ActivityRequestCode {
    UNDEFINED,
    AUTH_GOOGLE_SIGN_IN;

    companion object {
        fun generate(ordinal: Int): ActivityRequestCode {
            val values = ActivityRequestCode.values()
            if(ordinal >= 0 && ordinal < values.size) {
                return values[ordinal]
            }
            return UNDEFINED
        }
    }
}