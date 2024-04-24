package my.dahr.monopolyone.ui.home.friends

import my.dahr.monopolyone.network.api.MonopolyApi
import javax.inject.Inject

class FriendsRepository @Inject constructor(
    private val monopolyApi: MonopolyApi
){
    suspend fun getFriendsList() = monopolyApi.getFriendsList()
}