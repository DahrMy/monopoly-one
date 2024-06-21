package my.dahr.monopolyone.data.network.dto.inventory.items


import com.google.gson.annotations.SerializedName

data class UserJson(
    @SerializedName("avatar")
    val avatar: String? = null,
    @SerializedName("avatar_key")
    val avatarKey: String? = null,
    @SerializedName("friendship")
    val friendship: Int? = null,
    @SerializedName("games")
    val games: Int? = null,
    @SerializedName("games_wins")
    val gamesWins: Int? = null,
    @SerializedName("gender")
    val gender: Int? = null,
    @SerializedName("nick")
    val nick: String? = null,
    @SerializedName("nicks_old")
    val nicksOld: List<Any?>? = null,
    @SerializedName("rank")
    val rank: RankJson? = null,
    @SerializedName("user_id")
    val userId: Int? = null,
    @SerializedName("xp")
    val xp: Int? = null,
    @SerializedName("xp_level")
    val xpLevel: Int? = null
)