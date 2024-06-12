package my.dahr.monopolyone.data.network.dto.response.users

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataJson(
    @Expose
    @SerializedName("approved")
    val approved: Int? = null,
    @Expose
    @SerializedName("avatar")
    val avatar: String? = null,
    @Expose
    @SerializedName("domain")
    val domain: String? = null,
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
    @SerializedName("online")
    val online: Int? = null,
    @Expose
    @SerializedName("rank")
    val rank: RankJson? = null,
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