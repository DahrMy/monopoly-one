package my.dahr.monopolyone.data.utils

enum class ItemTypeConverter(val number: Int, val word: String) {
    ZERO(0, "Card"),
    ONE(1, "Gift Set"),
    TWO(2, "Case"),
    THREE(3, "Key"),
    FOUR(4, "Number Generator"),
    FIVE(5, "Icon"),
    SIX(6, "Pass (Disposable Item)"),
    SEVEN(7, "Subscription (Reusable Item)"),
    EIGHT(8, "Mockery"),
    NINE(9, "Sticker");

    companion object {
        fun fromNumber(number: Int): String? {
            return entries.find { it.number == number }?.word
        }
    }
}