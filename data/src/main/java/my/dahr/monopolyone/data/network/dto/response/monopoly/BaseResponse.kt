package my.dahr.monopolyone.data.network.dto.response.monopoly

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("base_code")
    open val code: Int,
    @SerializedName("base_description")
    open val description: String?,
    @SerializedName("base_data")
    open val `data`: Any?
) {
    override fun toString(): String {
        return "BaseResponse(code=$code, description=$description, `data`=${`data`.toString()})"
    }
}
