package my.dahr.monopolyone.domain.models.users

data class Data(
    val approved: Int,
    val avatar: String,
    val domain: String,
    val games: Int,
    val gamesWins: Int,
    val gender: Int,
    val nick: String,
    val nicksOld: List<String?>,
    val online: Int,
    val rank: Rank,
    val userId: Int,
    val xp: Int,
    val xpLevel: Int
)