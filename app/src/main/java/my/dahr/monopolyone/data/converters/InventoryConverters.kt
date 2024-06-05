package my.dahr.monopolyone.data.converters

import my.dahr.monopolyone.data.network.dto.inventory.CollectionJson
import my.dahr.monopolyone.data.network.dto.inventory.DataJson
import my.dahr.monopolyone.data.network.dto.inventory.DropJson
import my.dahr.monopolyone.data.network.dto.inventory.InventoryResponse
import my.dahr.monopolyone.data.network.dto.inventory.ItemJson
import my.dahr.monopolyone.data.network.dto.inventory.PricesJson
import my.dahr.monopolyone.data.network.dto.inventory.RankJson
import my.dahr.monopolyone.data.network.dto.inventory.UserJson
import my.dahr.monopolyone.domain.models.inventory.Collection
import my.dahr.monopolyone.domain.models.inventory.Data
import my.dahr.monopolyone.domain.models.inventory.Drop
import my.dahr.monopolyone.domain.models.inventory.Inventory
import my.dahr.monopolyone.domain.models.inventory.Item
import my.dahr.monopolyone.domain.models.inventory.Prices
import my.dahr.monopolyone.domain.models.inventory.Rank
import my.dahr.monopolyone.domain.models.inventory.User

fun CollectionJson.toUi(): Collection {
    return Collection(
        collectionId = collectionId ?: -1,
        title = title ?: ""
    )
}

fun DataJson.toUi(): Data {
    return Data(
        collections = collections?.mapNotNull { it?.toUi() } ?: listOf(),
        count = count ?: -1,
        items = items?.mapNotNull { it?.toUi() } ?: listOf(),
        user = (user ?: UserJson()).toUi()
    )
}

fun DropJson.toUi(): Drop {
    return Drop(
        itemProtoId = itemProtoId ?: -1
    )
}

fun InventoryResponse.toUi(): Inventory {
    return Inventory(
        code = code ?: -1,
        data = (data ?: DataJson()).toUi()
    )
}

fun ItemJson.toUi(): Item {
    return Item(
        caseItemProtoIds = caseItemProtoIds ?: listOf(),
        collectionId = collectionId ?: -1,
        description = description ?: "",
        drop = drop?.mapNotNull { it?.toUi() } ?: listOf(),
        image = image ?: "",
        itemId = itemId ?: -1,
        itemProtoId = itemProtoId ?: -1,
        prices = (prices ?: PricesJson()).toUi(),
        qualityId = qualityId ?: -1,
        title = title ?: "",
        tsOwned = tsOwned ?: -1,
        type = type ?: -1
    )
}

fun PricesJson.toUi(): Prices {
    return Prices(
        buy = buy ?: -1
    )
}

fun RankJson.toUi(): Rank{
    return Rank(
        hidden = hidden?: -1
    )
}

fun UserJson.toUi(): User {
    return User(
        avatar = avatar?:"",
        avatarKey = avatarKey ?: "",
        friendship = friendship ?: -1,
        games = games ?:-1,
        gamesWins = gamesWins ?: -1,
        gender = gender ?: -1,
        nick = nick ?: "",
        nicksOld = nicksOld ?: listOf(),
        rank = (rank ?: RankJson()).toUi(),
        userId = userId ?: -1,
        xp = xp ?:-1,
        xpLevel = xpLevel ?:-1
    )
}
