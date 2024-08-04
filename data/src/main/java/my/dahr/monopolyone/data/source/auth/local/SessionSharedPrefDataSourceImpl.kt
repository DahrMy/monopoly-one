package my.dahr.monopolyone.data.source.auth.local

import android.content.SharedPreferences
import com.google.gson.Gson

private const val SESSION_KEY = "session"

internal class SessionSharedPrefDataSourceImpl(
    private val sharedPreferences: SharedPreferences
) : SessionLocalDataSource {

    override var deserializedSession: DeserializedSession?

        get() {
            val sessionJson = sharedPreferences.getString(SESSION_KEY, "")
            return Gson().fromJson(sessionJson, DeserializedSession::class.java)
        }

        set(value) {
            if (value != null) {
                val serializedData = Gson().toJson(value)
                sharedPreferences.edit()
                    .putString(SESSION_KEY, serializedData)
                    .apply()
            } else {
                sharedPreferences.edit().remove(SESSION_KEY).apply()
            }
        }

}