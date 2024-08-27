package my.dahr.monopolyone.data.network.api.monopoly.utils

internal val currentTimeInSec: Long get() = (java.util.Date().time / 1000).toInt().toLong()

internal fun validEmail(email: String) = Regex(
    """^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$"""
).matches(email)

internal fun validPassword(password: String) = Regex(
    """^(.){10,}$"""
).matches(password)