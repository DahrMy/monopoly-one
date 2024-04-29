package my.dahr.monopolyone.utils

val currentTimeInSec: Long get() = (java.util.Date().time / 1000).toInt().toLong()