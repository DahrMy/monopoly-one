package my.dahr.monopolyone.domain.models.friends.requests



data class Request(
    val avatar: String,
    val friendship: Int,
    val games: Int,
    val gamesWins: Int,
    val gender: Int,
    val nick: String,
    val nicksOld: List<String?>,
    val rank: RankRequests,
    val userId: Int,
    val xp: Int,
    val xpLevel: Int
)