package wangyeo.interview.data.models

import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lat") val lat: Double? = 0.0,
    @SerializedName("lon") val long: Double? = 0.0,
) : Model()
