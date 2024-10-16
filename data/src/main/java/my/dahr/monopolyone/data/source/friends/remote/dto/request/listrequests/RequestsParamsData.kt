package my.dahr.monopolyone.data.source.friends.remote.dto.request.listrequests

import com.google.gson.annotations.Expose

data class RequestsParamsData(
    @Expose
    private val accessToken: String,
    @Expose
    private val type: String,
    @Expose
    private val offset: Int,
    @Expose
    private val count: Int
)
