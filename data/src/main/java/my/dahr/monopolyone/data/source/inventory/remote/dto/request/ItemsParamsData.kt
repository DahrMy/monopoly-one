package my.dahr.monopolyone.data.source.inventory.remote.dto.request

data class ItemsParamsData(
    private val accessToken: String,
    private val userId: Any,
    private val includeStock: Boolean,
    private val order: String,
    private val count: Int,
    private val addUser: Boolean,
    private val addEquipped: String,
    private val addLegacy: Boolean
)