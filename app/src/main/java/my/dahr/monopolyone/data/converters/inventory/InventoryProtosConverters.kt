package my.dahr.monopolyone.data.converters.inventory

import my.dahr.monopolyone.data.network.dto.inventory.protos.CollectionJson
import my.dahr.monopolyone.data.network.dto.inventory.protos.DataJson
import my.dahr.monopolyone.data.network.dto.inventory.protos.DropJson
import my.dahr.monopolyone.data.network.dto.inventory.protos.ItemProtoJson
import my.dahr.monopolyone.data.network.dto.inventory.protos.PricesJson
import my.dahr.monopolyone.data.network.dto.inventory.protos.ProtosResponse
import my.dahr.monopolyone.domain.models.inventory.protos.Collection
import my.dahr.monopolyone.domain.models.inventory.protos.Data
import my.dahr.monopolyone.domain.models.inventory.protos.Drop
import my.dahr.monopolyone.domain.models.inventory.protos.ItemProto
import my.dahr.monopolyone.domain.models.inventory.protos.Prices
import my.dahr.monopolyone.domain.models.inventory.protos.Protos


fun CollectionJson.toUi(): Collection {
    return Collection(
        collectionId = collectionId ?: -1,
        title = title ?: ""
    )
}

fun DataJson.toUi(): Data {
    return Data(
        collections = collections?.mapNotNull { it?.toUi() } ?: listOf(),
        itemProtos = itemProtos?.mapNotNull { it?.toUi() } ?: listOf()
    )
}

fun DropJson.toUi(): Drop {
    return Drop(
        isRare = isRare ?: -1,
        isSecondary = isSecondary ?: -1,
        itemProtoId = itemProtoId ?: -1,
    )
}

fun ProtosResponse.toUi(): Protos {
    return Protos(
        code = code,
        data = (data ?: DataJson()).toUi()
    )
}

fun ItemProtoJson.toUi(): ItemProto {
    return ItemProto(
        caseItemProtoIds = caseItemProtoIds ?: listOf(),
        collectionId = collectionId ?: -1,
        description = description ?: "",
        drop = drop?.mapNotNull { it?.toUi() } ?: listOf(),
        image = image ?: "",
        itemProtoId = itemProtoId ?: -1,
        keyItemProtoId = keyItemProtoId ?: -1,
        monopolyId = monopolyId ?: -1,
        prices = (prices ?: PricesJson()).toUi(),
        qualityId = qualityId ?: -1,
        title = title ?: "",
        type = type ?: -1
    )
}

fun PricesJson.toUi(): Prices {
    return Prices(
        buy = buy ?: -1,
        quickSell = quickSell ?: -1.0
    )
}
