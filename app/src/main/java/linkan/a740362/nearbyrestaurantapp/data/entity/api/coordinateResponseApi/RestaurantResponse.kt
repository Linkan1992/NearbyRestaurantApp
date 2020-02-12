package linkan.a740362.nearbyrestaurantapp.data.entity.api.coordinateResponseApi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RestaurantResponse(

    @SerializedName("nearby_restaurants")
    @Expose
    val nearbyRestaurants : List<NearbyRestaurant>?

) : Serializable