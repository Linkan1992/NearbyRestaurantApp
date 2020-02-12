package linkan.a740362.nearbyrestaurantapp.data.entity.api.coordinateResponseApi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Restaurant(

    @SerializedName("id")
    @Expose
    val restaurantId: Int,

    @SerializedName("name")
    @Expose
    val restaurantName: String,

    @SerializedName("cuisines")
    @Expose
    val cuisines: String,

    @SerializedName("location")
    @Expose
    val location: Location,

    @SerializedName("average_cost_for_two")
    @Expose
    val average_cost_per_two: Int,

    @SerializedName("currency")
    @Expose
    val currencySymbol: String,

    @SerializedName("thumb")
    @Expose
    val thumbnail: String,

    @SerializedName("user_rating")
    @Expose
    val userRating: UserRating

) : Serializable