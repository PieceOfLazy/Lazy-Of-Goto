package lazy.of.go.to.common.impl

import android.content.Context
import lazy.of.go.to.common.LocalPreferences
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author piece.of.lazy
 */
@Singleton
class SharedPreferences @Inject constructor(): LocalPreferences {

    @Inject
    lateinit var context: Context

    private fun getSharedPreference(): android.content.SharedPreferences {
        return context.getSharedPreferences(LocalPreferences.KEY_NAME, Context.MODE_PRIVATE)
    }

    private fun getEditor(): android.content.SharedPreferences.Editor {
        return getSharedPreference().edit()
    }

    override fun getValue(key: String, defValue: Boolean): Boolean = getSharedPreference().getBoolean(key, defValue)

    override fun getValue(key: String, defValue: Int): Int = getSharedPreference().getInt(key, defValue)

    override fun getValue(key: String, defValue: Long): Long = getSharedPreference().getLong(key, defValue)

    override fun getValue(key: String, defValue: Float): Float = getSharedPreference().getFloat(key, defValue)

    override fun getValue(key: String, defValue: String): String = getSharedPreference().getString(key, defValue)

    override fun setValue(key: String, value: Boolean) {
        val editor = getEditor()
        editor.putBoolean(key, value)
        editor.commit()
    }

    override fun setValue(key: String, value: Int) {
        val editor = getEditor()
        editor.putInt(key, value)
        editor.commit()
    }

    override fun setValue(key: String, value: Long) {
        val editor = getEditor()
        editor.putLong(key, value)
        editor.commit()
    }

    override fun setValue(key: String, value: Float) {
        val editor = getEditor()
        editor.putFloat(key, value)
        editor.commit()
    }

    override fun setValue(key: String, value: String) {
        val editor = getEditor()
        editor.putString(key, value)
        editor.commit()
    }
}