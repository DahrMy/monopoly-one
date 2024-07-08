package my.dahr.monopolyone.domain

import java.util.Date

val currentTimeInSec get() = (Date().time / 1000).toInt().toLong()