package my.dahr.monopolyone.data.network.dto.users

import com.google.gson.annotations.SerializedName

data class DataJson(
    @SerializedName("approved")
    val approved: Int? = null,
    @SerializedName("avatar")
    val avatar: String? = null,
    @SerializedName("domain")
    val domain: String? = null,
    @SerializedName("games")
    val games: Int? = null,
    @SerializedName("games_wins")
    val gamesWins: Int? = null,
    @SerializedName("gender")
    val gender: Int? = null,
    @SerializedName("nick")
    val nick: String? = null,
    @SerializedName("nicks_old")
    val nicksOld: List<String?>? = null,
    @SerializedName("online")
    val online: Int? = null,
    @SerializedName("rank")
    val rank: RankJson? = null,
    @SerializedName("user_id")
    val userId: Int? = null,
    @SerializedName("xp")
    val xp: Int? = null,
    @SerializedName("xp_level")
    val xpLevel: Int? = null
)