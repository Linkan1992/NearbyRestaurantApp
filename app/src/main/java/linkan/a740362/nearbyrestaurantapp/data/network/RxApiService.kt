package linkan.a740362.nearbyrestaurantapp.data.network

import io.reactivex.Single
import linkan.a740362.nearbyrestaurantapp.data.entity.api.locationSearchResponse.LocationRestaurantResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RxApiService {

    // declare method for api call

    @GET("search")
    fun fetchLocationBasedRestaurants(
        @Query("q") query: String,
        @Query("count") count_per_page: Int,
        @Query("start") startFrom: Int,
        @Query("entity_type") entity_type: String
    ): Single<LocationRestaurantResponse>

}