package my.dahr.monopolyone.data.source.ip.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

private const val IP_KEY = "ip_address"

class IpSharedPrefDataSourceImpl(
    private val sharedPreferences: SharedPreferences
) : IpLocalDataSource {

    override var storedIp: DeserializedIp?

        get() {
            val serializedData = sharedPreferences.getString(IP_KEY, null)
            return if (!serializedData.isNullOrEmpty() && serializedData.startsWith("{")) {
                try {
                    Gson().fromJson(serializedData, DeserializedIp::class.java)
                } catch (e: JsonSyntaxException) {
                    null
                }
            } else {
                null
            }
        }

        set(value) {
            if (value != null) {
                val serializedData = Gson().toJson(value)
                sharedPreferences.edit()
                    .putString(IP_KEY, serializedData)
                    .apply()
            } else {
                sharedPreferences.edit().remove(IP_KEY).apply()
            }
        }

}