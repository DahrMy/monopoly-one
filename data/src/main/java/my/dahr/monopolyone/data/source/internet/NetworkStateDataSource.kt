package my.dahr.monopolyone.data.source.internet

/**
 * Gets information about Internet connection state
 */
interface NetworkStateDataSource {

    /**
     * @return `true` if connected to the Internet, also `false`
     */
    fun hasInternetConnection(): Boolean

}