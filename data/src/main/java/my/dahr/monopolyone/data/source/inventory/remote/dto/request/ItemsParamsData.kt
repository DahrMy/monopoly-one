package my.dahr.monopolyone.data.source.inventory.remote.dto.request

import com.google.gson.annotations.Expose

data class ItemsParamsData(
    @Expose
    private val accessToken: String,
    @Expose
    private val userId: Any,
    @Expose
    private val includeStock: Boolean,
    @Expose
    private val order: String,
    @Expose
    private val count: Int,
    @Expose
    private val addUser: Boolean,
    @Expose
    private val addEquipped: String,
    @Expose
    private val addLegacy: Boolean
)