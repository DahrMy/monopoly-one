package my.dahr.monopolyone.ui.home.friends

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import my.dahr.monopolyone.data.converters.toUi
import my.dahr.monopolyone.data.models.RequestStatus
import my.dahr.monopolyone.data.network.MonopolyCallback
import my.dahr.monopolyone.data.network.dto.response.BaseResponse
import my.dahr.monopolyone.data.network.dto.response.SessionResponse
import my.dahr.monopolyone.data.network.dto.response.friends.add.AddRequest
import my.dahr.monopolyone.data.network.dto.response.friends.add.AddResponse
import my.dahr.monopolyone.data.network.dto.response.friends.delete.DeleteRequest
import my.dahr.monopolyone.data.network.dto.response.friends.delete.DeleteResponse
import my.dahr.monopolyone.data.network.dto.response.friends.list.FriendsResponse
import my.dahr.monopolyone.data.network.dto.response.friends.requests.FriendsRequestsResponse
import my.dahr.monopolyone.data.repository.ResourceRepository
import my.dahr.monopolyone.domain.models.friends.list.Friend
import my.dahr.monopolyone.domain.models.friends.requests.Request
import my.dahr.monopolyone.domain.repository.FriendsRepository
import my.dahr.monopolyone.utils.SessionHelper
import my.dahr.monopolyone.utils.toSession
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val sessionHelper: SessionHelper,
    private val repository: FriendsRepository,
    private val resourceRepository: ResourceRepository
) : ViewModel() {

    private val myCoroutineContext = SupervisorJob() + Dispatchers.IO
    val friendsResultLiveData = MutableLiveData<List<Friend>>()

    val friendForUserResultLiveData = MutableLiveData<List<Friend>>()

    val friendResultForChecking = MutableLiveData<List<Friend>>()

    val friendsRequestsResultLiveData = MutableLiveData<List<Request>>()

    val isFriend = MutableLiveData<Boolean>()

    val requestStatusLiveData = MutableLiveData<RequestStatus>()

    fun getFriendList() {
        val sessionFromHelper = sessionHelper.session
        if (sessionFromHelper != null) {
            requestStatusLiveData.postValue(RequestStatus.Loading)
            viewModelScope.launch(myCoroutineContext) {
                repository.getFriendsList(
                    sessionFromHelper.userId,
                    online = false,
                    addUser = false,
                    type = "short",
                    offset = 0,
                    count = 20,
                    callback = object : MonopolyCallback<BaseResponse>(requestStatusLiveData, null) {
                        override fun onSuccessfulResponse(
                            call: Call<BaseResponse>,
                            responseBody: BaseResponse
                        ) {
                            when (responseBody) {
                                is FriendsResponse -> {
                                    val friends = responseBody.toUi().data.friends
                                    friendsResultLiveData.postValue(friends)
                                    requestStatusLiveData.postValue(RequestStatus.Success)
                                }

                                else -> handleErrorResponse(responseBody)
                            }
                        }

                    })

            }
        }
    }

    fun checkIfFriend(userId: Any) {
        val sessionFromHelper = sessionHelper.session
        if (sessionFromHelper != null) {
            getFriendsListForChecking(sessionFromHelper.userId)
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


    private fun getFriendsListForChecking(userId: Any) {
        requestStatusLiveData.postValue(RequestStatus.Loading)
        viewModelScope.launch {
            repository.getFriendsList(
                userId,
                online = false,
                addUser = false,
                type = "short",
                offset = 0,
                count = 20,
                callback = object : MonopolyCallback<BaseResponse>(requestStatusLiveData, null) {
                    override fun onSuccessfulResponse(
                        call: Call<BaseResponse>,
                        responseBody: BaseResponse
                    ) {
                        when (responseBody) {
                            is FriendsResponse -> {
                                val friends = responseBody.toUi().data.friends
                                friendResultForChecking.postValue(friends)
                                requestStatusLiveData.postValue(RequestStatus.Success)
                            }

                            else -> handleErrorResponse(responseBody)
                        }
                    }

                }
            )
        }
    }

    fun getFriendListForUser(userId: Any) {
        requestStatusLiveData.postValue(RequestStatus.Loading)
        viewModelScope.launch(myCoroutineContext) {
            repository.getFriendsList(
                userId,
                online = false,
                addUser = false,
                type = "short",
                offset = 0,
                count = 20,
                callback = object : MonopolyCallback<BaseResponse>(requestStatusLiveData, null) {
                    override fun onSuccessfulResponse(
                        call: Call<BaseResponse>,
                        responseBody: BaseResponse
                    ) {
                        when (responseBody) {
                            is FriendsResponse -> {
                                val friends = responseBody.toUi().data.friends
                                friendForUserResultLiveData.postValue(friends)
                                requestStatusLiveData.postValue(RequestStatus.Success)
                            }

                            else -> handleErrorResponse(responseBody)
                        }

                    }

                }
            )
        }
    }

    private fun getRequests() {
        val sessionFromHelper = sessionHelper.session
        if (sessionFromHelper != null) {
            repository.getFriendsRequestsList(
                sessionFromHelper.accessToken,
                "short",
                0,
                20,
                object : MonopolyCallback<BaseResponse>(requestStatusLiveData, null) {
                    override fun onSuccessfulResponse(
                        call: Call<BaseResponse>,
                        responseBody: BaseResponse
                    ) {
                        when (responseBody) {
                            is FriendsRequestsResponse -> {
                                val requests = responseBody.toUi().data.requests
                                Log.d("requests", requests.toString())
                                friendsRequestsResultLiveData.postValue(requests)
                                requestStatusLiveData.postValue(RequestStatus.Success)
                            }

                            else -> handleErrorResponse(responseBody)
                        }

                    }

                }
            )
        }
    }


    fun getFriendRequestsList() {
        val sessionFromHelper = sessionHelper.session
        requestStatusLiveData.postValue(RequestStatus.Loading)
        viewModelScope.launch {
            if (sessionHelper.isSessionNotExpired()) {
                if (sessionHelper.isCurrentIpChanged()) {
                    if (sessionFromHelper != null) {
                        getRequests()
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
                                    getRequests()
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

    private fun add(userId: Any) {
        val sessionFromHelper = sessionHelper.session
        if (sessionFromHelper != null) {
            val request = AddRequest(
                access_token = sessionFromHelper.accessToken,
                userId = userId
            )
            repository.addFriend(
                request,
                object : MonopolyCallback<BaseResponse>(requestStatusLiveData, null) {
                    override fun onSuccessfulResponse(
                        call: Call<BaseResponse>,
                        responseBody: BaseResponse
                    ) {
                        requestStatusLiveData.postValue(RequestStatus.Success)
                        when (responseBody) {
                            is AddResponse -> {

                            }

                            else -> handleErrorResponse(responseBody)
                        }
                    }
                })
        }
    }

    private fun delete(userId: Any) {
        val sessionFromHelper = sessionHelper.session
        if (sessionFromHelper != null) {
            val request = DeleteRequest(
                access_token = sessionFromHelper.accessToken,
                userId = userId
            )
            repository.deleteFriend(
                request,
                object : MonopolyCallback<BaseResponse>(requestStatusLiveData, null) {
                    override fun onSuccessfulResponse(
                        call: Call<BaseResponse>,
                        responseBody: BaseResponse
                    ) {
                        when (responseBody) {
                            is DeleteResponse -> {
                                requestStatusLiveData.postValue(RequestStatus.Success)
                            }

                            else -> handleErrorResponse(responseBody)
                        }
                    }
                })
        }
    }

    fun addFriend(userId: Any) {
        val sessionFromHelper = sessionHelper.session
        viewModelScope.launch {
            if (sessionHelper.isSessionNotExpired()) {
                if (sessionHelper.isCurrentIpChanged()) {
                    if (sessionFromHelper != null) {
                        add(userId)
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
                                add(userId)
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
                        delete(userId)
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
                                delete(userId)
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


    fun loadErrorMessage(status: RequestStatus) =
        resourceRepository.getErrorMessageStringResource(status)
}

