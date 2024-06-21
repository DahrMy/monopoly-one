package my.dahr.monopolyone.domain.models.inventory.items



data class User(
    val avatar: String,
    val avatarKey: String,
    val friendship: Int,
    val games: Int,
    val gamesWins: Int,
    val gender: Int,
    val nick: String,
    val nicksOld: List<Any?>,
    val rank: Rank,
    val userId: Int,
    val xp: Int,
    val xpLevel: Int
)