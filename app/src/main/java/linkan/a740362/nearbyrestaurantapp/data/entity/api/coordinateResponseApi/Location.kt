package linkan.a740362.nearbyrestaurantapp.data.entity.api.coordinateResponseApi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Location(

    @SerializedName("address")
    @Expose
    val address: String,

    @SerializedName("locality")
    @Expose
    val locality: String,

    @SerializedName("city")
    @Expose
    val city: String,

    @SerializedName("city_id")
    @Expose
    val cityId: Int,

    @SerializedName("locality_verbose")
    @Expose
    val localityVerbose: String

) : Serializable