package my.dahr.monopolyone.data.source.ip.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParcelableIp(
    val address: String
) : Parcelable
