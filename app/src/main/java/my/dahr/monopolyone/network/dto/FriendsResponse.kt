package my.dahr.monopolyone.network.dto


import com.google.gson.annotations.SerializedName

data class FriendsResponse(
    @SerializedName("code")
    val code: Int? = 0,
    @SerializedName("data")
    val `data`: Data? = Data()
) {
    data class Data(
        @SerializedName("count")
        val count: Int? = 0,
        @SerializedName("friends")
        val friends: List<Friend?>? = listOf()
    ) {
        data class Friend(
            @SerializedName("approved")
            val approved: Int? = 0,
            @SerializedName("avatar")
            val avatar: String? = "",
            @SerializedName("avatar_key")
            val avatarKey: String? = "",
            @SerializedName("badge")
            val badge: Badge? = Badge(),
            @SerializedName("domain")
            val domain: String? = "",
            @SerializedName("games")
            val games: Int? = 0,
            @SerializedName("games_wins")
            val gamesWins: Int? = 0,
            @SerializedName("gender")
            val gender: Int? = 0,
            @SerializedName("moderator")
            val moderator: Int? = 0,
            @SerializedName("nick")
            val nick: String? = "",
            @SerializedName("nicks_old")
            val nicksOld: List<Any?>? = listOf(),
            @SerializedName("profile_cover")
            val profileCover: String? = "",
            @SerializedName("profile_cover_key")
            val profileCoverKey: String? = "",
            @SerializedName("rank")
            val rank: Rank? = Rank(),
            @SerializedName("social_vk")
            val socialVk: Int? = 0,
            @SerializedName("superadmin")
            val superadmin: Int? = 0,
            @SerializedName("user_id")
            val userId: Int? = 0,
            @SerializedName("vip")
            val vip: Int? = 0,
            @SerializedName("xp")
            val xp: Int? = 0,
            @SerializedName("xp_level")
            val xpLevel: Int? = 0
        ) {
            data class Badge(
                @SerializedName("can_be_upgraded")
                val canBeUpgraded: Int? = 0,
                @SerializedName("collection")
                val collection: Int? = 0,
                @SerializedName("description")
                val description: String? = "",
                @SerializedName("group")
                val group: Any? = Any(),
                @SerializedName("image")
                val image: String? = "",
                @SerializedName("owned_time")
                val ownedTime: Int? = 0,
                @SerializedName("quality")
                val quality: Int? = 0,
                @SerializedName("thing_id")
                val thingId: Int? = 0,
                @SerializedName("thing_prototype_id")
                val thingPrototypeId: Int? = 0,
                @SerializedName("thing_prototype_status")
                val thingPrototypeStatus: Int? = 0,
                @SerializedName("thing_type")
                val thingType: Int? = 0,
                @SerializedName("title")
                val title: String? = "",
                @SerializedName("user_id")
                val userId: Int? = 0
            )

            data class Rank(
                @SerializedName("hidden")
                val hidden: Int? = 0
            )
        }
    }
}