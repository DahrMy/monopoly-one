package my.dahr.monopolyone.ui.home.friends


import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import my.dahr.monopolyone.data.models.Session
import my.dahr.monopolyone.data.network.dto.friends.add.AddResponseJson
import my.dahr.monopolyone.data.network.dto.friends.delete.DeleteResponseJson
import my.dahr.monopolyone.domain.models.friends.list.Friend
import my.dahr.monopolyone.domain.models.friends.requests.Request
import my.dahr.monopolyone.domain.repository.FriendsRepository
import my.dahr.monopolyone.utils.SESSION_KEY
import my.dahr.monopolyone.utils.SHARED_PREFERENCES

import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val repository: FriendsRepository

) : ViewModel() {

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
    private val sessionJson = sharedPreferences.getString(SESSION_KEY, "")
    private var session = Gson().fromJson(sessionJson, Session::class.java)


    private val myCoroutineContext = SupervisorJob() + Dispatchers.IO
    val friendsResultLiveData = MutableLiveData<List<Friend>>()

    val friendForUserResultLiveData = MutableLiveData<List<Friend>>()

    val friendsRequestsResultLiveData = MutableLiveData<List<Request>>()


    fun getFriendList() {
        viewModelScope.launch(myCoroutineContext) {
            try {
                val response = repository.getFriendsList(
                    session.userId,
                    online = false,
                    addUser = false,
                    type = "short",
                    offset = 0,
                    count = 20
                )
                friendsResultLiveData.postValue(response)
            } catch (e: Exception) {
                Log.d("e", e.toString())
            }
        }
    }

    fun getFriendListForUser(friend: Friend) {
        viewModelScope.launch(myCoroutineContext) {
            val response = repository.getFriendsList(
                friend.userId,
                online = false,
                addUser = false,
                type = "short",
                offset = 0,
                count = 20
            )
            friendForUserResultLiveData.postValue(response)
        }
    }


fun getFriendRequestsList() {
    viewModelScope.launch(myCoroutineContext) {
        try {
            val response = repository.getFriendsRequestsList(
                session.accessToken,
                "short",
                0,
                20
            )
            friendsRequestsResultLiveData.postValue(response)
        } catch (e: Exception) {
            Log.d("error", e.toString())
        }
    }
}

fun addFriend(userId: Any) {
    viewModelScope.launch(myCoroutineContext) {
        val response = AddResponseJson(access_token = session.accessToken, userId = userId)
        repository.addFriend(response)
    }
}

fun deleteFriend(userId: Any) {
    viewModelScope.launch(myCoroutineContext) {
        val response = DeleteResponseJson(access_token = session.accessToken, userId = userId)
        repository.deleteFriend(response)
    }
}
}



