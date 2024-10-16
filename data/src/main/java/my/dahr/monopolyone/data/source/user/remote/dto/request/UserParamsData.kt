package my.dahr.monopolyone.data.source.user.remote.dto.request

import com.google.gson.annotations.Expose

data class UserParamsData(
    @Expose
    private val userId: Any,
    @Expose
    private val userIds: Set<Int>,
    @Expose
    private val type: String
)