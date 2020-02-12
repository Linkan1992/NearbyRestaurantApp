package linkan.a740362.nearbyrestaurantapp.data.entity.api.locationSearchResponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import linkan.a740362.nearbyrestaurantapp.data.entity.api.coordinateResponseApi.NearbyRestaurant
import java.io.Serializable

data class LocationRestaurantResponse(

    @SerializedName("restaurants")
    @Expose
    val restaurants : List<NearbyRestaurant>?

) : Serializable