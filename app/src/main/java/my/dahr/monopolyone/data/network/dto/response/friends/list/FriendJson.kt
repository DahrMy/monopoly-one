package my.dahr.monopolyone.data.network.dto.response.friends.list


import com.google.gson.annotations.SerializedName

data class FriendJson(
    @SerializedName("approved")
    val approved: Int? = null,
    @SerializedName("avatar")
    val avatar: String? = null,
    @SerializedName("avatar_key")
    val avatarKey: String? = null,
    @SerializedName("domain")
    val domain: String? = null,
    @SerializedName("games")
    val games: Int? = null,
    @SerializedName("games_wins")
    val gamesWins: Int? = null,
    @SerializedName("gender")
    val gender: Int? = null,
    @SerializedName("moderator")
    val moderator: Int? = null,
    @SerializedName("nick")
    val nick: String? = null,
    @SerializedName("nicks_old")
    val nicksOld: List<Any?>? = null,
    @SerializedName("online")
    val online: Int? = null,
    @SerializedName("profile_cover")
    val profileCover: String? = null,
    @SerializedName("rank")
    val rank: RankJson? = null,
    @SerializedName("superadmin")
    val superadmin: Int? = null,
    @SerializedName("user_id")
    val userId: Int? = null,
    @SerializedName("vip")
    val vip: Int? = null,
    @SerializedName("xp")
    val xp: Int? = null,
    @SerializedName("xp_level")
    val xpLevel: Int? = null
)