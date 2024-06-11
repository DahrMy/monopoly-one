package my.dahr.monopolyone.ui.home.friends

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import my.dahr.monopolyone.data.converters.toUi
import my.dahr.monopolyone.data.models.RequestStatus
import my.dahr.monopolyone.data.network.MonopolyCallback
import my.dahr.monopolyone.data.network.dto.response.BaseResponse
import my.dahr.monopolyone.data.network.dto.response.SessionResponse
import my.dahr.monopolyone.data.network.dto.response.friends.add.AddResponseJson
import my.dahr.monopolyone.data.network.dto.response.friends.delete.DeleteResponseJson
import my.dahr.monopolyone.data.network.dto.response.friends.list.FriendsResponse
import my.dahr.monopolyone.data.network.dto.response.friends.requests.FriendsRequestsResponse
import my.dahr.monopolyone.domain.models.friends.list.Friend
import my.dahr.monopolyone.domain.models.friends.requests.FriendsRequests
import my.dahr.monopolyone.domain.models.friends.requests.Request
import my.dahr.monopolyone.domain.repository.FriendsRepository
import my.dahr.monopolyone.utils.SessionHelper
import my.dahr.monopolyone.utils.toSession

import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val sessionHelper: SessionHelper,
    private val repository: FriendsRepository
) : ViewModel() {

    private val myCoroutineContext = SupervisorJob() + Dispatchers.IO
    val friendsResultLiveData = MutableLiveData<List<Friend>>()

    val friendForUserResultLiveData = MutableLiveData<List<Friend>>()

    val friendsRequestsResultLiveData = MutableLiveData<List<Request>>()

    val isFriend = MutableLiveData<Boolean>()

    private val requestStatusLiveData = MutableLiveData<RequestStatus>()

    fun getFriendList() {
        val sessionFromHelper = sessionHelper.session
        if (sessionFromHelper != null) {
            viewModelScope.launch(myCoroutineContext) {
                repository.getFriendsList(
                    sessionFromHelper.userId,
                    online = false,
                    addUser = false,
                    type = "short",
                    offset = 0,
                    count = 20,
                    callback = object : MonopolyCallback<BaseResponse>(requestStatusLiveData) {
                        override fun onSuccessfulResponse(
                            call: Call<BaseResponse>,
                            responseBody: BaseResponse
                        ) {
                            when (responseBody) {
                                is FriendsResponse -> {
                                    val friends = responseBody.toUi().data.friends
                                    friendsResultLiveData.postValue(friends)
                                }

                                else -> handleErrorResponse(responseBody)
                            }
                        }

                    })

            }
        }
    }

//    fun checkIfFriend(userId: Any) {
//        val sessionFromHelper = sessionHelper.session
//        if (sessionFromHelper != null) {
//            viewModelScope.launch(myCoroutineContext) {
//                val friends = getFriendsListForChecking(sessionFromHelper.userId)
//                var found = false
//                for (friend in friends) {
//                    if (userId == friend.userId) {
//                        found = true
//                        break
//                    }
//                }
//                isFriend.postValue(found)
//            }
//        }
//    }

    fun checkIfMe(userId: Any): Boolean {
        val sessionFromHelper = sessionHelper.session
        var result = false
        if (sessionFromHelper != null) {
            if (userId == sessionFromHelper.userId) {
                result = true
            }
        }
        return result
    }


//    private suspend fun getFriendsListForChecking(userId: Any): List<Friend> {
//        return repository.getFriendsList(
//            userId,
//            online = false,
//            addUser = false,
//            type = "short",
//            offset = 0,
//            count = 20
//        )
//    }

//    fun getFriendListForUser(userId: Any) {
//        viewModelScope.launch(myCoroutineContext) {
//            val response = repository.getFriendsList(
//                userId,
//                online = false,
//                addUser = false,
//                type = "short",
//                offset = 0,
//                count = 20
//            )
//            friendForUserResultLiveData.postValue(response)
//        }
//    }


    fun getFriendRequestsList() {
        val sessionFromHelper = sessionHelper.session
        viewModelScope.launch {
            if (sessionHelper.isSessionNotExpired()) {
                if (sessionHelper.isCurrentIpChanged()) {
                    if (sessionFromHelper != null) {
                         repository.getFriendsRequestsList(
                            sessionFromHelper.accessToken,
                            "short",
                            0,
                            20,
                            object : MonopolyCallback<BaseResponse>(requestStatusLiveData){
                                override fun onSuccessfulResponse(
                                    call: Call<BaseResponse>,
                                    responseBody: BaseResponse
                                ) {
                                    when (responseBody) {
                                        is FriendsRequestsResponse -> {
                                            val requests = responseBody.toUi().data.requests
                                            Log.d("requests", requests.toString())
                                            friendsRequestsResultLiveData.postValue(requests)
                                        }

                                        else -> handleErrorResponse(responseBody)
                                    }

                                }

                            }
                        )
                    } else {
                        sessionHelper.refreshSavedIp()
                    }
                } else {
                    if (sessionFromHelper != null) {
                        sessionHelper.refreshSession(sessionFromHelper.refreshToken) {
                            object : Callback<SessionResponse> {
                                override fun onResponse(
                                    call: Call<SessionResponse>,
                                    response: Response<SessionResponse>
                                ) {
                                    val session = response.body()?.data?.toSession()
                                    if (session != null) {
                                        sessionHelper.session = session
                                    }
                                }

                                override fun onFailure(
                                    call: Call<SessionResponse>,
                                    t: Throwable
                                ) {
                                    Log.e("Retrofit", "Failure: ${t.message}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    fun addFriend(userId: Any) {
        val sessionFromHelper = sessionHelper.session
        viewModelScope.launch {
            if (sessionHelper.isSessionNotExpired()) {
                if (sessionHelper.isCurrentIpChanged()) {
                    if (sessionFromHelper != null) {
                        val response =
                            AddResponseJson(
                                access_token = sessionFromHelper.accessToken,
                                userId = userId
                            )
                        repository.addFriend(response)
                    }
                } else {
                    sessionHelper.refreshSavedIp()
                }
            } else {
                if (sessionFromHelper != null) {
                    sessionHelper.refreshSession(sessionFromHelper.refreshToken) {
                        object : Callback<SessionResponse> {
                            override fun onResponse(
                                call: Call<SessionResponse>,
                                response: Response<SessionResponse>
                            ) {
                                val session = response.body()?.data?.toSession()
                                if (session != null) {
                                    sessionHelper.session = session
                                }
                            }

                            override fun onFailure(
                                call: Call<SessionResponse>,
                                t: Throwable
                            ) {
                                Log.e("Retrofit", "Failure: ${t.message}")
                            }
                        }
                    }
                }
            }
        }
    }


    fun deleteFriend(userId: Any) {
        val sessionFromHelper = sessionHelper.session
        viewModelScope.launch {
            if (sessionHelper.isSessionNotExpired()) {
                if (sessionHelper.isCurrentIpChanged()) {
                    if (sessionFromHelper != null) {
                        val response =
                            DeleteResponseJson(
                                access_token = sessionFromHelper.accessToken,
                                userId = userId
                            )
                        repository.deleteFriend(response)
                    }
                } else {
                    sessionHelper.refreshSavedIp()
                }
            } else {
                if (sessionFromHelper != null) {
                    sessionHelper.refreshSession(sessionFromHelper.refreshToken) {
                        object : Callback<SessionResponse> {
                            override fun onResponse(
                                call: Call<SessionResponse>,
                                response: Response<SessionResponse>
                            ) {
                                val session = response.body()?.data?.toSession()
                                if (session != null) {
                                    sessionHelper.session = session
                                }
                            }

                            override fun onFailure(
                                call: Call<SessionResponse>,
                                t: Throwable
                            ) {
                                Log.e("Retrofit", "Failure: ${t.message}")
                            }
                        }
                    }
                }
            }
        }
    }
}

