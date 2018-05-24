package lazy.of.go.to.common

/**
 * @author piece.of.lazy
 */
interface LocalPreferences {
    companion object {
        const val KEY_NAME = "lazy.of.go.to.pref"

        const val KEY_AUTH_ANONYMOUSLY = "KEY_AUTH_ANONYMOUSLY"

        const val KEY_USER_UUID = "KEY_USER_UUID"
    }

    fun getValue(key: String, defValue: Boolean): Boolean

    fun getValue(key: String, defValue: Int): Int

    fun getValue(key: String, defValue: Long): Long

    fun getValue(key: String, defValue: Float): Float

    fun getValue(key: String, defValue: String): String

    fun setValue(key: String, value: Boolean)

    fun setValue(key: String, value: Int)

    fun setValue(key: String, value: Long)

    fun setValue(key: String, value: Float)

    fun setValue(key: String, value: String)
}