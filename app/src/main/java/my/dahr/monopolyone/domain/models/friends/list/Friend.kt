package my.dahr.monopolyone.domain.models.friends.list



data class Friend(
    val approved: Int,
    val avatar: String,
    val avatarKey: String,
    val domain: String,
    val games: Int,
    val gamesWins: Int,
    val gender: Int,
    val moderator: Int,
    val nick: String,
    val nicksOld: List<Any?>,
    val online: Int,
    val profileCover: String,
    val rank: Rank,
    val superadmin: Int,
    val userId: Int,
    val vip: Int,
    val xp: Int,
    val xpLevel: Int
)