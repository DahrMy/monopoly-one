package my.dahr.monopolyone.ui.friends

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import my.dahr.monopolyone.domain.model.friends.params.AddParams
import my.dahr.monopolyone.domain.model.friends.params.DeleteParams
import my.dahr.monopolyone.domain.model.friends.params.ListParams
import my.dahr.monopolyone.domain.model.friends.params.RequestsParams
import my.dahr.monopolyone.domain.model.session.Session
import my.dahr.monopolyone.domain.models.friends.list.Friend
import my.dahr.monopolyone.domain.models.friends.list.Friends
import my.dahr.monopolyone.domain.models.friends.requests.FriendsRequests
import my.dahr.monopolyone.domain.models.friends.requests.Request
import my.dahr.monopolyone.domain.usecase.friends.AddFriendUseCase
import my.dahr.monopolyone.domain.usecase.friends.DeleteFriendUseCase
import my.dahr.monopolyone.domain.usecase.friends.GetFriendsListUseCase
import my.dahr.monopolyone.domain.usecase.friends.GetListOfRequestsUseCase
import my.dahr.monopolyone.domain.usecase.session.RequireSessionUseCase
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val getFriendsListUseCase: GetFriendsListUseCase,
    private val requireSessionUseCase: RequireSessionUseCase,
    private val getListOfRequestsUseCase: GetListOfRequestsUseCase,
    private val addFriendUseCase: AddFriendUseCase,
    private val deleteFriendUseCase: DeleteFriendUseCase
) : ViewModel() {

    private val myCoroutineContext = SupervisorJob() + Dispatchers.IO
    val friendsResultLiveData = MutableLiveData<List<Friend>>()

    val friendForUserResultLiveData = MutableLiveData<List<Friend>>()

    val friendResultForChecking = MutableLiveData<List<Friend>>()

    val friendsRequestsResultLiveData = MutableLiveData<List<Request>>()

    val isFriend = MutableLiveData<Boolean>()

    fun getFriendList() {
        viewModelScope.launch(myCoroutineContext) {
            val session = requireSessionUseCase()
            if (session is Session) {
                val params = ListParams(
                    userId = session.userId,
                    online = false,
                    addUser = false,
                    type = "short",
                    offset = 0,
                    count = 20
                )
                val list = getFriendsListUseCase(params)
                if (list is Friends) {
                    val listOfFriends = list.data.friends
                    friendsResultLiveData.postValue(listOfFriends)
                }
            }
        }
    }

    fun checkIfFriend(userId: Any) {
        viewModelScope.launch {
            val session = requireSessionUseCase()
            if (session is Session) {
                getFriendsListForChecking(session.userId)
                friendResultForChecking.observeForever {
                    var found = false
                    for (friend in it) {
                        if (userId == friend.userId) {
                            found = true
                            break
                        }
                    }
                    isFriend.postValue(found)
                    friendResultForChecking.removeObserver { }
                }
            }
        }
    }

    fun checkIfMe(userId: Any): Boolean {
        var result = false
        viewModelScope.launch {
            val session = requireSessionUseCase()
            if (session is Session) {
                if (userId == session.userId) {
                    result = true
                }
            }
        }
        return result
    }


    private fun getFriendsListForChecking(userId: Any) {
        viewModelScope.launch {
            val params = ListParams(
                userId,
                online = false,
                addUser = false,
                type = "short",
                offset = 0,
                count = 20
            )

            val list = getFriendsListUseCase(params)
            if (list is Friends) {
                val friends = list.data.friends
                friendResultForChecking.postValue(friends)
            }
        }
    }

    fun getFriendListForUser(userId: Any) {
        viewModelScope.launch(myCoroutineContext) {
            val params = ListParams(
                userId,
                online = false,
                addUser = false,
                type = "short",
                offset = 0,
                count = 20
            )

            val list = getFriendsListUseCase(params)
            if (list is Friends) {
                val friends = list.data.friends
                friendForUserResultLiveData.postValue(friends)
            }
        }
    }

     fun getRequestsList() {
        viewModelScope.launch {
            val session = requireSessionUseCase()
            if (session is Session) {
                val params = RequestsParams(session.accessToken.token, "short", 0, 20)
                val listOfRequest = getListOfRequestsUseCase(params)
                if (listOfRequest is FriendsRequests) {
                    friendsRequestsResultLiveData.postValue(listOfRequest.data.requests)
                }
            }
        }
    }


     fun addFriend(userId: Any) {
        viewModelScope.launch {
            val session = requireSessionUseCase()
            if (session is Session) {
                val request = AddParams(
                    access_token = session.accessToken.token,
                    userId = userId
                )
                addFriendUseCase(request)
            }
        }
    }

     fun deleteFriend(userId: Any) {
        viewModelScope.launch {
            val session = requireSessionUseCase()
            if (session is Session) {
                val request = DeleteParams(
                    access_token = session.accessToken.token,
                    userId = userId
                )
                deleteFriendUseCase(request)
            }
        }
    }
}

