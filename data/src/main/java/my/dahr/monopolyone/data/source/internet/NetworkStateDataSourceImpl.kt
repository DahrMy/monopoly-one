package my.dahr.monopolyone.data.source.internet

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkStateDataSourceImpl(
    private val connectivityManager: ConnectivityManager
) : NetworkStateDataSource {

    override fun hasInternetConnection(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

}