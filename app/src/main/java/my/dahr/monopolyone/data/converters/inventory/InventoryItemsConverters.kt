package my.dahr.monopolyone.data.converters.inventory

import my.dahr.monopolyone.data.network.dto.inventory.items.CollectionJson
import my.dahr.monopolyone.data.network.dto.inventory.items.DataJson
import my.dahr.monopolyone.data.network.dto.inventory.items.DropJson
import my.dahr.monopolyone.data.network.dto.inventory.items.InventoryResponse
import my.dahr.monopolyone.data.network.dto.inventory.items.ItemJson
import my.dahr.monopolyone.data.network.dto.inventory.items.PricesJson
import my.dahr.monopolyone.data.network.dto.inventory.items.RankJson
import my.dahr.monopolyone.data.network.dto.inventory.items.UserJson
import my.dahr.monopolyone.domain.models.inventory.items.Collection
import my.dahr.monopolyone.domain.models.inventory.items.Data
import my.dahr.monopolyone.domain.models.inventory.items.Drop
import my.dahr.monopolyone.domain.models.inventory.items.Inventory
import my.dahr.monopolyone.domain.models.inventory.items.Item
import my.dahr.monopolyone.domain.models.inventory.items.Prices
import my.dahr.monopolyone.domain.models.inventory.items.Rank
import my.dahr.monopolyone.domain.models.inventory.items.User

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
        code = code,
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

fun RankJson.toUi(): Rank {
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
