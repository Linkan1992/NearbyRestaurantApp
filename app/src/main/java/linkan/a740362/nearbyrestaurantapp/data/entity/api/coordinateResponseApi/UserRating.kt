package linkan.a740362.nearbyrestaurantapp.data.entity.api.coordinateResponseApi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserRating(

    @SerializedName("aggregate_rating")
    @Expose
    val ratingNum: String,

    @SerializedName("rating_color")
    @Expose
    val ratingColor: String

) : Serializable