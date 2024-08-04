package my.dahr.monopolyone.domain.repository

import my.dahr.monopolyone.domain.model.Ip

/**
 * The repository make contract with network data information
 */
interface NetworkRepository {

    /**
     * Gets current IP address.
     */
    suspend fun getCurrentIp(): Ip?

    /**
     * Gets IP address stored in local storage.
     */
    fun getStoredIp(): Ip?

    /**
     * Save IP address into local storage.
     * @param ip current IP address.
     */
    fun saveIp(ip: Ip)
}