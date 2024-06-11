package my.dahr.monopolyone.data.network.dto.response.friends.requests


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RequestJson(
    @Expose
    @SerializedName("avatar")
    val avatar: String? = null,
    @Expose
    @SerializedName("friendship")
    val friendship: Int? = null,
    @Expose
    @SerializedName("games")
    val games: Int? = null,
    @Expose
    @SerializedName("games_wins")
    val gamesWins: Int? = null,
    @Expose
    @SerializedName("gender")
    val gender: Int? = null,
    @Expose
    @SerializedName("nick")
    val nick: String? = null,
    @Expose
    @SerializedName("nicks_old")
    val nicksOld: List<String?>? = null,
    @Expose
    @SerializedName("rank")
    val rank: RankRequestsJson? = null,
    @Expose
    @SerializedName("user_id")
    val userId: Int? = null,
    @Expose
    @SerializedName("xp")
    val xp: Int? = null,
    @Expose
    @SerializedName("xp_level")
    val xpLevel: Int? = null
)