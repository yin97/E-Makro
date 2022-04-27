package uz.dsavdo.emakro.utills

import android.content.Context
import android.content.SharedPreferences
import uz.dsavdo.emakro.network.Constants.Companion.LOGIN_OFF
import uz.dsavdo.emakro.network.Constants.Companion.LOGIN_ON

@Suppress("UNCHECKED_CAST")
class SharedPrefs(context: Context) {

    companion object {
        private const val PREF = "MyAppPrefName"
        const val LOGIN = "LOGIN"
        const val PHONE = "PHONE"
    }

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    val statusOfLogin = get(LOGIN, Int::class.java)
    fun setStatusLogin(status: Int) = put(LOGIN, status)

    val phone = get(PHONE, String::class.java)
    fun setPhone(phone: String) = put(PHONE, phone)

    private fun <T> get(key: String, clazz: Class<T>): T =
        when (clazz) {
            String::class.java -> sharedPref.getString(key, "")
            Boolean::class.java -> sharedPref.getBoolean(key, false)
            Float::class.java -> sharedPref.getFloat(key, -1f)
            Double::class.java -> sharedPref.getFloat(key, -1f)
            Int::class.java -> sharedPref.getInt(key, -1)
            Long::class.java -> sharedPref.getLong(key, -1L)
            else -> null
        } as T

    private fun <T> put(key: String, data: T) {
        val editor = sharedPref.edit()
        when (data) {
            is String -> editor.putString(key, data)
            is Boolean -> editor.putBoolean(key, data)
            is Float -> editor.putFloat(key, data)
            is Double -> editor.putFloat(key, data.toFloat())
            is Int -> editor.putInt(key, data)
            is Long -> editor.putLong(key, data)
        }
        editor.apply()
    }

}
