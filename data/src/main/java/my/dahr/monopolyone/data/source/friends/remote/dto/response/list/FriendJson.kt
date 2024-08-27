package my.dahr.monopolyone.data.network.dto.response.friends.list


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FriendJson(
    @Expose
    @SerializedName("approved")
    val approved: Int? = null,
    @Expose
    @SerializedName("avatar")
    val avatar: String? = null,
    @Expose
    @SerializedName("avatar_key")
    val avatarKey: String? = null,
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
    @SerializedName("moderator")
    val moderator: Int? = null,
    @Expose
    @SerializedName("nick")
    val nick: String? = null,
    @Expose
    @SerializedName("nicks_old")
    val nicksOld: List<Any?>? = null,
    @Expose
    @SerializedName("online")
    val online: Int? = null,
    @Expose
    @SerializedName("profile_cover")
    val profileCover: String? = null,
    @Expose
    @SerializedName("rank")
    val rank: RankJson? = null,
    @Expose
    @SerializedName("superadmin")
    val superadmin: Int? = null,
    @Expose
    @SerializedName("user_id")
    val userId: Int? = null,
    @Expose
    @SerializedName("vip")
    val vip: Int? = null,
    @Expose
    @SerializedName("xp")
    val xp: Int? = null,
    @Expose
    @SerializedName("xp_level")
    val xpLevel: Int? = null
)