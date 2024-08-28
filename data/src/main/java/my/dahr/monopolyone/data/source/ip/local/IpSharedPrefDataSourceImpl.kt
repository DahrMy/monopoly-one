package my.dahr.monopolyone.data.source.ip.local

import android.content.SharedPreferences
import com.google.gson.Gson

private const val IP_KEY = "ip_address"

class IpSharedPrefDataSourceImpl(
    private val sharedPreferences: SharedPreferences
) : IpLocalDataSource {

    override var storedIp: DeserializedIp?

        get() {
            val serializedData = sharedPreferences.getString(IP_KEY, "")
            return Gson().fromJson(serializedData, DeserializedIp::class.java)
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