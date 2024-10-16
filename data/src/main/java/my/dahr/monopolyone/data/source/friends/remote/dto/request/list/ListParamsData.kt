package my.dahr.monopolyone.data.source.friends.remote.dto.request.list

import com.google.gson.annotations.Expose

data class ListParamsData(
    @Expose
    private val userId: Any,
    @Expose
    private val online: Boolean,
    @Expose
    private val addUser: Boolean,
    @Expose
    private val type: String,
    @Expose
    private val offset: Int,
    @Expose
    private val count: Int
)
